package dao;

import entidades.Diet;

import java.util.List;

public interface DietDAOInterface {

    Diet searchById(Long id);
    Diet createDiet(Diet diet);
    List <Diet> showAll();
//    List <Diet> showAll(int page, int size);

    Diet updateDietByID(Diet diet);
    boolean deleteDietByID(Long id);
//    Long totalDiets();
}
