package hibernate_assessment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Question04 {
    /*
    CRUD stands for->
    C -> CREATE
    R -> READ
    U -> UPDATE
    D -> DELETE
     */

    public static void main(String[] args) {


        List<Student> list=getList();
        for(Object obj:list)
            System.out.println(obj);
       updateAnyRecord();

    }

    public  static  void insertNewRecord(){
        Configuration configuration=new Configuration().configure();
        configuration.addResource("Student.hbm.xml");
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
    public static List<Student> getList(){
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addResource("Student.hbm.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session=sessionFactory.openSession();
        List<Student> personList = session.createQuery("from Student", Student.class).getResultList();
        return personList;
    }

    public static void deleteAnyRecord(){
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addResource("Student.hbm.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session=sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        Student s=session.get(Student.class,97968);
        session.remove(s);
        transaction.commit();
        session.close();
    }

    public static void updateAnyRecord(){
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addResource("Student.hbm.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session=sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        Student s= session.load(Student.class,66534);
        s.setName("Keshav Gupta");
        s.setRollno(101);
        session.persist(s);
        transaction.commit();
        session.close();

    }
}
