package com.eh.demo.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
@Transactional
@Table(name = "Forum")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer fid;

    public String comment;

    public String uid;

    public String timestamp;

    public Forum(String comment, String uid, String timestamp) {
        this.comment = comment;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
                ", uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
    
}
