package project_day21;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TweetDaoImpl implements TweetDao {
    @Autowired
    private Session session;

    @Override
    public void createTweet(Tweet tweet) {
        session.beginTransaction();
        session.persist(tweet);
        session.getTransaction().commit();

    }

    @Override
    public List<Tweet> readAll() {
        return  session.createQuery("from Tweet", Tweet.class).getResultList();
    }
}
