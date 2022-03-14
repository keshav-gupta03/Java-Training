package assessment.spring_assessment.Question2;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.Query;
import java.util.List;

@Component
public class PostImplement implements Dao<Post> {
    @Autowired
    private Session session;

    @Override
    public List<Post> readAll() {
        return session.createQuery("from fb_post", Post.class).getResultList();
    }

    @Override
    public void create(Post entity) {
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
    }

    @Override
    public Post readById(int id) {
        return session.createQuery("from fb_post where id="+id,Post.class).getSingleResult();
    }

    @Override
    public Boolean update(Post entity) {
        session.beginTransaction();
        Query query = session.createQuery("UPDATE fb_post SET post=:post, type=:type  WHERE id=:id");
        query.setParameter("id",entity.getId());
        query.setParameter("post",entity.getPost());
        query.setParameter("type",entity.getType());
        int rr = query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("rr :"+rr);
        if (rr !=0)
            return true;
        return false;
    }

    @Override
    public void delete(Post entity) {
        session.beginTransaction();
        session.remove(entity);
        session.getTransaction().commit();
    }

    @Override
    public Post readByEmail(String entity) {
        return null;
    }

}