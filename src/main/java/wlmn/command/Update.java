package wlmn.command;

import java.util.Map;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, заменяющая элемент коллекции с указанным id на новый указанный элемент.
 */
public class Update implements Command{
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
        for (Map.Entry<String, Dragon> x: CollectionManager.getCollection().entrySet()){
            String key = x.getKey();
            Dragon value = x.getValue();
            if (id == value.getId()){
                CollectionManager.addElement(key, newDragon);
            }
        }
        return ("Значение элемента коллекции с id = " + id + " успешно обновлено.");
    }

    public static String getMessage(){
        return ("update id {dragon}: Заменяет значение элемента с указанным id на новое. "
        + "Через пробел введите update, id старого элемента (целое число). "
        + "Ввод элемента производится со следующей строки по одному полю построчно.");
    }
}
