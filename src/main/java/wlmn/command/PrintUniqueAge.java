package wlmn.command;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, выводящая в консоль все уникальные значения поля age среди элементов коллекции.
 */
public class PrintUniqueAge extends Command {
    public PrintUniqueAge(){

    }
    
    public String execute(){
        LinkedList<Integer> ages = CollectionManager.getCollection().entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .map(item -> item.getAge())
            .distinct()
            .collect(Collectors.toCollection(LinkedList::new));
        String message = "";
        for (int x: ages){
            message += "\n"+x;
        }
        return message.stripLeading();
    }

    public static String getMessage(){
        return ("print_unique_age: выводит построчно уникальные значения возраста среди всех элементов коллекции.");
    }
}
