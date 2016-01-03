package ru.jts_dev.gameserver.variables.server;

import ru.jts_dev.gameserver.variables.AbstractVariable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Table
@Entity
public class ServerVariable extends AbstractVariable {
    @Column
    private int serverId;

    public ServerVariable(int serverId, ServerVariableType serverVariableType) {
        super(serverVariableType);
        this.serverId = serverId;
    }
}
