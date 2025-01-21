package com.eh.demo.view;

public class RoleView {

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

    private RoleView(String uid, String role) {
        this.uid = uid;
        this.role = role;
    }

    public static RoleView roleViewFactory(String uid, String role) {
        return new RoleView(uid, role);
    }

    @Override
    public String toString() {
        return "RoleView{" +
                "uid='" + uid + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
