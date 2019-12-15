package com.kabanov.messaging.di;

import java.util.Arrays;

/**
 * @author Kabanov Alexey
 */
public enum PlayerType {
    INITIATOR("initiator"),
    RESPONDING("responding");

    private String alias;

    PlayerType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static PlayerType parseString(String string) {
        return Arrays.stream(values())
                .filter((val -> val.getAlias().equals(string)))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
