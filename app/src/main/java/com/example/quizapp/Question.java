package com.example.quizapp;

import java.util.Date;

public class Question {
    // Fields for the class
    private String questionText;
    private String options;
    private String answer;
    private String level;  // Level of the question, e.g., A1, A2
    private String type;   // Type of the question, e.g., MCQ, one-word, etc.
    private Date date;     // Date when the question was posted

    // Constructor
    public Question(String questionText, String options, String answer, String level, String type, Date date) {
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
        this.level = level;
        this.type = type;
        this.date = date;
    }

    // Getter and Setter methods

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
