package wlmn.command;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, удаляющая элемент коллекции с заданным ключом.
 */
public class RemoveKey implements Command{
    private String key;

    /**
     * @param key ключ
     */
    public RemoveKey(String key) {
        this.key = key;
    }

    public String execute() {
        if (CollectionManager.removeElement(key) != null){
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
