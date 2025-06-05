package wlmn.command;

import wlmn.dbeditor.CollectionManager;

/**
 * Команда, очищающая коллекцию.
 */
public class Clear extends ModificationCommand{
    
    public Clear(){

    }
    
    public String execute() {
        CollectionManager.clearCollection(login);

        return ("Ваша коллекция полностью очищена.");
    }

    public static String getMessage(){
        return "clear: удаляет все элементы из коллекции.";
    }
}
