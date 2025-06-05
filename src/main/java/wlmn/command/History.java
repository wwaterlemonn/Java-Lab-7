package wlmn.command;

import java.util.LinkedList;

import wlmn.server.CommandInvoker;

/**
 * Команда, выводящая последние 13 использованных команд в консоль.
 */
public class History extends Command{
    public History(){

    }
    
    public String execute(){
        LinkedList<String> history = CommandInvoker.getHistory();
        String message = ("Последние 13 использованных команд:");
        for (int i = 0; i < history.size(); i+=1){
            message += "\n" + ((i+1) + ") " + history.get(history.size()-(i+1)));
        }
        return message;
    }

    public static String getMessage(){
        return ("history: выводит имена последних 13 использованных команд без их аргументов,"
        + "начиная с последней использованной.");
    }
}
