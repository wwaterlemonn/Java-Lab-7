package wlmn.command;

import java.util.Map;
import java.util.stream.Collectors;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, выводящая коллекцию в консоль.
 */
public class Show extends Command{
    public Show(){

    }
    
    public String execute() {
        return CollectionManager.getCollection().entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .map(item -> item.getValue().toString())
            .collect(Collectors.joining("\n\n"));
    }

    public static String getMessage(){
        return ("show: выводит коллекцию в консоль.");
    }
}
