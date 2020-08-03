package com.ad_sih;

public class player {
    String name,age,gender,mail1,mail2;
    int score;

    public player(String name, String age, String gender, String mail1, String mail2, int score) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mail1 = mail1;
        this.mail2 = mail2;
        this.score = score;
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
