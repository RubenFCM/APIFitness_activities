package dto;

import com.google.gson.annotations.Expose;

public class ExercisesDTO {
    @Expose
    private String name, url_image;
    @Expose
    private Integer difficultyLevel;

    public ExercisesDTO() {
    }

    public ExercisesDTO(String name, String url_image, Integer difficultyLevel) {
        this.name = name;
        this.url_image = url_image;
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return "ExercisesDTO{" +
                "name='" + name + '\'' +
                ", url_image='" + url_image + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                '}';
    }
}
