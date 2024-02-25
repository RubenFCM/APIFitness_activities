package dao;

import entidades.Country;
import entidades.Diet;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class CountryDAO implements CountryDAOInterface{
    @Override
    public Country searchById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Country c = session.find(Country.class, id);
        session.close();

        return c;
    }

    @Override
    public Country createCountry(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(country);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return country;
    }

    @Override
    public List<Country> showAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Country> all = session.createQuery("from Country", Country.class).list();
        session.close();

        return all;
    }

    @Override
    public List<Country> showAll(int page, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Country", Country.class);
        query.setMaxResults(size);
        query.setFirstResult((page-1)*size);
        List<Country> countries = query.list();
        session.close();
        return countries;
    }

    @Override
    public Country updateCountryByID(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(country);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return country;
    }

    @Override
    public boolean deleteCountryByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Country country = this.searchById(id);
            if (country!=null){
                session.delete(country);
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

    @Override
    public Long totalCountries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Long> query = session.createQuery("select count(c) from Country c", Long.class);
        Long counter = query.getSingleResult();
        session.close();
        return counter;
    }
}
