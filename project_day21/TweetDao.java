package project_day21;


import java.util.List;

public interface TweetDao {
    void createTweet(Tweet tweet);
    List<Tweet> readAll();
}
