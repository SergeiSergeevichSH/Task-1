package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    //    private final SessionFactory sessionFactory =
//            Util.getFactory();
    private static final String TABLE = "users";

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List users = null;
        try (Session session = Util.getFactory().openSession()) {
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
        try (Session session = Util.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE +
                            "(id INT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR (50) NOT NULL, " +
                            "lastname VARCHAR (50) NOT NULL, " +
                            "age INT(3) NOT NULL, " +
                            "PRIMARY KEY (id)) DEFAULT CHARSET = utf8"
            ).executeUpdate();
            transaction.commit();
            System.out.println("Таблица c именем <" + TABLE + "> создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS " + TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица c именем <" + TABLE + "> уничтожена))");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Что-то пошло не так, см ошибку -> e.printStackTrace();");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

                Transaction transaction = null;
        try (Session session = Util.getFactory().openSession()) {
//        try (Session session = Util.getFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
//                session.save(new User());
                session.saveOrUpdate(User.class.toString(), new User(name, lastName, age));
                session.getTransaction().commit();
                System.out.println("Пользователь - <" + name + "> сохранён в таблицу " + TABLE);
            }
            catch (HibernateException e) {
                System.out.println("Не удалось сохранить пользователя, " +
                        "проверьте соединение с БД или таблицей \n" + e);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
                throw e;
            }
        }
        catch (HibernateException e) {
            System.out.println("Что-то пошло не так, см ошибку -> StackTrace();");
            e.printStackTrace();
        }

//        User user1 = new User();
//        Transaction transaction = null;
//        try (Session session = Util.getFactory().openSession()) {
//            try {
//                transaction =
//                session.beginTransaction();
//                session.save(user1);
////                session.saveOrUpdate(User.class.toString(), new User(name, lastName, age));
//                transaction.commit();
//                System.out.println("Пользователь - <" + name + "> сохранён в таблицу " + TABLE);
//            }
//            catch (HibernateException e) {
//                System.out.println("Не удалось сохранить пользователя, " +
//                        "проверьте соединение с БД или таблицей \n" + e);
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
//        catch (HibernateException e) {
//            System.out.println("Что-то пошло не так, см ошибку -> StackTrace();");
//            e.printStackTrace();
//        }


        /*try (Session session = Util.getFactory().openSession()) {
            transaction = session.beginTransaction();
//            session.saveOrUpdate(user1);
            session.saveOrUpdate(User.class.toString(),new User(name, lastName, age));
            transaction.commit();
            System.out.println("Пользователь - <" + name + "> сохранён в таблицу " + TABLE);
        } catch (HibernateException e) {
            System.out.println("Не удалось сохранить пользователя, " +
                    "проверьте соединение с БД или таблицей \n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Что-то пошло не так, см ошибку -> StackTrace();");
            e.printStackTrace();
        }*/
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь под номером id=" + id + " - удален из таблицы");
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя под таким id=" + id);
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Что-то пошло не так, см ошибку -> StackTrace();");
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        try (Session session = Util.getFactory().openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User user : users) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Таблица <" + TABLE + "> очищена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Что-то пошло не так, см ошибку -> StackTrace();");
                e.printStackTrace();
            }
        }
    }

}
