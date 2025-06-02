package wlmn.command;

import java.util.HashMap;
import java.util.Map;

import wlmn.server.CommandInvoker;

import java.lang.reflect.*;

/**
 * Команда, выводящая в консоль подробную информацию о различных доступных командах:
 *  их предназначение и правила ввода.
 */
public class Help implements Command{
    private static HashMap<String, String> messages = initMessages();
    private String key;

    /**
     * @param key имя команды, описание которой нужно вывести
     */
    public Help(String key){
        this.key = key;
    }

    private static HashMap<String, String> initMessages(){
        HashMap<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, Class<?>> x: CommandInvoker.getCommandClasses().entrySet()){
            String key = x.getKey();
            Class<?> value = x.getValue();
            try {
                Method method = value.getMethod("getMessage");
                String message = (String) method.invoke(null);
                map.put(key, message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка: указанная команда не имеет описания.");
            }
        }
        return map;
    }

    public String execute(){
        if (key.equals("all")){
            String message = "";
            for (String x: messages.values()){
                message += ("\n\n" + x);
            }
            return message.stripLeading();
        }
        else{
            String message = messages.get(key);
            if (message != null){
                return(message);
            }
            else{
                return("Ошибка: невозможно вывести описание команды, так как команды с таким именем не существует.");
            }
        }
    }

    public static String getMessage(){
        return ("help: Выводит описание команды, указанной через пробел. "
        +"Опционально через пробел можно указать \"all\", чтобы вывести описание всех команд.");
    }
}
