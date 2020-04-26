package com.project.classes;

import com.project.database.DataBaseUtils;
import com.project.exceptions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserDao {
    private User user;

    /**
     * CONSTRUCTOR THAT GET THE USER FROM THE DATABASE
     * @param email The primary key
     * @param password The password to check the autentication
     * @throws SQLException If the User is not found
     */
    public UserDao(String email, String password) throws SQLException, LengthQuestionException, EmailInvalidException, LengthAnswerException, PasswordInvalidException, UsernameLengthException, UnderAgeException {
        try( Connection connection = DataBaseUtils.createConnection() ){
            Statement statement =connection.createStatement();
            //La palabra secreta para desencriptar el password para ser segura, tendria que estar en un servidor y apartado de la ddbb por si es comprometida, para que nadie la pudiera user, mientras el programa esta en version de desarrollo y sin mas acto que el educativo, no se usara un servidor ajeno al local por lo que se usara este metodo
            ResultSet userSecret = statement.executeQuery("SELECT answer FROM "+DataBaseUtils.nameTableUser+"WHERE email='"+email+"'");
            userSecret.next();
            ResultSet userFound = statement.executeQuery(
                    "SELECT * FROM "+DataBaseUtils.nameTableUser+"WHERE email='"+email+"' AND password= AES_ENCRYPT('"+password+"', '"+userSecret.getString(0)+"')"
            );
            userSecret.close();
            userFound.next();
            this.user = new User(
                    userFound.getString(0),
                    userFound.getString(1),
                    userFound.getString(2),
                    userFound.getDate(3).toLocalDate(),
                    userFound.getString(4),
                    userFound.getString(5)
            );
            userFound.close();
            statement.close();
        }


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
    public UserDao(String username, String password, String email, LocalDate birthday, String question, String answer) throws SQLException, LengthQuestionException, EmailInvalidException, LengthAnswerException, PasswordInvalidException, UsernameLengthException, UnderAgeException, EmailUsedException {
        this.user = new User(username, password,email,birthday,question,answer);
        try(Connection connection = DataBaseUtils.createConnection()){

            System.out.println("Conected OKKKKKKKKKKKKKKKKKK");
            connection.createStatement().execute(
                    "INSERT INTO "+DataBaseUtils.nameTableUser+" VALUES " +
                            "( '"+user.getUsername()+"', AES_ENCRYPT('"+user.getPassword()+"', '"+user.getAnswer()+"'), '"+user.getEmail()+"','"+user.getBirthday()+"',\""+user.getQuestion()+"\",'"+user.getAnswer()+"');"
            );
            System.out.println("INSERT OKKKKKKKKKKKKKKKKKKKKKKKKK");
        }catch (SQLException ex){
            throw new EmailUsedException();
        }
    }

    /**
     * GETTER USERMANE
     * @return THE USERNAME
     */
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * SETTER USERNAME
     * @param username THE USERNAME
     * @throws SQLException
     */
    public void setUsername(String username) throws SQLException, UsernameLengthException {
        this.user.setUsername(username);
        updateCamp("username",username);

    }

    /**
     * GETTTER PASSWORD
     * @return THE PASSWORD
     */
    public String getPassword() {
        return this.user.getPassword();
    }

    public void setPassword(String password) throws SQLException, PasswordInvalidException {
        this.user.setPassword(password);
        updateCamp("password", password);
    }

    /**
     * GETTER EMAIL
     * @return THE EMAIL
     */
    public String getEmail(){
        return this.user.getEmail();
    }

    /**
     * SETTER EMAIL
     * @param email THE EMAIL
     * @throws SQLException
     */
    public void setEmail(String email) throws SQLException, EmailInvalidException {
        this.user.setEmail(email);
        updateCamp("email", email);
    }

    /**
     * GETTER BIRTHDAY
     * @return THE BIRTHDAY
     */
    public LocalDate getBirthday() {
        return this.user.getBirthday();
    }

    /**
     * SETTER BIRTHDAY
     * @param birthday THE NEW BIRTHDAY
     * @throws SQLException
     */
    public void setBirthday(LocalDate birthday) throws SQLException, UnderAgeException {
        this.user.setBirthday(birthday);
        try(Connection connection = DataBaseUtils.createConnection()){
            connection.createStatement().executeUpdate(
                    "UPDATE "+DataBaseUtils.nameTableUser+" SET birthday='"+birthday+"' WHERE email='"+this.user.getEmail()+"'"
            );
            connection.commit();
        }
    }

    /**
     * GETTER QUESTION
     * @return THE QUESTION
     */
    public String getQuestion() {
        return this.user.getQuestion();
    }

    /**
     * SETTER QUESTION
     * @param question THE NEW QUESTION
     */
    public void setQuestion(String question) throws SQLException, LengthQuestionException {
        this.user.setQuestion(question);
        updateCamp("question", question);
    }

    /**
     * GETTER ANSWER
     * @return THE ANSWER
     */
    public String getAnswer() {
        return this.user.getAnswer();
    }

    /**
     * SETTER ANSWER
     * @param answer THE NEW ANSWER
     */
    public void setAnswer(String answer) throws SQLException, LengthAnswerException {
        this.user.setAnswer(answer);
        updateCamp("answer", answer);
    }

    /**
     * METHOD TO UPDATE ONE FIELD IN THE DATABASE, ONLY STRING CAMPS
     * @param field THE FIELD
     * @param modify THE NEW VALUE
     * @throws SQLException ERROR IN DATABASE
     */
    private void updateCamp(String field, String modify) throws SQLException {
        try(Connection connection = DataBaseUtils.createConnection()){
            connection.createStatement().executeUpdate(
                    "UPDATE "+DataBaseUtils.nameTableUser+" SET "+field+"='"+modify+"' WHERE email='"+this.user.getEmail()+"'"
            );
            connection.commit();
        }
    }
}
