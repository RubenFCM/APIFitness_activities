package dao;

import entidades.Diet;
import entidades.TrainingRecords;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class TrainingDAO implements TrainingDAOInterface{


    @Override
    public TrainingRecords searchById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        TrainingRecords tr = session.find(TrainingRecords.class, id);
        session.close();

        return tr;
    }

    @Override
    public List<TrainingRecords> showAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TrainingRecords> all = session.createQuery("select tr from TrainingRecords tr join fetch tr.user join fetch tr.exercise", TrainingRecords.class).list();
        session.close();
        return all;
    }

    @Override
    public TrainingRecords createTraining(TrainingRecords trainingRecord) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
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
    public TrainingRecords updateTrainingtByID(TrainingRecords trainingRecord) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
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

    @Override
    public boolean deleteTrainingByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            TrainingRecords tr = this.searchById(id);
            if (tr!=null){
                session.delete(tr);
            }else {
                return false;
            }
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.beginTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }


}
