package dao;

import entidades.Diet;
import entidades.Users;

import java.util.List;

public interface AssociationsDAOInterface {
    // RELACION 1 a M o M a 1 ------------------------------
//    boolean asignarProveedor(Mueble m, Proveedor p);
//
//    Proveedor obtenerProvedorMueble(Mueble m);
//
//    List<Mueble> mueblesProveedor(Proveedor p);


    //----------------------RELACION M a N --------------

    List<Users> usersWithDiet(Diet d);

    List<Diet> dietOfUser(Users u);

    boolean assignDietToUser(Diet d, Users u);

}
