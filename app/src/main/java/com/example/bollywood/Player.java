package com.example.bollywood;

public class Player {
    String name;
    int score;

    //Constructors

    public Player() {
    }

    public Player(String name,int score) {
        this.name = name;
        this.score = score;
    }

    //Getters//

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    //Setter//

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
