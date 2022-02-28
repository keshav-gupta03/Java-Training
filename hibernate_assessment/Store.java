package hibernate_assessment;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String address;
    @OneToMany(mappedBy = "Store" ,cascade =CascadeType.ALL)
    private Set<Product2> products= new HashSet<>();



    public Store(){

    }

    public Set<Product2> getProducts() {
        return products;
    }

    public void setProducts(Set<Product2> products) {
        this.products = products;
    }

    public Store(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
