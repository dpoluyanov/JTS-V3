package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.constants.ActionHandlerType;
import ru.jts_dev.gameserver.parser.data.action.Action;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Camelion
 * @since 20.01.16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserBasicActionsHolder.class)
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
        assertThat(userBasicActionsHolder.getActionsData().size(), greaterThan(0));

        for (final Entry<Integer, Action> entry : userBasicActionsHolder.getActionsData().entrySet()) {
            assertThat("For action " + entry.getKey(), entry.getKey(), greaterThanOrEqualTo(0));
            assertThat("For action " + entry.getKey(), entry.getKey(), equalTo(entry.getValue().getId()));

            // Option can be presented only with handler type
            if (entry.getValue().getOption() != null)
                assertThat("For action " + entry.getKey(), entry.getValue().getHandler(), is(notNullValue()));

            // only integer possible for ActionHandlerType.SOCIAL_ACTION and ActionHandlerType.COUPLE_ACTION
            // for ActionHandlerType.PET_ACTION, ActionHandlerType.SUMMON_ACTION and ActionHandlerType.AIRSHIO_VALUE
            // string option must be present
            // for other cases option must be set to null
            if (entry.getValue().getHandler() == ActionHandlerType.SOCIAL_ACTION
                    || entry.getValue().getHandler() == ActionHandlerType.COUPLE_ACTION) {
                assertThat("For action " + entry.getKey(), entry.getValue().getOption(), allOf(is(notNullValue()), is(instanceOf(Integer.class))));
            } else if (entry.getValue().getHandler() == ActionHandlerType.PET_ACTION
                    || entry.getValue().getHandler() == ActionHandlerType.SUMMON_ACTION
                    || entry.getValue().getHandler() == ActionHandlerType.AIRSHIP_ACTION) {
                assertThat("For action " + entry.getKey(), entry.getValue().getOption(), anyOf(is(notNullValue()), is(instanceOf(String.class))));
            } else {
                assertThat("For action " + entry.getKey(), entry.getValue().getOption(), is(nullValue()));
            }
        }
    }
}