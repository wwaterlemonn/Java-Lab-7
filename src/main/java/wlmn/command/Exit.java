package wlmn.command;

import wlmn.server.CommandInvoker;
import wlmn.server.Request;

/**
 * Команда, завершающая работу программы.
 */
public class Exit implements Command{
    public Exit(){

    }
    
    public String execute(){
        return CommandInvoker.executeCommand(new Request("save", null));
    }

    public static String getMessage(){
        return ("exit: Завершает работу приложения и сохраняет коллекцию в файл.");
    }
}
