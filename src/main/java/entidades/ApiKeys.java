package entidades;

import javax.persistence.*;

@Entity
@Table(name = "ApiKeys")
public class ApiKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apikey",nullable = false,unique = true, length = 40)
    private String apikey;

    @Column(name = "numberUses")
    private int numberUses;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "isReading")
    private boolean isReading;

    @Column(name = "isCreate")
    private boolean isCreate;

    @Column(name = "isDelete")
    private boolean isDelete;

    @Column(name = "isModify")
    private boolean isModify;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getNumberUses() {
        return numberUses;
    }

    public void setNumberUses(int numberUses) {
        this.numberUses = numberUses;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isReading() {
        return isReading;
    }

    public void setReading(boolean reading) {
        isReading = reading;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }
}
