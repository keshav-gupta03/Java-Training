package project_day21;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private Session session;

    @Override
    public List<User> readAll() {
        return session.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void create(User user) {
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    @Override
    public User readById(int id) {
        Query query = session.createQuery("from User where id=:id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();

    }

    @Override
    public int update(User user) {
        session.beginTransaction();
        Query q=session.createQuery("update User set name=:n , password=:p where email=:e");
        String n= user.getName();
        String e=user.getEmail();
        String p=user.getPassword();
        q.setParameter("n",n);
        q.setParameter("e",e);
        q.setParameter("p",p);

//        session.persist(user);
        int status =q.executeUpdate();
        session.getTransaction().commit();
        return status;
    }

    @Override
    public void delete(User user) {
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<Tweet> getTweetByEmail(String email) {
        Query query=session.createQuery("from Tweet where email=:email", Tweet.class);
        query.setParameter("email",email);
        return query.getResultList();
    }

    @Override
    public void createTweet(Tweet tweet) {
        Transaction transaction = session.beginTransaction();
        session.persist(tweet);
        transaction.commit();
    }

    @Override
    public List<Tweet> getAllTweets() {
        return  session.createQuery(" from Tweet ", Tweet.class).getResultList();
    }
}