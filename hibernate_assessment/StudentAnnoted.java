package hibernate_assessment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class StudentAnnoted {
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(strategy = "increment", name="custom-generator")
    private int id;
    @Column(nullable = false)
    private int rollno;
    @Column(nullable = false)
    private String name;
    private String university;

}
