package com.project.classes;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private String question;
    private String answer;

    public User(String username, String password, String email, Date birthday, String question, String answer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.question = question;
        this.answer = answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
