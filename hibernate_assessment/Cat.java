package hibernate_assessment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cat {
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(strategy = "increment", name="custom-generator")
    private int id;
    private String name;
    private float weight;
    private int age;

    public Cat(){

    }

    public Cat(String name, float weight, int age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                '}';
    }
}
