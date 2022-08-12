package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final Configuration HIBERNATE_USER_TABLE_CONFIGURATION = Util.hibernateConfiguration();
    private final String CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS user(id INT  NOT NULL AUTO_INCREMENT,
                                               name VARCHAR(45) NULL,
                                               lastname VARCHAR(45) NULL,
                                               age INT NULL,
                                               PRIMARY KEY (id))
            """;
    private final String DROP_USER_TABLE = """
            DROP TABLE IF EXISTS user
            """;

    private final String CLEAR_USER_TABLE = """
            DELETE FROM user 
            """;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            hibertnateUserTableSession.createSQLQuery(CREATE_USER_TABLE).addEntity(User.class).executeUpdate();
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            hibertnateUserTableSession.createSQLQuery(DROP_USER_TABLE).addEntity(User.class).executeUpdate();
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            hibertnateUserTableSession.save(new User(name, lastName, age));
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            User user = hibertnateUserTableSession.get(User.class, id);
            if (user == null) {
                return;
            }
            hibertnateUserTableSession.delete(user);
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultUserTable = null;
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            resultUserTable = hibertnateUserTableSession.createQuery("FROM User").getResultList();
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
        return resultUserTable;
    }

    @Override
    public void cleanUsersTable() {
        Session hibertnateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibertnateUserTableSession.beginTransaction();
            hibertnateUserTableSession.createSQLQuery(CLEAR_USER_TABLE).addEntity(User.class).executeUpdate();
            hibertnateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibertnateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibertnateUserTableSession.close();
        }
    }
}
