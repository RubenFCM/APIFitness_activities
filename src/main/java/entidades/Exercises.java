package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Exercises")
public class Exercises implements Serializable {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Name",nullable = false,unique = true, length = 60)
    @Expose
    private String name;

    @Column(name = "Muscle_Group",nullable = false,length = 60)
    @Expose
    private String muscle_group;

    @Column(name = "Creation_Date")
    @Expose
    private LocalDate creation_date;

    @Column(name = "Image")
    @Expose
    private String url_image;

    @Column(name = "Description")
    @Expose
    private String description;

    @Column(name = "Difficulty_Level", nullable = false)
    @Expose
    private Integer difficulty_level;

    @OneToMany(mappedBy = "exercise",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TrainingRecords> trainingRecordsList = new ArrayList<>();

    public List<TrainingRecords> getTrainingRecordsList() {
        return trainingRecordsList;
    }

    public void setTrainingRecordsList(List<TrainingRecords> trainingRecordsList) {
        this.trainingRecordsList = trainingRecordsList;
    }

    // constructor
    public Exercises(){}

    public Exercises(Long id, String name, String muscle_group, LocalDate creation_date, String url_image, String description, Integer difficulty_level) {
        this.id = id;
        this.name = name;
        this.muscle_group = muscle_group;
        this.creation_date = creation_date;
        this.url_image = url_image;
        this.description = description;
        this.difficulty_level = difficulty_level;
    }

    //getters y setters

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

    public String getMuscle_group() {
        return muscle_group;
    }

    public void setMuscle_group(String muscle_group) {
        this.muscle_group = muscle_group;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(Integer difficulty_level) {
        this.difficulty_level = difficulty_level;
    }


    // ToString


    @Override
    public String toString() {
        return "Exercises{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", muscle_group='" + muscle_group + '\'' +
                ", creation_date=" + creation_date +
                ", url_image='" + url_image + '\'' +
                ", description='" + description + '\'' +
                ", difficulty_level=" + difficulty_level +
                '}';
    }
}
