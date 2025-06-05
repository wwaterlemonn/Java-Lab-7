package wlmn.dbeditor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Класс, предназначенный для регистрации и аутентификации пользователей.
 */
public class AuthManager{
    private static MessageDigest md;
    private static Session session = HibernateUtil.getSessionFactory().openSession();

    public static String registerUser(String login, String password){
        try{
            session.beginTransaction();
            User user = new User(login, md.digest(password.getBytes()));
            session.persist(user);
            session.getTransaction().commit();
            return ("[:^)] Успешно зарегистрирован пользователь с логином " + login + ".");
        }
        catch(ConstraintViolationException e){
            System.out.println(e.getClass() + ": " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return ("[X] Ошибка: пользователь с логином " + login + " уже существует.");
        }
    }

    public static String authenticateUser(String login, String password){
        User user = session.createSelectionQuery("SELECT user FROM User user WHERE login = :login", User.class)
            .setParameter("login", login).getSingleResultOrNull();
        if (user != null && user.getPassword().equals(md.digest(password.getBytes()))){
            return ("[:^)] Успешно выполнен вход в аккаунт с логином " + login + ".");
        }
        else{
            return ("[X] Ошибка: неверный логин или пароль.");
        }
    }

    public static void initMD(String algorithm) throws NoSuchAlgorithmException{
        md = MessageDigest.getInstance(algorithm);
    }
}
