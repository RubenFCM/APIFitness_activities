package dao;

import entidades.Diet;
import entidades.Exercises;
import entidades.TrainingRecords;
import entidades.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;


public class AssociationsDAO implements AssociationsDAOInterface{

    @Override
    public List<TrainingRecords> trainingsUser(Users u) {
        List<TrainingRecords> list_training = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Users> query =session.createQuery("select u from Users u join fetch u.trainingRecordsList where u.id =:id", Users.class);
            query.setParameter("id", u.getId());
            list_training = query.getSingleResult().getTrainingRecordsList();
        }catch (NoResultException | NullPointerException nre){
            nre.printStackTrace();
            return  null;
        }
        session.close();
        return  list_training;
    }

    @Override
    public Users showUserPerformedTraining(TrainingRecords trainingRecords) {
        Users user = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<TrainingRecords> query = session.createQuery("select tr from TrainingRecords tr join fetch tr.user where tr.id =:id", TrainingRecords.class);
            query.setParameter("id",trainingRecords.getId());
            user = query.getSingleResult().getUser();
        }catch (NoResultException | NullPointerException nre){
            nre.printStackTrace();
            return null;
        }
        session.close();
        return user;
    }

    @Override
    public Exercises showExerciseUsedTraining(TrainingRecords trainingRecords) {
        Exercises exercise = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<TrainingRecords> query = session.createQuery("select tr from TrainingRecords tr join fetch tr.exercise where tr.id =:id", TrainingRecords.class);
            query.setParameter("id",trainingRecords.getId());
            exercise = query.getSingleResult().getExercise();
        }catch (NoResultException | NullPointerException nre){
            nre.printStackTrace();
            return null;
        }
        session.close();
        return exercise;
    }

    @Override
    public List<TrainingRecords> traiginsExercise(Exercises e) {
        List<TrainingRecords> list_training = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Exercises> query =session.createQuery("select e from Exercises e join fetch e.trainingRecordsList where e.id =:id", Exercises.class);
            query.setParameter("id", e.getId());
            list_training = query.getSingleResult().getTrainingRecordsList();
        }catch (NoResultException | NullPointerException nre){
            nre.printStackTrace();
            return  null;
        }
        session.close();
        return  list_training;
    }


    @Override
    public List<Users> usersWithDiet(Diet d) {
        List<Users> list_users = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Diet> query = session.createQuery("select d from Diet d join fetch d.users where d.id =:id", Diet.class);
            query.setParameter("id",d.getId());
            list_users = query.getSingleResult().getUsers();
        }catch (NoResultException nre){
            nre.printStackTrace();
            return  new ArrayList<>();
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
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
            nre.printStackTrace();
            return new ArrayList<>();
        }catch ( NullPointerException e){
            e.printStackTrace();
            return null;
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
    @Override
    public TrainingRecords createTraining(TrainingRecords trainingRecord,Users u, Exercises e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trainingRecord.setUser(u);
            trainingRecord.setExercise(e);
            session.beginTransaction();
            session.save(trainingRecord);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return trainingRecord;
    }
    @Override
    public TrainingRecords updateTrainingtByID(TrainingRecords trainingRecord,Users u, Exercises e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trainingRecord.setUser(u);
            trainingRecord.setExercise(e);
            session.beginTransaction();
            session.update(trainingRecord);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return trainingRecord;
    }
}
