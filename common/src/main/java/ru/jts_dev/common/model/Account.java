package ru.jts_dev.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Entity
public class Account {
    @Id
    private String login;

    @Column
    private String passwordHash;

    public Account(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    private Account() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
