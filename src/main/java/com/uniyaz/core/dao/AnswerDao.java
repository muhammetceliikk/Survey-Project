package com.uniyaz.core.dao;

import com.uniyaz.core.domain.Answer;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AnswerDao {
    
    public void saveAnswer(List<Answer> answerList) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (Answer answer : answerList) {
                session.merge(answer);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAnswer(Answer answer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(answer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Answer> listAnswers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     answer " +
                            "From       Answer answer ";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Answer> listAnswersById(Question question) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     answer " +
                            "From       Answer answer " +
                            "where answer.question.id=:qid";
            Query query = session.createQuery(hql);
            query.setParameter("qid",question.getId());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Answer> listAnswersByMail(String mail, Survey survey) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     answer " +
                            "From       Answer answer "+
                            "where answer.mail=:amail and answer.survey.id=:asid";
            Query query = session.createQuery(hql);
            query.setParameter("amail",mail);
            query.setParameter("asid",survey.getId());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
