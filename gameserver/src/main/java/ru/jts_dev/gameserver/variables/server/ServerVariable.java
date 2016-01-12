package ru.jts_dev.gameserver.variables.server;

import ru.jts_dev.gameserver.variables.AbstractVariable;

import javax.persistence.*;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Entity
@IdClass(ServerVariableKey.class)
public class ServerVariable extends AbstractVariable {
    @Id
    private byte serverId;

    @Id
    @Enumerated(EnumType.STRING)
    private ServerVariableType serverVariableType;

    public void setServerId(byte serverId) {
        this.serverId = serverId;
    }

    public void setServerVariableType(ServerVariableType serverVariableType) {
        this.serverVariableType = serverVariableType;
    }
}
