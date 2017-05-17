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

package ru.jts_dev.gameserver.party;

import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Java-man
 * @since 21.01.2016
 */
public class Party {
    private static final int MAX_CHARACTERS_IN_PARTY = 8;

    private List<GameCharacter> members = new ArrayList<>(MAX_CHARACTERS_IN_PARTY);

    public Party(GameCharacter leader) {
        members.add(leader);
    }

    public void joinParty(GameCharacter character) {
        members.add(character);
    }
}
