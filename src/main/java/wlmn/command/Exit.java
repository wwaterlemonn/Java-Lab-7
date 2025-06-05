package wlmn.command;

/**
 * Команда, завершающая работу программы.
 */
public class Exit extends Command{
    public Exit(){

    }
    
    public String execute(){
        //return CommandInvoker.executeCommand(new Request("save", null));
        return ("Клиент завершил работу приложения.");
    }

    public static String getMessage(){
        return ("exit: Завершает работу приложения и сохраняет коллекцию в файл.");
    }
}
