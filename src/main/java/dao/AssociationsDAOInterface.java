package dao;

import entidades.Diet;
import entidades.Exercises;
import entidades.TrainingRecords;
import entidades.Users;

import java.util.List;

public interface AssociationsDAOInterface {
    // RELACION 1 a M o M a 1 ------------------------------

    List<TrainingRecords> trainingsUser(Users u);

    Users showUserPerformedTraining(TrainingRecords trainingRecords);

    Exercises showExerciseUsedTraining(TrainingRecords trainingRecords);

    List<TrainingRecords> traiginsExercise(Exercises e);

    //----------------------RELACION M a N --------------

    List<Users> usersWithDiet(Diet d);

    List<Diet> dietOfUser(Users u);

    boolean assignDietToUser(Diet d, Users u);

}
