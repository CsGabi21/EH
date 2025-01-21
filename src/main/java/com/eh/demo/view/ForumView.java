package com.eh.demo.view;

public class ForumView {

    public Integer fid;

    public String comment;

    public String user;

    public String timestamp;

    public ForumView(Integer fid,  String comment, String user, String timestamp) {
        this.fid = fid;
        this.comment = comment;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getFid() {
        return fid;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "fid=" + fid +
                ", comment='" + comment + '\'' +
                ", user='" + user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
