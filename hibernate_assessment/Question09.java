package hibernate_assessment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Question09 {
    public static void main(String[] args) {
        Configuration configuration=new Configuration().configure().addAnnotatedClass(Cat.class);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        Root<Cat> root = criteriaQuery.from(hibernate_assessment.Cat.class);
        Predicate nameLike=criteriaBuilder.like(root.get("name"),"m%");
        Predicate greaterThanWeight = criteriaBuilder.gt(root.get("weight"),2);
        criteriaQuery.select(root).where(criteriaBuilder.and(nameLike,greaterThanWeight)).orderBy(criteriaBuilder.asc(root.get("age")));
        List<Cat> List=session.createQuery(criteriaQuery).getResultList();
        for(Cat list:List)
            System.out.println(list);
    }

    private static void insertNewRcord() {
        Configuration configuration=new Configuration().configure().addAnnotatedClass(Cat.class);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();

        Cat cat=new Cat("Max.",4.4f,4);
        session.persist(cat);
        transaction.commit();
        session.close();
    }
}
