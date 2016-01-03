package ru.jts_dev.gameserver.variables.server;

import ru.jts_dev.gameserver.variables.AbstractVariable;

import javax.persistence.*;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Table
@Entity
@IdClass(ServerVariableKey.class)
public class ServerVariable extends AbstractVariable {
    @Id
    @Column
    private int serverId;

    @Id
    @Column
    @Enumerated(EnumType.STRING)
    private ServerVariableType serverVariableType;

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setServerVariableType(ServerVariableType serverVariableType) {
        this.serverVariableType = serverVariableType;
    }
}
