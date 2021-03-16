package com.uniyaz.core.dao;

import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.utils.HibernateUtil;
import com.vaadin.ui.Panel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PanelDao {

    public void savePanel(MyPanel panel) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(panel);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MyPanel> listSurveys() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     panel " +
                            "From       MyPanel panel ";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
