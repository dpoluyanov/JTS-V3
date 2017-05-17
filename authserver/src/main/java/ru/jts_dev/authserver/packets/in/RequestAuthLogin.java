/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.authserver.packets.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.Account;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.packets.out.LoginFail;
import ru.jts_dev.authserver.packets.out.LoginOk;
import ru.jts_dev.authserver.repositories.AccountRepository;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.service.BroadcastService;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.authserver.packets.out.LoginFail.REASON_USER_OR_PASS_WRONG;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public final class RequestAuthLogin extends IncomingMessageWrapper {
    private static final Logger log = LoggerFactory.getLogger(RequestAuthLogin.class);
    private static final int LENGTH = 128;

    private final AuthSessionService authSessionService;

    private final BroadcastService broadcastService;

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository repository;

    private final AbstractConnectionFactory connectionFactory;

    @Value("${authserver.accounts.autocreate}")
    private boolean accountsAutocreate;

    private byte[] data;

    @Autowired
    public RequestAuthLogin(PasswordEncoder passwordEncoder, AbstractConnectionFactory connectionFactory, AuthSessionService authSessionService, BroadcastService broadcastService, AccountRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.connectionFactory = connectionFactory;
        this.authSessionService = authSessionService;
        this.broadcastService = broadcastService;
        this.repository = repository;
    }

    @Override
    public void prepare() {
        data = readBytes(LENGTH);
    }

    @Override
    public void run() {
        AuthSession session = authSessionService.getSessionBy(getConnectionId());

        byte[] decrypted;
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/nopadding");
            rsaCipher.init(Cipher.DECRYPT_MODE, session.getRsaKeyPair().getPrivate());
            decrypted = rsaCipher.doFinal(data, 0x00, 0x80);
        } catch (Exception e) {
            broadcastService.send(session, null);
            return;
        }

        String login = new String(decrypted, 0x5E, 14, StandardCharsets.UTF_8).trim();
        String password = new String(decrypted, 0x6C, 16, StandardCharsets.UTF_8).trim();

        if (!repository.exists(login)) {
            if (accountsAutocreate) {
                repository.save(new Account(login, passwordEncoder.encode(password)));
            } else {
                log.trace("Account with login '" + login + "' not found in database");

                broadcastService.send(session, new LoginFail(REASON_USER_OR_PASS_WRONG));
                connectionFactory.closeConnection(getConnectionId());
                return;
            }
        }

        Account account = repository.findOne(login);

        if (!passwordEncoder.matches(password, account.getPasswordHash())) {
            log.trace("Password don't match for account '" + login + "'");

            broadcastService.send(session, new LoginFail(REASON_USER_OR_PASS_WRONG));
            connectionFactory.closeConnection(getConnectionId());
            return;
        }

        broadcastService.send(session, new LoginOk(session.getLoginKey1(), session.getLoginKey2()));
    }
}
