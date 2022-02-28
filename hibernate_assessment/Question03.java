package hibernate_assessment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Question03 {
    public static void main(String[] args) {
        /*
        the name of the hibernate mapping file -> Class_name.hbm.xml

         */
        Configuration configuration=new Configuration().configure();
//        With xml configuration
        configuration.addResource("Student.hbm.xml");

//        with annoted class
//        configuration.addAnnotatedClass(hibernate_assessment.StudentAnnoted.class);

        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        Student s1=new Student("keshav",2894,"rgpv");
        Student s2= new Student("madhav",2895,"davv");

        session.persist(s1);
        session.persist(s2);
        transaction.commit();
        session.close();
    }
}
