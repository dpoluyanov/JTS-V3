package ru.jts_dev.gameserver.variables;

import ru.jts_dev.gameserver.variables.server.ServerVariableType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public abstract class AbstractVariable {
    @Column
    @Enumerated(EnumType.STRING)
    private ServerVariableType serverVariableType;

    @Column
    private String value;

    public AbstractVariable(ServerVariableType serverVariableType) {
        this.serverVariableType = serverVariableType;
    }

    public ServerVariableType getServerVariableType() {
        return serverVariableType;
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
