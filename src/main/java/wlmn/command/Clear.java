package wlmn.command;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, очищающая коллекцию.
 */
public class Clear implements Command{
    public Clear(){

    }
    
    public String execute() {
        CollectionManager.clearCollection();

        return ("Коллекция полностью очищена.");
    }

    public static String getMessage(){
        return "clear: удаляет все элементы из коллекции.";
    }
}
