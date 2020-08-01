package com.ad_sih;

public class player {
    String name,age,gender;
    int score;

    public player(String name, String age, String gender, int score) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.score=score;
    }
    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
