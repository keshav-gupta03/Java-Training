package assessment.spring_assessment.Question2;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UserImplement implements Dao<User>{
    @Autowired
    Session session;

    @Override
    public List<User> readAll() {
        return session.createQuery("from fb_user",User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
    }

    @Override
    public User readById(int id) {
        Query query =session.createQuery("from fb_user where id=:id");
        query.setParameter("id",id);
        User fbUser = (User) query.getSingleResult();
        return fbUser;
    }
    @Override
    public User readByEmail(String entity) {
        Query query =session.createQuery("from fb_user where email=:email");
        query.setParameter("email",entity);
        User fbUser = (User) query.getSingleResult();
        return fbUser;
    }

    @Override
    public Boolean update(User user) {
        String hql = "UPDATE Userreg SET uname = :uname, uemail = :uemail, uphone = :uphone WHERE uemail = :uemail";
        session.beginTransaction();
        Query query = session.createQuery("UPDATE fb_user SET name=:name, password=:password  WHERE email=:email");
        query.setParameter("email",user.getEmail());
        query.setParameter("name",user.getName());
        query.setParameter("password",user.getPassword());
        int rr = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("rr :"+rr);
        if (rr !=0)
            return true;
        return false;
    }

    @Override
    public void delete(User entity) {
        session.beginTransaction();
        session.remove(entity);
        session.getTransaction().commit();
    }
}