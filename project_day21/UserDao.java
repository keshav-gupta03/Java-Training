package project_day21;

import java.util.List;

public interface UserDao {
    List<User> readAll();
    void create(User user);
    User readById(int id);
    int update(User user);
    void delete(User user);
    List<Tweet> getTweetByEmail(String email);
    void createTweet(Tweet tweet);
    List<Tweet> getAllTweets();

}
