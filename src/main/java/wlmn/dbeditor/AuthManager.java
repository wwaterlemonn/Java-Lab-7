package wlmn.dbeditor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Класс, предназначенный для регистрации и аутентификации пользователей.
 */
public class AuthManager{
    private static MessageDigest md;
    private static Session session = HibernateUtil.getSessionFactory().openSession();
    private static ReentrantLock lock = new ReentrantLock();

    public static String registerUser(String login, String password){
        lock.lock();
        try{
            session.beginTransaction();
            UserAccount user = new UserAccount(login, bytesToHex(md.digest(password.getBytes())));
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
        finally{
            lock.unlock();
        }
    }

    public static String authenticateUser(String login, String password){
        lock.lock();
        UserAccount user = session.createSelectionQuery("SELECT user FROM UserAccount user WHERE login = :login", UserAccount.class)
            .setParameter("login", login).getSingleResultOrNull();
        if (user != null && user.getPassword().equals(bytesToHex(md.digest(password.getBytes())))){
            lock.unlock();
            return ("[:^)] Успешно выполнен вход в аккаунт с логином " + login + ".");
        }
        else{
            lock.unlock();
            return ("[X] Ошибка: неверный логин или пароль.");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void initMD(String algorithm) throws NoSuchAlgorithmException{
        md = MessageDigest.getInstance(algorithm);
    }
}
