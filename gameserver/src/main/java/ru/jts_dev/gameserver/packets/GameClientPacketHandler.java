package ru.jts_dev.gameserver.packets;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;
import static ru.jts_dev.gameserver.packets.Opcode.CLIENT_SWITCH_OPCODE;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Component
public class GameClientPacketHandler {
    private static final int BYTES_COUNT = -Byte.MIN_VALUE + Byte.MAX_VALUE;
    private static final Logger log = LoggerFactory.getLogger(GameClientPacketHandler.class);
    @Autowired
    private ApplicationContext context;

    private Map<Integer, Object> packets;

    /**
     * Find beans with {@link Opcode} annotation, and put it to {@link #packets} map
     * where key is 'first' (or single) part of packet identifier
     * and value is a bean name of packet or {@link Map} with 'second' part of
     * packet identifier as key, and packet bean name as value
     *
     * @see IncomingMessageWrapper
     */
    @PostConstruct
    private void postConstruct() {
        final String[] packetBeanNames = context.getBeanNamesForAnnotation(Opcode.class);

        packets = new HashMap<>(BYTES_COUNT, 1.0f);
        for (final String beanName : packetBeanNames) {
            final Opcode opcode = context.findAnnotationOnBean(beanName, Opcode.class);

            assert opcode != null : "opcode annotation not present for bean " + beanName;

            final int firstOpcode = opcode.first();
            final int secondOpcode = opcode.second();
            if (secondOpcode != Integer.MIN_VALUE) {
                final Map<Integer, Object> secondOpcodesMap =
                        (Map<Integer, Object>) packets.getOrDefault(firstOpcode, new HashMap<Integer, Object>());

                assert !secondOpcodesMap.containsKey(secondOpcode)
                        : "duplicate second opcode for " + beanName + ", old is " + secondOpcodesMap.get(secondOpcode);

                secondOpcodesMap.put(secondOpcode, beanName);
                packets.putIfAbsent(firstOpcode, secondOpcodesMap);
            }

            assert firstOpcode == CLIENT_SWITCH_OPCODE || !packets.containsKey(firstOpcode)
                    : "duplicate first opcode for " + beanName + ", old is " + packets.get(firstOpcode);

            packets.putIfAbsent(firstOpcode, beanName);
        }
    }

    /**
     * Handle incoming packet by first bytes (opcode)
     *
     * @param buf          - packet data
     * @param connectionId - client connectionId from Spring Integration
     * @return - handled packet
     */
    public IncomingMessageWrapper handle(ByteBuf buf, @Header(CONNECTION_ID) String connectionId) {
        if (buf.readableBytes() == 0)
            throw new RuntimeException("At least 1 readable byte excepted in buffer");

        int opcode = buf.readUnsignedByte();

        if (!packets.containsKey(opcode))
            throw new RuntimeException("Invalid first packet opcode: " + String.format("0x%02X", (byte) opcode));

        IncomingMessageWrapper msg;

        Object node = packets.get(opcode);
        if (node instanceof String) {
            msg = context.getBean((String) node, IncomingMessageWrapper.class);
        } else { // (node instanceof Map)
            opcode = buf.readUnsignedShort();

            if (!((Map) node).containsKey(opcode))
                throw new RuntimeException("Invalid second packet opcode: " + String.format("0x%02X", (byte) opcode));

            node = ((Map) node).get(opcode);
            msg = context.getBean((String) node, IncomingMessageWrapper.class);
        }

        ByteBuf data = buf.slice();

        log.trace("received packet: " + msg.getClass().getSimpleName() + ", length: " + data.readableBytes());

        msg.getHeaders().put(CONNECTION_ID, connectionId);
        msg.setPayload(data);

        return msg;
    }
}
