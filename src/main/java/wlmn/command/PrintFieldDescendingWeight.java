package wlmn.command;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, выводящая в консоль поле weight всех элементов коллекции в порядке убывания.
 */
public class PrintFieldDescendingWeight extends Command{
    public PrintFieldDescendingWeight(){

    }
    
    public String execute(){
        LinkedList<Double> weights = new LinkedList<Double>();
        for (Map.Entry<String, Dragon> x: CollectionManager.getCollection().entrySet()){
            Dragon value = x.getValue();
            weights.add(value.getWeight());
        }
        String message = "";
        weights = weights.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toCollection(LinkedList::new));
        for (double x: weights){
            message += "\n"+x;
        }
        return message.stripLeading();
    }

    public static String getMessage(){
        return ("print_field_descending_weight: "
        + "выводит построчно значения веса всех элементов в порядке убывания.");
    }
}
