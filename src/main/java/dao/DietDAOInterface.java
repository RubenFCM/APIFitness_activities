package dao;

import entidades.Diet;

import java.util.List;

public interface DietDAOInterface {

    Diet searchById(Long id);

    Diet createDiet(Diet diet);

    List <Diet> showAll();

    Diet updateDietByID(Diet diet);

    boolean deleteDietByID(Long id);

}
