package dao;


import dto.ExercisesDTO;
import entidades.Exercises;
import entidades.TrainingRecords;
import entidades.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class ExercisesDAO implements ExercisesDAOInterface {


    @Override
    public Exercises create(Exercises exercises) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(exercises);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return exercises;
    }

    @Override
    public Exercises updateByID(Exercises exercise) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(exercise);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return exercise;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Exercises exercise = this.searchByID(id);
            if (exercise!=null){
                session.delete(exercise);
            }else {
                return false;
            }
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM Exercises E WHERE E.name = :name";
            Query<Exercises> query = session.createQuery(hql, Exercises.class);
            query.setParameter("name", name);
            Exercises exercise = query.uniqueResult();
            if (exercise != null) {
                session.delete(exercise);
            } else {
                return false;
            }
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public Exercises searchByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Exercises exercise = session.find(Exercises.class, id);
        session.close();

        return exercise;
    }

    @Override
    public List<Exercises> showAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Exercises> todos = session.createQuery("from Exercises", Exercises.class).list();
        session.close();

        return todos;
    }

    @Override
    public List<Exercises> showAll(int page, int size){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Exercises", Exercises.class);
        query.setMaxResults(size);
        query.setFirstResult((page -1)*size);
        List<Exercises> exercises = query.list();
        session.close();
        return exercises;
    }
    @Override
    public List<Exercises> showSortedDifficulty(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Exercises> exercises = session.createQuery("from Exercises e order by e.difficulty_level desc", Exercises.class).list();
        session.close();

        return exercises;
    }

    @Override
    public List<String> showAllImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<String> images = session.createQuery("select e.url_image from Exercises e",String.class).list();
        session.close();

        return images;
    }

    @Override
    public List<ExercisesDTO> showNamesImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<ExercisesDTO> exerciseImage = session.createQuery("select new dto.ExercisesDTO(e.name, e.url_image, e.difficulty_level) from Exercises e", ExercisesDTO.class).list();
        session.close();

        return exerciseImage;
    }

    @Override
    public List<ExercisesDTO> showNamesImagesDifficulty(Integer difficulty) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<ExercisesDTO> query = session.createQuery("select new dto.ExercisesDTO(e.name, e.url_image, e.difficulty_level) from Exercises e where e.difficulty_level = :difficulty", ExercisesDTO.class);
        List<ExercisesDTO> result = query.setParameter("difficulty",difficulty).list();

        session.close();

        return result;
    }

    @Override
    public Long totalExercises() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Long> query = session.createQuery("select count(e) from Exercises e", Long.class);
        Long counter =  query.getSingleResult();

        session.close();

        return counter;
    }



    @Override
    public List<Exercises> searchNameLike(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Exercises> query = session.createQuery("from Exercises e where e.name like :name", Exercises.class);
        List<Exercises> filter = query.setParameter("name","%"+name+"%").list();
        session.close();

        return filter;
    }

    @Override
    public List<Exercises> filterMuscleGroup(String muscleGroup) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query <Exercises> query = session.createQuery("from Exercises e where muscle_group=:muscleGroup", Exercises.class);
        List<Exercises> exercises = query.setParameter("muscleGroup",muscleGroup).list();
        session.close();
        return exercises;
    }

    @Override
    public List<Exercises> filterMuscleGroups(List<String> listMuscleGroups) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Exercises> query = session.createQuery("from Exercises e where muscle_group in (:muscleGroups)",Exercises.class);
        List<Exercises> filter =query.setParameter("muscleGroups",listMuscleGroups).list();
        session.close();

        return filter;
    }

}
