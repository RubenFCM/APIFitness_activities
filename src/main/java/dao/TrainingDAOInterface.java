package dao;

import entidades.Diet;
import entidades.TrainingRecords;

import java.util.List;

public interface TrainingDAOInterface {
    TrainingRecords searchById(Long id);

    List<TrainingRecords> showAll();

    TrainingRecords createTraining (TrainingRecords trainingRecord);

    TrainingRecords updateTrainingtByID(TrainingRecords trainingRecord);

    boolean deleteTrainingByID(Long id);

}
