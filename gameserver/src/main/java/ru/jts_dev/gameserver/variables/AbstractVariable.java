package ru.jts_dev.gameserver.variables;

import javax.persistence.Column;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public abstract class AbstractVariable {
    @Column
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
