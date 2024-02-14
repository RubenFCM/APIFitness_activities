package dao;

import entidades.Exercises;
import entidades.Users;

import java.util.List;

public interface UsersDAOInterface {

    Users create(Users users);

    List<Users> showAll();

    Users updateUserID(Users users);

    Users searchByID(Long id);
}
