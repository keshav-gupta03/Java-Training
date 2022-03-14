package assessment.spring_assessment.Question06;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetDaoImpl implements TweetDao {
    @Autowired
    private Session session;

    @Override
    public void createTweet(Tweet tweet) {
        session.beginTransaction();
        session.persist(tweet);

    }
}
