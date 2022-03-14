package assessment.spring_assessment.Question2;

import javax.persistence.*;

@Entity(name = "add_friend")
@Table(name = "friends")
public class AddFriend {
    @Id
    private int id;
    @ManyToMany(cascade = CascadeType.ALL)
    private User fbUser;
    @ManyToMany(cascade = CascadeType.ALL)
    @Column(name = "friend_id")
    private User friendUser;


}