package hibernate_assessment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class Question08 {
    public static void main(String[] args) {


//        Object[] obj= session.createQuery(" select userid, max(totalmessage) as totalMessage from (select userid, count(userid) as totalmessage from user group by userid) as totalmessage ",Object[].class).getSingleResult();
//        System.out.println(obj);

        maxMessage();
    }

    public static void maxMessage(){
        Configuration  configuration=new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        List<Object[]> object=session.createQuery("select userid, count(message) from User group by userid order by userid",Object[].class).getResultList();
        Long max = 0l;
        Object userId = "";


        for (int i = 0 ; i < object.size() ; i++){
            Object[] arr = object.get(i);
            Long messages = (Long) arr[1];
            if(max < messages) {
                max = messages;
                userId = arr[0];
            }
        }


        System.out.print("UserId       ");
        System.out.print("Total Message " );
        System.out.println();
        System.out.print(userId+"                ");
        System.out.print(max);


    }

    public static void insertNewUser(){
        Configuration  configuration=new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        Session session= sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();

        User user= new User();
        user.setUserid(1);
        user.setMessage("message-9");

        session.persist(user);
        transaction.commit();
        session.close();
    }
}
