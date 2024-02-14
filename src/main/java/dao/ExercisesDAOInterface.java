package dao;


import dto.ExercisesDTO;
import entidades.Exercises;

import java.util.List;

public interface ExercisesDAOInterface {
    List<Exercises> showAll();

    List<Exercises> showAll(int page ,int size);

    List<Exercises> showSortedDifficulty();

    List<String> showAllImages();

    List<ExercisesDTO> showNamesImages();

    List<ExercisesDTO> showNamesImagesDifficulty(Integer difficulty);

    Long totalExercises();

    List<Exercises> searchNameLike(String name);

    List<Exercises> filterMuscleGroup(String muscleGroup);

    List<Exercises> filterMuscleGroups(List<String> listMuscleGroups);

    Exercises create(Exercises exercises);

    Exercises updateByID(Exercises exercises);

    boolean deleteById(Long id);

    boolean deleteName(String name);

    Exercises searchByID(Long id);

}
