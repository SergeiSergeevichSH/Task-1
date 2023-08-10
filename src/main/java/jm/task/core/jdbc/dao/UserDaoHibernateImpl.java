package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    private final SessionFactory sessionFactory =
            Util.getFactory();
    private static final String TABLE = "users";

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List users = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS " + TABLE +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR (50) NOT NULL, lastname VARCHAR (50) NOT NULL, " +
                    "age INT(3) NOT NULL) DEFAULT CHARSET=utf8").executeUpdate();
            transaction.commit();
            System.out.println("Таблица " + TABLE + " создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS " + TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица " + TABLE + " уничтожена))");
        } catch (Exception e) {
            System.out.println("Соединение с БД не установлен");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("Пользователь - " + name + " сохранён в таблицу");
        } catch (Exception e) {
//            System.out.println("Не удалось сохранить пользователя, " +
//                    "ошибка доступа к таблице или БД" +e);
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("sddssd");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        }
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User user : users) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Таблица " + TABLE + " очищена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
//    }
//    @Override
//    public void cleanUsersTable() {
//
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            session.createSQLQuery("TRUNCATE TABLE " + TABLE).executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
////            e.printStackTrace();
//        }

}
