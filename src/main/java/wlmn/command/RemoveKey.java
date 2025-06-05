package wlmn.command;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, удаляющая элемент коллекции с заданным ключом.
 */
public class RemoveKey extends ModificationCommand{
    private String key;

    /**
     * @param key ключ
     */
    public RemoveKey(String key) {
        this.key = key;
    }

    public String execute() {
        if (CollectionManager.removeElement(login, CollectionManager.getCollection().get(key).getId()) != null){
            return ("Успешно удален элемент с ключом " + key + ".");
        }
        else{
            return ("Ошибка: элемента с таким ключом не существует.");
        }
    }

    public static String getMessage(){
        return ("remove_key key: удаляет из коллекции элемент с указанным через пробел ключом.");
    }
}
