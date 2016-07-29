package ru.jts_dev.gameserver.parser.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.constants.ActionHandlerType;
import ru.jts_dev.gameserver.parser.data.action.Action;
import ru.jts_dev.gameserver.parser.data.npc.NpcDatasHolder;

import java.util.Map;
import java.util.Map.Entry;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.jts_dev.gameserver.constants.ActionHandlerType.*;

/**
 * @author Camelion
 * @since 20.01.16
 */
@SpringJUnitConfig(UserBasicActionsHolder.class)
public class UserBasicActionsHolderTest {

    @Autowired
    private UserBasicActionsHolder userBasicActionsHolder;

    /**
     * Test, that {@link UserBasicActionsHolder#actionsData} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseActionsData() throws Exception {
        assertThat(userBasicActionsHolder.getActionsData().size()).isGreaterThan(0);

        for (final Entry<Integer, Action> entry : userBasicActionsHolder.getActionsData().entrySet()) {
            assertThat(entry.getKey()).as("For action %s", entry.getKey()).isGreaterThanOrEqualTo(0);
            assertThat(entry.getKey()).as("For action %s", entry.getKey()).isEqualTo(entry.getValue().getId());

            // Option can be presented only with handler type
            if (entry.getValue().getOption() != null)
                assertThat(entry.getValue().getHandler()).as("For action %s", entry.getKey()).isNotNull();

            // only integer possible for ActionHandlerType.SOCIAL_ACTION and ActionHandlerType.COUPLE_ACTION
            // for ActionHandlerType.PET_ACTION, ActionHandlerType.SUMMON_ACTION and ActionHandlerType.AIRSHIO_VALUE
            // string option must be present
            // for other cases option must be set to null
            if (entry.getValue().getHandler() == SOCIAL_ACTION
                    || entry.getValue().getHandler() == COUPLE_ACTION) {
                assertThat(entry.getValue().getOption()).as("For action %s", entry.getKey())
                        .isNotNull().isInstanceOf(Integer.class);
            } else if (entry.getValue().getHandler() == PET_ACTION
                    || entry.getValue().getHandler() == SUMMON_ACTION
                    || entry.getValue().getHandler() == AIRSHIP_ACTION) {
                assertThat(entry.getValue().getOption()).as("For action %s", entry.getKey()).isNotNull();
            } else {
                assertThat(entry.getValue().getOption()).as("For action %s", entry.getKey()).isNull();
            }
        }
    }


}