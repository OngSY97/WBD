package my.edu.tarc.wbd.Model;

/**
 * Created by S3113 on 9/1/2018.
 */

public class Account {
    private String email,password,name;
    private int userID;

    public Account() {
    }

    public Account(int userID, String name , String email, String password ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }
    public int getUserID(){
        return userID;
    }
    public String toString() {
        return super.toString();
    }
}
