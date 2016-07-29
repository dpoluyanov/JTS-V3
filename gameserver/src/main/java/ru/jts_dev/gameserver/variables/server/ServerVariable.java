package ru.jts_dev.gameserver.variables.server;

import javax.persistence.*;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Entity
@IdClass(ServerVariableKey.class)
public class ServerVariable {
    @Id
    private byte serverId;

    @Id
    @Enumerated(EnumType.STRING)
    private ServerVariableType serverVariableType;

    @Column
    private String value;

    public void setServerId(byte serverId) {
        this.serverId = serverId;
    }

    public void setServerVariableType(ServerVariableType serverVariableType) {
        this.serverVariableType = serverVariableType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
