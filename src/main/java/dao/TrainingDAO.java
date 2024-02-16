package dao;

import entidades.TrainingRecords;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class TrainingDAO implements TrainingDAOInterface{


    @Override
    public List<TrainingRecords> showAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TrainingRecords> all = session.createQuery("from TrainingRecords", TrainingRecords.class).list();
        session.close();
        return all;
    }
}
