package ru.jts_dev.gameserver.packets;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Component
public class GameClientPacketHandler {
    private static final Logger log = LoggerFactory.getLogger(GameClientPacketHandler.class);
    @Autowired
    private ApplicationContext context;

    @Autowired
    // proxy copies of packets, used only for parse @Opcode Annotation
    private List<IncomingMessageWrapper> packetProxies;

    private Map<Integer, Object> packets;

    @PostConstruct
    private void postConstruct() {
        packets = new TreeMap<>();
        for (IncomingMessageWrapper packetProxy : packetProxies) {
            Opcode opcode = AnnotationUtils.findAnnotation(packetProxy.getClass(), Opcode.class);

            assert opcode != null : "opcode annotation not present for " + packetProxy.getClass();

            int firstOpcode = opcode.first();
            int secondOpcode = opcode.second();
            if (secondOpcode != Integer.MIN_VALUE) {
                Map<Integer, Object> secondOpcodesMap = (Map<Integer, Object>) packets.getOrDefault(firstOpcode, new HashMap<Integer, Object>());

                assert !secondOpcodesMap.containsKey(secondOpcode)
                        : "duplicate second opcode for " + packetProxy.getClass() + ", " + secondOpcodesMap.get(secondOpcode);

                secondOpcodesMap.put(secondOpcode, packetProxy.getClass());
                packets.putIfAbsent(firstOpcode, secondOpcodesMap);
            }
            packets.putIfAbsent(firstOpcode, packetProxy.getClass());
        }
    }

    public IncomingMessageWrapper handle(ByteBuf buf, @Header(CONNECTION_ID) String connectionId) {
        if (buf.readableBytes() == 0)
            throw new RuntimeException("At least 1 readable byte excepted in buffer");

        int opcode = buf.readUnsignedByte();

        if (!packets.containsKey(opcode))
            throw new RuntimeException("Invalid first packet opcode: " + String.format("0x%02X", (byte) opcode));

        IncomingMessageWrapper msg;

        Object node = packets.get(opcode);
        if (node instanceof Class) {
            msg = context.getBean((Class<IncomingMessageWrapper>) node);
        } else { // (node instanceof Map)
            opcode = buf.readUnsignedShort();

            if (!((Map) node).containsKey(opcode))
                throw new RuntimeException("Invalid second packet opcode: " + String.format("0x%02X", (byte) opcode));

            node = ((Map) node).get(opcode);
            msg = context.getBean((Class<IncomingMessageWrapper>) node);
        }

        ByteBuf data = buf.slice();

        log.trace("received packet: " + msg.getClass().getSimpleName() + ", length: " + data.readableBytes());

        msg.getHeaders().put(CONNECTION_ID, connectionId);
        msg.setPayload(data);

        return msg;
    }
}
