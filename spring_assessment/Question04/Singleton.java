package assessment.spring_assessment.Question04;

public class Singleton {
    public static void main(String[] args) {
        EagerLoading eagerLoading=EagerLoading.getInstance("Url","root","1234");
        LazyLoading lazyLoading=LazyLoading.getInstance("Url","root","1234");



//        In object-oriented programming, a singleton class is a class that can have only one
//                object (an instance of the class) at a time. After the first time, if we try to
//        instantiate the Singleton class, the new variable also points to the first instance created.

    }
}
