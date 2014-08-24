package com.huayuan.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Richard on 14-8-22.
 */
public class UserDto implements Serializable {
    private String username;
    private String password;
    @JsonIgnore
    private String accessMenus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessMenus() {
        return accessMenus;
    }

    public void setAccessMenus(String accessMenus) {
        this.accessMenus = accessMenus;
    }
}
