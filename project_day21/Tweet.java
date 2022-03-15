package project_day21;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String email;
    @Column
    private String tweet;
    @Transient
    private String name;


    @Transient
    private String password;
    private LocalDateTime localDateTime;

    Tweet(){

    }
    public Tweet(String name, String email, String tweet, LocalDateTime localDateTime) {
        this.name = name;
        this.email = email;
        this.tweet = tweet;
        this.password=password;
        this.localDateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tweet='" + tweet + '\'' +
                ", password='" + password + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
