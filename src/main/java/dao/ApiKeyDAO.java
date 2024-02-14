package dao;

import entidades.ApiKeys;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateUtil;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class ApiKeyDAO implements  ApiKeyDAOInterface{

    @Override
    public ApiKeys createApiKey(ApiKeys apiKey){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String keyHashed = BCrypt.hashpw(apiKey.getApikey(), BCrypt.gensalt());
        System.out.println(keyHashed);
        keyHashed = keyHashed.substring(0,Math.min(keyHashed.length(),40));
        System.out.println(keyHashed);
        apiKey.setApikey(keyHashed);
        try {
            session.beginTransaction();
            session.save(apiKey);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return apiKey;
    }

    @Override
    public ApiKeys getApiKey(String apikey) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<ApiKeys> query = session.createQuery("from ApiKeys a where a.apikey =:apikey", ApiKeys.class);
            ApiKeys apiKeys = query.setParameter("apikey",apikey).getSingleResult();
            if(apiKeys != null && apiKeys.isActive() && apiKeys.getNumberUses() > 0){
                addNumberUses(session,apiKeys);
                if (apiKeys.getNumberUses() == 0){
                    deactivateApikey(session,apiKeys);
                }
                return apiKeys;
            }else{
                return null;
            }
        }catch (NoResultException e){
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public List<ApiKeys> showAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<ApiKeys> all = session.createQuery("from ApiKeys", ApiKeys.class).list();
        session.close();

        return all;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            ApiKeys apiKeys = this.searchByID(id);
            if (apiKeys!=null){
                session.delete(apiKeys);
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
    public ApiKeys modifyByID(ApiKeys apiKeys) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(apiKeys);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return apiKeys;
    }


    private ApiKeys searchByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ApiKeys apiKeys = session.find(ApiKeys.class, id);
        session.close();

        return apiKeys;
    }

    private void addNumberUses(Session session, ApiKeys apiKeys){
        session.beginTransaction();
        apiKeys.setNumberUses(apiKeys.getNumberUses() - 1);
        session.update(apiKeys);
        session.getTransaction().commit();
    }
    private void deactivateApikey(Session session, ApiKeys apiKeys){
        session.beginTransaction();
        apiKeys.setActive(false);
        session.update(apiKeys);
        session.getTransaction().commit();
    }
}
