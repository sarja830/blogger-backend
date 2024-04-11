package com.blog.demo.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    private  int value;
    VoteType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}