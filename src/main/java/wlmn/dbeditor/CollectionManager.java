package wlmn.dbeditor;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;

import wlmn.character.Dragon;

/**
 * Класс для управления коллекцией объектов типа {@link Dragon}, хранящейся в {@link TreeMap}.
 * Предоставляет методы для загрузки, добавления, удаления и очистки элементов коллекции.
 */
public class CollectionManager {
    private static TreeMap<String, Dragon> collection;
    private static String collectionFileName;
    private static LinkedList<Long> id_taken = new LinkedList<Long>();

    /**
     * Загружает коллекцию драконов из JSON-файла.
     * Если коллекция уже была загружена, она очищается перед загрузкой новых данных.
     *
     * @param filename путь к JSON-файлу с данными о коллекции
     * @see FileEditor#jsonToDragon(String)
     */
    public static void loadCollection(String filename){
        if (collection != null) collection.clear();
        collectionFileName = filename;
        collection = FileEditor.jsonToDragon(collectionFileName);
        id_taken = collection.entrySet()
            .stream()
            .map(item -> item.getValue().getId())
            .distinct().collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Коллекция загружена из файла " + collectionFileName);
    }
    
    /**
     * Добавляет новый элемент в коллекцию. Также используется для замены уже существующего элемента по ключу.
     *
     * @param key ключ, по которому будет сохранён дракон
     * @param dragon объект дракона для добавления
     */
    public static void addElement(String key, Dragon dragon){
        if (id_taken.isEmpty()){
            dragon.setId(1L);
        }
        else{
            dragon.setId(id_taken.get(id_taken.size()-1)+1);
        }
        collection.put(key, dragon);
    }

    /**
     * Удаляет элемент из коллекции по заданному ключу.
     *
     * @param key ключ элемента, который нужно удалить
     * @return удалённый объект {@link Dragon} или {@code null}, если ключ не найден
     */
    public static Dragon removeElement(String key){
        return (collection.remove(key));
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
}