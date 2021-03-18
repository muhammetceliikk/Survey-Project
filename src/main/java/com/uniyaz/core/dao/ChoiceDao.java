package com.uniyaz.core.dao;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ChoiceDao {
    
    public void saveChoice(Choice choice) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(choice);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteChoice(Choice choice) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(choice);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Choice> listChoices() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     choice " +
                            "From       Choice choice ";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Choice> listChoicesById(Question question) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     choice " +
                            "From       Choice choice " +
                            "where choice.question.id=:qid";
            Query query = session.createQuery(hql);
            query.setParameter("qid",question.getId());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
