package org.yigit.enums;

public enum AccountStatus {
    ACTIVE("Active"),
    DELETED("Delete");
    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
