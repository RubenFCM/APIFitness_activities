package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TrainingRecords")
public class TrainingRecords implements Serializable {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="User_id",nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Exercise_id",nullable = false, foreignKey = @ForeignKey(name = "fk_exercise_id"))
    private Exercises exercise;

    @Column(name = "Datef")
    @Expose
    private LocalDate date;

    @Column(name = "Time_spent",nullable = false)
    @Expose
    private Integer time_spent;

    @Column(name = "Number_series")
    @Expose
    private Integer number_series;

    @Column(name = "Number_repetition")
    @Expose
    private Integer number_repetition;

    @Column(name = "Calories_burned")
    @Expose
    private Double calories_burned;

    public TrainingRecords() {
    }

    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(Integer time_spent) {
        this.time_spent = time_spent;
    }

    public Integer getNumber_series() {
        return number_series;
    }

    public void setNumber_series(Integer number_series) {
        this.number_series = number_series;
    }

    public Integer getNumber_repetition() {
        return number_repetition;
    }

    public void setNumber_repetition(Integer number_repetition) {
        this.number_repetition = number_repetition;
    }

    public Double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(Double calories_burned) {
        this.calories_burned = calories_burned;
    }
}
