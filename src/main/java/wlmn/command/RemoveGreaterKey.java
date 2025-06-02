package wlmn.command;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, удаляющая все элементы коллекции с ключом больше заданного.
 */
public class RemoveGreaterKey implements Command{
    private String key;

    /**
     * @param key ключ
     */
    public RemoveGreaterKey(String key){
        this.key = key;
    }

    public String execute(){
        long counter = CollectionManager.getCollection().keySet()
            .stream()
            .filter(item -> (item.compareTo(key) == 1))
            .count();
        CollectionManager.getCollection().keySet()
            .stream()
            .filter(item -> (item.compareTo(key) == 1))
            .forEach(item -> CollectionManager.removeElement(item));
        return ("Успешно удалены "+counter+" элементов.");
    }

    public static String getMessage(){
        return ("remove_greater_key key: удаляет из коллекции все элементы, ключ которых превышает заданный."
        +"Ключ задается через пробел после имени команды.");
    }
}
