package hibernate_assessment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Question07 {
    public static void main(String[] args) {
        Configuration configuration=new Configuration().configure();
        configuration.addAnnotatedClass(Product.class);
        SessionFactory sessionFactory= configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        Product product1=new Product();
        product1.setName("laptop");
        product1.setPrice(40000);

        Product product2=new Product();
        product2.setName("Mobile");
        product2.setPrice(20000);

        Product product3=new Product();
        product3.setName("Tv");
        product3.setPrice(3000);

        session.persist(product1);
        session.persist(product2);
        session.persist(product3);

        transaction.commit();
        session.close();

    }
}
