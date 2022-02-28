package hibernate_assessment;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "product2")
public class Product2 {
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(strategy = "increment", name="custom-generator")
    private int id;
    @Column
    private String name;


    @ManyToOne(cascade = CascadeType.ALL)
    private Store store;

    public Product2() {
    }

    public Product2(int id, String name, Store store) {
        this.id = id;
        this.name = name;
        this.store = store;
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


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
