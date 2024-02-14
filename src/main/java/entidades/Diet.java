package entidades;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Diet")
public class Diet {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Description")
    @Type(type="text")
    @Expose
    private String description;

    @Column(name = "Calories")
    @Expose
    private double calories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_diet",
        joinColumns = @JoinColumn(name ="Diet_id"),
        inverseJoinColumns =@JoinColumn(name = "User_id")
    )
    private List<Users> users =new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
