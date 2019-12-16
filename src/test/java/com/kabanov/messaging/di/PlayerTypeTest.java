package com.kabanov.messaging.di;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kabanov Alexey
 */
public class PlayerTypeTest {

    @Test
    public void stringShouldBeParsedWhenValidAliasIsGiven() {
        Assert.assertEquals(PlayerType.INITIATOR, PlayerType.parseString("initiator"));
        Assert.assertEquals(PlayerType.RESPONDING, PlayerType.parseString("responding"));
    }
}