package dao;

import entidades.Diet;
import entidades.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AssociationsDAO implements AssociationsDAOInterface{

    @Override
    public List<Users> usersWithDiet(Diet d) {
        List<Users> list_users = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Diet> query = session.createQuery("select d from Diet d join fetch d.users where d.id =:id", Diet.class);
            query.setParameter("id",d.getId());
            list_users = query.getSingleResult().getUsers();
        }catch (NoResultException nre){
            list_users = new ArrayList<>();
        }
        session.close();
        return list_users;
    }

    @Override
    public List<Diet> dietOfUser(Users u) {
        List<Diet> list_diets = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Users> query = session.createQuery("select u from Users u join fetch u.diets where u.id =:id", Users.class);
            query.setParameter("id",u.getId());
            list_diets = query.getSingleResult().getDiets();
        }catch (NoResultException nre){
            list_diets = new ArrayList<>();
        }
        session.close();
        return  list_diets;
    }

    @Override
    public boolean assignDietToUser(Diet d, Users u) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Diet> list_diets = dietOfUser(u);
            list_diets.add(d);
            u.setDiets(list_diets);

            List<Users> list_users = usersWithDiet(d);
            list_users.add(u);
            d.setUsers(list_users);

            session.beginTransaction();

            session.update(d);
            session.update(u);
            session.getTransaction().commit();

        }catch (PersistenceException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
        session.close();
        return true;
    }
}
