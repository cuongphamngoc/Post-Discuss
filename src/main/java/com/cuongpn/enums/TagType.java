package com.cuongpn.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TagType {
    @JsonProperty("admin")
    ADMIN,
    @JsonProperty("user")
    USER,
}
