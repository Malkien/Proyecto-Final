package com.project.classes;

import com.project.database.DataBaseUtils;
import com.project.exceptions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class User {
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
    private String question;
    private String answer;

    public User(String username, String password, String email, LocalDate birthday, String question, String answer) throws UsernameLengthException, PasswordInvalidException, EmailInvalidException, UnderAgeException, LengthQuestionException, LengthAnswerException {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setBirthday(birthday);
        setQuestion(question);
        setAnswer(answer);
    }

    /**
     * GETTER USERMANE
     * @return THE USERNAME
     */
    public String getUsername() {
        return username;
    }

    /**
     * SETTER USERNAME
     * @param username THE USERNAME
     * @throws SQLException
     */
    public void setUsername(String username) throws UsernameLengthException {
        if(username.length()<=5 && username.length()>=20){
            this.username = username;
        }else{
            throw new UsernameLengthException();
        }
    }

    /**
     * GETTTER PASSWORD
     * @return THE PASSWORD
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws PasswordInvalidException {
        if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$")){
            this.password = password;
        }else{
            throw new PasswordInvalidException();
        }
    }

    /**
     * GETTER EMAIL
     * @return THE EMAIL
     */
    public String getEmail(){
        return email;
    }

    /**
     * SETTER EMAIL
     * @param email THE EMAIL
     * @throws SQLException
     */
    public void setEmail(String email) throws EmailInvalidException {
        if(email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") && email.length()<=50){
            this.email = email;
        }else{
            throw new EmailInvalidException();
        }
    }

    /**
     * GETTER BIRTHDAY
     * @return THE BIRTHDAY
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * SETTER BIRTHDAY
     * @param birthday THE NEW BIRTHDAY
     * @throws SQLException
     */
    public void setBirthday(LocalDate birthday) throws UnderAgeException {
        if(Period.between(birthday,LocalDate.now()).getYears()>=18){
            this.birthday = birthday;
        }else{
            throw new UnderAgeException();
        }
    }

    /**
     * GETTER QUESTION
     * @return THE QUESTION
     */
    public String getQuestion() {
        return question;
    }

    /**
     * SETTER QUESTION
     * @param question THE NEW QUESTION
     */
    public void setQuestion(String question) throws LengthQuestionException {
        if(question.length()<=100){
            this.question = question;
        }else{
            throw new LengthQuestionException();
        }
    }

    /**
     * GETTER ANSWER
     * @return THE ANSWER
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * SETTER ANSWER
     * @param answer THE NEW ANSWER
     */
    public void setAnswer(String answer) throws LengthAnswerException {
        if(answer.length()<=100){
            this.answer = answer;
        }else{
            throw new LengthAnswerException();
        }
    }
}
