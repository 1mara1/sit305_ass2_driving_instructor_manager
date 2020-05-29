package com.example.drivingschoolmanagerandplanner.models;

public class Planner {

    private String topic;
    private String comments;
    private float ratings;
    private int lessonId;

    public Planner(String topic, String comments, float ratings, int lessonId) {
        this.topic = topic;
        this.comments = comments;
        this.ratings = ratings;
        this.lessonId = lessonId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
