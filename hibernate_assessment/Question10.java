package hibernate_assessment;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Question10 {
    public static void main(String[] args) {
        Configuration configuration=new Configuration().configure();
        configuration.addAnnotatedClass(Product2.class);
        configuration.addAnnotatedClass(Store.class);
        SessionFactory sessionFactory= configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        Store store=new Store();
        store.setAddress("indore");
        store.setName("General Store");

        Product2 product=new Product2();
        product.setName("Tv");
        product.setStore(store);
        session.persist(product);
        transaction.commit();
        session.close();
    }
}
