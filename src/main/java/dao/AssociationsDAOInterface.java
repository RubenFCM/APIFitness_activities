package dao;

import entidades.*;

import java.util.List;

public interface AssociationsDAOInterface {
    // RELACION 1 a M o M a 1 ------------------------------

    List<TrainingRecords> trainingsUser(Users u);

    Users showUserPerformedTraining(TrainingRecords trainingRecords);

    Exercises showExerciseUsedTraining(TrainingRecords trainingRecords);

    List<TrainingRecords> traiginsExercise(Exercises e);

    Country showCountryUser(Users u);

    List<Users> showUsersCountry(Country c);

    boolean assignCountryToUser(Country c, Users u);


    //----------------------RELACION M a N --------------

    Users updateUserID(Users users, Country country);

    List<Users> usersWithDiet(Diet d);

    List<Diet> dietOfUser(Users u);

    boolean assignDietToUser(Diet d, Users u);

    TrainingRecords updateTrainingtByID(TrainingRecords trainingRecord,Users u, Exercises e);
    TrainingRecords createTraining (TrainingRecords trainingRecord,Users u, Exercises e);

}
