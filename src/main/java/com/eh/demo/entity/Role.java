package com.eh.demo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {

    @EmbeddedId
    RoleId roleId;

    public RoleId getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleId roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                '}';
    }
}
