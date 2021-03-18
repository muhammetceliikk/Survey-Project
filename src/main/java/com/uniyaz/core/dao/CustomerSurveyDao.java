package com.uniyaz.core.dao;

import com.uniyaz.core.domain.CustomerSurvey;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerSurveyDao {
    
    public void saveCustomerSurvey(CustomerSurvey customerSurvey) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(customerSurvey);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomerSurvey(CustomerSurvey customerSurvey) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(customerSurvey);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CustomerSurvey> listCustomerSurveys() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     customerSurvey " +
                            "From       CustomerSurvey customerSurvey ";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CustomerSurvey> listCustomerSurveysByMail(String mail) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     customerSurvey " +
                            "From       CustomerSurvey customerSurvey "+
                    "where customerSurvey.mail=:umail";
            Query query = session.createQuery(hql);
            query.setParameter("umail",mail);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*

    public List<CustomerSurvey> listCustomerSurveysById(Question question) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     customerSurvey " +
                            "From       CustomerSurvey customerSurvey " +
                            "where customerSurvey.question.id=:qid";
            Query query = session.createQuery(hql);
            query.setParameter("qid",question.getId());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/

}
