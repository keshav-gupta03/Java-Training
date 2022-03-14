package assessment.spring_assessment.Question2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MainDriver {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainDriver.class,args);
    }
}