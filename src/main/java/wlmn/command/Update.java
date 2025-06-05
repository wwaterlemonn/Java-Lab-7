package wlmn.command;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, заменяющая элемент коллекции с указанным id на новый указанный элемент.
 */
public class Update extends ModificationCommand{
    private Long id;
    private Dragon newDragon;

    /**
     * @param id идентификатор элемента
     * @param dragon новый элемент
     * @throws Exception если id не целое число
     */
    public Update(String id, Dragon newDragon) throws Exception{
        this.id = Long.parseLong(id);
        this.newDragon = newDragon;
    }

    public String execute(){
        return (CollectionManager.updateElement(login, id, newDragon));
    }

    public static String getMessage(){
        return ("update id {dragon}: Заменяет значение элемента с указанным id на новое. "
        + "Через пробел введите update, id старого элемента (целое число). "
        + "Ввод элемента производится со следующей строки по одному полю построчно.");
    }
}
