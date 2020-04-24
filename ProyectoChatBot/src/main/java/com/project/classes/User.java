package com.project.classes;

import com.project.database.DataBaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class User {
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private String question;
    private String answer;

    /**
     * CONSTRUCTOR THAT GET THE USER FROM THE DATABASE
     * @param email The primary key
     * @param password The password to check the autentication
     * @throws SQLException If the User is not found
     */
    public User(String email, String password) throws SQLException {
        Statement statement = DataBaseUtils.createStatement();
        //La palabra secreta para desencriptar el password para ser segura, tendria que estar en un servidor y apartado de la ddbb por si es comprometida, para que nadie la pudiera user, mientras el programa esta en version de desarrollo y sin mas acto que el educativo, no se usara un servidor ajeno al local por lo que se usara este metodo
        ResultSet userSecret = statement.executeQuery("SELECT answer FROM "+DataBaseUtils.nameTableUser+"WHERE email='"+email+"'");
        userSecret.next();
        ResultSet userFound = statement.executeQuery(
                "SELECT * FROM "+DataBaseUtils.nameTableUser+"WHERE email='"+email+"' AND password= AES_ENCRYPT('"+password+"', '"+userSecret.getString(0)+"')"
        );
        userSecret.close();
        userFound.next();
        this.username = userFound.getString(0);
        this.password = userFound.getString(1);
        this.email = userFound.getString(2);
        this.birthday = userFound.getDate(3);
        this.question = userFound.getString(4);
        this.answer = userFound.getString(5);
        userFound.close();
        statement.close();
    }

    /**
     * CONSTRUCTOR OF THE CLASS THAN CREATE A NEW USERENTERPRISE ON THE DATABASE
     * @param username THE NAME
     * @param password THE PASSWORD
     * @param email THE EMAIL
     * @param birthday THE BIRTHDAY
     * @param question THE QUESTION
     * @param answer THE ANSWER
     */
    public User(String username, String password, String email, Date birthday, String question, String answer) throws SQLException {
        DataBaseUtils.createStatement().execute(
                "INSERT INTO "+DataBaseUtils.nameTableUser+" VALUES " +
                        "( '"+username+"', AES_ENCRYPT('"+password+"', '"+answer+"'), '"+email+"','"+birthday+"','"+question+"','"+answer+"',)"
        );

        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.question = question;
        this.answer = answer;

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
    public void setUsername(String username) throws SQLException {
        DataBaseUtils.createStatement().executeUpdate(
                "UPDATE "+DataBaseUtils.nameTableUser+" SET username='"+username+"' WHERE username='"+this.email+"'"
        );
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
