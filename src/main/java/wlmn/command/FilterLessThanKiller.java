package wlmn.command;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import wlmn.character.Dragon;
import wlmn.character.Person;
import wlmn.dbeditor.CollectionManager;
import wlmn.dbeditor.FileEditor;

/**
 * Команда, которая выводит все элементы коллекции со значением поля killer меньше указанного.
 */
public class FilterLessThanKiller implements Command{
    private Person killer;

    /**
     * @param killer убийца дракона
     */
    public FilterLessThanKiller(Person killer){
        this.killer = killer;
    }

    public String execute(){
        TreeMap<String, Dragon> strippedCollection = CollectionManager.getCollection();
        strippedCollection = strippedCollection.entrySet()
            .stream()
            .filter(entry -> (entry.getValue().getKiller().compareTo(killer) == 1))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, TreeMap::new));
        return (FileEditor.dragonToJson(strippedCollection));
    }

    public static String getMessage(){
        return ("filter_less_than_killer {person}: "
        + "выводит элементы коллекции, поле killer которых меньше заданного. Введите filter_less_than_killer. "
        + "Ввод элемента производится со следующей строки по одному полю построчно.");
    }
}
