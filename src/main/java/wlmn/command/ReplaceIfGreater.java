package wlmn.command;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, заменяющая элемент коллекции с указанным ключом на указанный новый элемент, если его значение превышает старое.
 */
public class ReplaceIfGreater extends ModificationCommand{
    private String key;
    private Dragon dragonNew;

    /**
     * @param key ключ
     * @param dragonNew новый элемент
     */
    public ReplaceIfGreater(String key, Dragon dragonNew){
        this.key = key;
        this.dragonNew = dragonNew;
    }

    public String execute(){
        Dragon dragonOld = CollectionManager.getCollection().get(key);
        if (dragonOld == null){
            return ("Ошибка: элемента с таким ключом не существует.");
        }
        else if (dragonNew.compareTo(CollectionManager.getCollection().get(key)) > 0){
            CollectionManager.updateElement(login, dragonOld.getId(), dragonNew);
            return ("Значение в коллекции с ключом "+ key + " успешно заменено на новое.");
        }
        else{
            return ("Замена не произведена: значение нового элемента не превышает старое.");
        }
    }

    public static String getMessage(){
        return ("replace_if_greater key {dragon}: заменяет элемент с ключом key на новый, если он больше старого. "
        + "Через пробел введите insert, задайте ключ для нового элемента. "
        + "Ввод элемента производится со следующей строки по одному полю построчно.");
    }
}
