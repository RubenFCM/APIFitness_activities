package dao;

import entidades.Diet;
import org.hibernate.Session;
import util.HibernateUtil;

public class DietDAO implements DietDAOInterface{

    @Override
    public Diet searchById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Diet d = session.find(Diet.class, id);
        session.close();

        return d;
    }
}

