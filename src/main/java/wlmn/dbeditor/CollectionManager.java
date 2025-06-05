package wlmn.dbeditor;

import java.util.TreeMap;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import wlmn.character.Dragon;

/**
 * Класс для управления коллекцией объектов типа {@link Dragon}, хранящейся в {@link TreeMap}.
 * Предоставляет методы для загрузки, добавления, удаления и очистки элементов коллекции.
 */
public class CollectionManager {
    private static TreeMap<String, Dragon> collection;
    private static String collectionFileName;
    private static Session session = HibernateUtil.getSessionFactory().openSession();

    /**
     * Загружает коллекцию драконов из JSON-файла.
     * Если коллекция уже была загружена, она очищается перед загрузкой новых данных.
     *
     * @param filename путь к JSON-файлу с данными о коллекции
     * @see FileEditor#jsonToDragon(String)
     */
    public static void loadCollection(){
        if (collection != null) collection.clear();
        collection = session.createSelectionQuery("FROM Dragon", Dragon.class).getResultList().stream()
            .collect(Collectors.toMap(Dragon::getKey, item -> item, (e1, e2) -> e1, TreeMap::new));
        System.out.println("Коллекция загружена из базы данных.");
    }
    
    /**
     * Добавляет новый элемент в коллекцию с заданным ключом, при условии что элемента с таким ключом
     * еще не существует в таблице.
     *
     * @param key ключ, по которому будет сохранён дракон
     * @param dragon объект дракона для добавления
     */
    public static String insertElement(String login, String key, Dragon dragon){
        try{
            session.beginTransaction();
            dragon.setKey(key);
            dragon.setOwnerLogin(login);
            session.persist(dragon);
            session.getTransaction().commit();
            collection.putIfAbsent(key, dragon);
            return ("В коллекцию успешно добавлен элемент с ключом " + key + ".");
        }
        catch(ConstraintViolationException e){
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return ("Ошибка: элемент с ключом " + key + " уже существует.");
        }
    }

    /**
     * Обновляет элемент коллекции с заданным ключом, при условии что элемент с таким ключом
     * существует в таблице.
     *
     * @param key ключ, по которому будет сохранён дракон
     * @param dragon новый объект дракона для обновления
     */
    public static String updateElement(String login, long id, Dragon dragon){
        try{
            session.beginTransaction();
            Dragon oldDragon = session.find(Dragon.class, id);
            dragon.setKey(oldDragon.getKey());
            dragon.setId(id);
            dragon.setOwnerLogin(login);
            session.merge(dragon);
            session.getTransaction().commit();
            collection.replace(dragon.getKey(), dragon);
            return ("Значение элемента коллекции с id = " + id + " успешно обновлено.");
        }
        catch(NullPointerException e){
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return ("Ошибка: не удалось обновить элемент, так как элемента с id = " + id + " не существует.");
        }
    }

    /**
     * Удаляет элемент из коллекции по заданному ключу.
     *
     * @param key ключ элемента, который нужно удалить
     * @return удалённый объект {@link Dragon} или {@code null}, если ключ не найден
     */
    public static Dragon removeElement(String login, Long id){
        try{
            Dragon dragon = session.find(Dragon.class, id);
            if (!dragon.getOwnerLogin().equals(login)) return null;
            session.beginTransaction();
            session.remove(dragon);
            session.getTransaction().commit();
            return collection.remove(dragon.getKey());
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getClass() + ": " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return null;
        }
    }

    /**
     * Полностью очищает коллекцию драконов соответствующего пользователя.
     */
    public static void clearCollection(String login){
        try{
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM Dragon dragon WHERE dragon.ownerLogin = :login")
                .setParameter("login", login)
                .executeUpdate();
            session.getTransaction().commit();
            collection.entrySet().removeIf(item -> (item.getValue().getOwnerLogin().equals(login)));
        }
        catch(Exception e){
            System.out.println(e.getClass() + ": " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Возвращает текущую коллекцию драконов.
     *
     * @return {@link TreeMap} коллекция драконов
     */
    public static TreeMap<String, Dragon> getCollection() {
        return collection;
    }

    /**
     * Возвращает имя файла, с коллекцией из которого идет работа.
     *
     * @return {@link String} имя файла коллекции
     */
    public static String getCollectionFileName() {
        return collectionFileName;
    }

    /**
     * Закрывает сессию работы с базой данных. Следует вызывать при завершении работы приложения.
     */
    public static void closeSession(){
        session.close();
    }
}