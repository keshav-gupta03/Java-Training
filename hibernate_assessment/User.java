package hibernate_assessment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(strategy = "increment", name="custom-generator")
    private int id;

    private int userid;
    private String message;

    public User(){

    }

    public User(int id, int userid, String message) {
        this.id = id;
        this.userid = userid;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }


}
