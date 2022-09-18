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
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            hibernateUserTableSession.createSQLQuery(CREATE_USER_TABLE).addEntity(User.class).executeUpdate();
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            hibernateUserTableSession.createSQLQuery(DROP_USER_TABLE).addEntity(User.class).executeUpdate();
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            hibernateUserTableSession.save(new User(name, lastName, age));
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            User user = hibernateUserTableSession.get(User.class, id);
            if (user == null) {
                return;
            }
            hibernateUserTableSession.delete(user);
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultUserTable = null;
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            resultUserTable = hibernateUserTableSession.createQuery("FROM User", User.class).getResultList();
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
        return resultUserTable;
    }

    @Override
    public void cleanUsersTable() {
        Session hibernateUserTableSession = HIBERNATE_USER_TABLE_CONFIGURATION.buildSessionFactory().getCurrentSession();
        try {
            hibernateUserTableSession.beginTransaction();
            hibernateUserTableSession.createSQLQuery(CLEAR_USER_TABLE).addEntity(User.class).executeUpdate();
            hibernateUserTableSession.getTransaction().commit();
        } catch (HibernateException e) {
            hibernateUserTableSession.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            hibernateUserTableSession.close();
        }
    }
}
