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
import ru.jts_dev.gameserver.packets.in.*;

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
        IncomingMessageWrapper msg;
        switch (opcode) {
            case 0x00:
                msg = context.getBean(Logout.class);
                break;
            case 0x0C:
                msg = context.getBean(CharacterCreate.class);
                break;
            case 0x0E:
                msg = context.getBean(RequestProtocolVersion.class);
                break;
            case 0x0F:
                msg = context.getBean(MoveBackwardToLocation.class);
                break;
            case 0x11:
                msg = context.getBean(EnterWorld.class);
                break;
            case 0x12:
                msg = context.getBean(CharacterSelect.class);
                break;
            case 0x13:
                msg = context.getBean(RequestNewCharacter.class);
                break;
            case 0x2B:
                msg = context.getBean(AuthLogin.class);
                break;
            case 0x67:
                msg = context.getBean(RequestPledgeCrest.class);
                break;
            case 0x92:
                msg = context.getBean(RequestAllyCrest.class);
                break;
            // second level opcodes
            case 0xD0:
                opcode = buf.readUnsignedShort();
                switch (opcode) {
                    case 0x01:
                        msg = context.getBean(RequestManorList.class);
                        break;
                    case 0x21:
                        msg = context.getBean(RequestKeyMapping.class);
                        break;
                    case 0x36:
                        msg = context.getBean(GotoLobby.class);
                        break;
                    case 0x3D:
                        msg = context.getBean(RequestShortCutReg.class);
                        break;
                    default:
                        throw new RuntimeException("Invalid second packet opcode: " + String.format("0x%02X", (byte) opcode));
                }
                break;
            default:
                throw new RuntimeException("Invalid first packet opcode: " + String.format("0x%02X", (byte) opcode));
        }

        ByteBuf data = buf.slice();

        log.trace("received packet: " + msg.getClass().getSimpleName() + ", length: " + data.readableBytes());

        msg.getHeaders().put(CONNECTION_ID, connectionId);
        msg.setPayload(data);

        return msg;
    }
}
