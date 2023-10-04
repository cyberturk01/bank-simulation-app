package org.yigit.enums;

public enum AccountType {
    CHECKING("Checking"),
    SAVING("Saving");

    public final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
