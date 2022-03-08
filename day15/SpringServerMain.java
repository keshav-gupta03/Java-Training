package day15;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringServerMain {
    public static void main(String[] args) {
        ApplicationContext context= SpringApplication.run(SpringServerMain.class , args);
    }

}
