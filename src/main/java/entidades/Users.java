package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
public class Users implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Name",nullable = false, length = 60)
    @Expose
    private String name;

    @Column(name = "LastName",nullable = false,length = 60)
    @Expose
    private String last_name;

    @Column(name = "Email",nullable = false, unique = true)
    @Expose
    private String email;

    @Column(name = "Registration_Date", nullable = true)
    @Expose
    private LocalDate registration_date;

    @Column(name = "Weight")
    @Expose
    private Double weight;

    @Column(name = "Height")
    @Expose
    private Double height;

    @Column(name = "Photo", nullable = true)
    @Expose
    private String photo;

    @Column(name = "IMC", nullable = true)
    @Expose
    private Double imc;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<TrainingRecords> trainingRecordsList = new ArrayList<>();

    //Relacion entre los usuarios y dietas
    @ManyToMany(mappedBy = "users")
    private List<Diet> diets = new ArrayList<>();

    public List<TrainingRecords> getTrainingRecordsList() {
        return trainingRecordsList;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public void setDiets(List<Diet> diets) {
        this.diets = diets;
    }

    public void setTrainingRecordsList(List<TrainingRecords> trainingRecordsList) {
        this.trainingRecordsList = trainingRecordsList;
    }

    public Users(Long id, String name, String last_name, String email, LocalDate registration_date, Double weight, Double height, String photo, Double imc) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.registration_date = registration_date;
        this.weight = weight;
        this.height = height;
        this.photo = photo;
        this.imc = imc;
    }

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", registration_date=" + registration_date +
                ", weight=" + weight +
                ", height=" + height +
                ", photo='" + photo + '\'' +
                ", imc=" + imc +
                '}';
    }
}
