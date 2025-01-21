package com.eh.demo.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RoleId  implements Serializable {

    String uid;

    String role;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleId{" +
                "uid='" + uid + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
