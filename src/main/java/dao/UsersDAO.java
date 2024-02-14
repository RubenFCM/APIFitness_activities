package dao;


import entidades.Users;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;

public class UsersDAO implements UsersDAOInterface{

    @Override
    public Users create(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            users.setRegistration_date(LocalDate.now());
            double imc = users.getWeight() / Math.pow(users.getHeight(),2);
            users.setImc((double) Math.round(imc * 10) / 10);
            session.save(users);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return users;
    }

    @Override
    public Users updateUserID(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            double imc = users.getWeight() / Math.pow(users.getHeight(),2);
            users.setImc((double) Math.round(imc * 10) / 10);
            session.update(users);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return users;
    }

    @Override
    public List<Users> showAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Users> allUsers = session.createQuery("from Users", Users.class).list();
        session.close();

        return allUsers;
    }

    @Override
    public Users searchByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Users user = session.find(Users.class, id);
        session.close();

        return user;
    }
}
