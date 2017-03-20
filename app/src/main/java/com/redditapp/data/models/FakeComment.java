/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 */

package com.redditapp.data.models;

public class FakeComment {
    public int level;
    
    public FakeComment(int level) {
        this.level = level;
    }
    
    public String getCommentText() {
        return "Comment at level " + level + ". " +
                "Comment at level " + level + ". " +
                "Comment at level " + level + ". " +
                "Comment at level " + level + ". " +
                "Comment at level " + level + ". ";
    }
}
