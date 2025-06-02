package wlmn.command;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, добавляющая указанный элемент в коллекцию по указанному ключу.
 */
public class Insert implements Command{
    private String key;
    private Dragon dragon;

    /**
     * @param key ключ элемента
     * @param dragon новый элемент
     */
    public Insert(String key, Dragon dragon){
        this.key = key;
        this.dragon = dragon;
    }

    public String execute() {
        CollectionManager.addElement(key, dragon);
        return ("В коллекцию успешно добавлен элемент с ключом " + key + ".");
    }

    public static String getMessage(){
        return ("insert key {dragon}: позволяет добавить в коллекцию новый элемент с ключом key. "
        + "Через пробел введите insert, задайте ключ для нового элемента. "
        + "Ввод элемента производится со следующей строки по одному полю построчно.");
    }
}
