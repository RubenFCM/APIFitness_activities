package dao;

import entidades.Country;
import entidades.Exercises;
import entidades.Users;

import java.util.List;

public interface UsersDAOInterface {

    Users createUser(Users users);

    List<Users> showAll();

    Users updateUserID(Users users);

    Users searchByID(Long id);
    boolean deleteUserByID(Long id);
}
