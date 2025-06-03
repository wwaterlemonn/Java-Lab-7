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
        System.out.println("Коллекция загружена из файла " + collectionFileName);
    }
    
    /**
     * Добавляет новый элемент в коллекцию с заданным ключом, при условии что элемента с таким ключом
     * еще не существует в таблице.
     *
     * @param key ключ, по которому будет сохранён дракон
     * @param dragon объект дракона для добавления
     */
    public static void addElement(String key, Dragon dragon){
        try{
            session.beginTransaction();
            dragon.setKey(key);
            session.persist(dragon);
            session.getTransaction().commit();
            collection.putIfAbsent(key, dragon);
        }
        catch(ConstraintViolationException e){
            System.out.println(e.getClass() + ": " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Обновляет элемент коллекции с заданным ключом, при условии что элемент с таким ключом
     * существует в таблице.
     *
     * @param key ключ, по которому будет сохранён дракон
     * @param dragon новый объект дракона для обновления
     */
    public static void updateElement(long id, Dragon dragon){
        try{
            session.beginTransaction();
            Dragon oldDragon = session.find(Dragon.class, id);
            dragon.setKey(oldDragon.getKey());
            dragon.setId(id);
            session.merge(dragon);
            session.getTransaction().commit();
            collection.replace(dragon.getKey(), dragon);
        }
        catch(NullPointerException e){
            System.out.println(e.getClass() + ": " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Удаляет элемент из коллекции по заданному ключу.
     *
     * @param key ключ элемента, который нужно удалить
     * @return удалённый объект {@link Dragon} или {@code null}, если ключ не найден
     */
    public static Dragon removeElement(Long id){
        try{
            session.beginTransaction();
            Dragon dragon = session.find(Dragon.class, id);
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
     * Полностью очищает коллекцию драконов.
     */
    public static void clearCollection(){
        collection.clear();
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