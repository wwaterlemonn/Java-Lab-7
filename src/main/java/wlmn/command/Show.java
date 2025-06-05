package wlmn.command;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;
import wlmn.dbeditor.FileEditor;

/**
 * Команда, выводящая коллекцию в консоль.
 */
public class Show extends Command{
    public Show(){

    }
    
    public String execute() {
        TreeMap<String, Dragon> sortedCollection = CollectionManager.getCollection().entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, TreeMap::new));
        return (FileEditor.dragonToJson(sortedCollection));
    }

    public static String getMessage(){
        return ("show: выводит коллекцию в консоль.");
    }
}
