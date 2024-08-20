package com.cuongpn.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty(value = "ROLE_ADMIN")
    ROLE_ADMIN,
    @JsonProperty(value = "ROLE_USER")
    ROLE_USER
}
