package com.example.newsappsudealan;

public class CommentModel {

    int id, news_id;
    String name, commentText;

    public CommentModel(int id, int news_id, String name, String commentText) {
        this.id = id;
        this.news_id = news_id;
        this.name = name;
        this.commentText = commentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
