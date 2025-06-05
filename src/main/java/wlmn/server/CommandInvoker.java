package wlmn.server;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;

import wlmn.character.Dragon;
import wlmn.character.Person;
import wlmn.command.*;

/**
 * Класс-инвокер команд, управляющий их созданием, выполнением и историей.
 * Обеспечивает динамическое создание команд по их имени и аргументам,
 * а также ведение истории выполненных команд.
 */
public class CommandInvoker {
    private static HashMap<String, Command> commands = new HashMap<String, Command>();
    private static HashMap<String, Class<?>> commandClasses = initCommandClasses();
    private static HashMap<String, Class<?>[]> commandSignatures = initCommandSignatures();
    private static LinkedList<String> history = new LinkedList<String>();

    /**
     * Инициализирует соответствие между именами команд и их классами.
     * @return HashMap, где ключ - имя команды, значение - класс команды
     */
    private static HashMap<String, Class<?>> initCommandClasses(){
        HashMap<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("register_account", RegisterAccount.class);
        map.put("login_account", LoginAccount.class);
        map.put("help", Help.class);
        map.put("exit", Exit.class);
        map.put("show", Show.class);
        map.put("insert", Insert.class);
        map.put("remove_key", RemoveKey.class);
        map.put("clear", Clear.class);
        map.put("save", Save.class);
        map.put("history", History.class);
        map.put("execute_script", ExecuteScript.class);
        map.put("replace_if_greater", ReplaceIfGreater.class);
        map.put("info", Info.class);
        map.put("remove_greater_key", RemoveGreaterKey.class);
        map.put("filter_less_than_killer", FilterLessThanKiller.class);
        map.put("print_unique_age", PrintUniqueAge.class);
        map.put("print_field_descending_weight", PrintFieldDescendingWeight.class);
        map.put("update", Update.class);
        return map;
    }

    /**
     * Инициализирует сигнатуры (типы аргументов) для каждой команды.
     * @return HashMap, где ключ - имя команды, значение - массив типов аргументов
     */
    private static HashMap<String, Class<?>[]> initCommandSignatures(){
        HashMap<String, Class<?>[]> map = new HashMap<String, Class<?>[]>();
        map.put("register_account", new Class<?>[]{String.class, String.class});
        map.put("login_account", new Class<?>[]{String.class, String.class});
        map.put("help", new Class<?>[]{String.class});
        map.put("exit", new Class<?>[]{});
        map.put("show", new Class<?>[]{});
        map.put("insert", new Class<?>[]{String.class, Dragon.class});
        map.put("remove_key", new Class<?>[]{String.class});
        map.put("clear", new Class<?>[]{});
        map.put("save", new Class<?>[]{});
        map.put("history", new Class<?>[]{});
        map.put("execute_script", new Class<?>[]{String.class});
        map.put("replace_if_greater", new Class<?>[]{String.class, Dragon.class});
        map.put("info", new Class<?>[]{});
        map.put("remove_greater_key", new Class<?>[]{String.class});
        map.put("filter_less_than_killer", new Class<?>[]{Person.class});
        map.put("print_unique_age", new Class<?>[]{});
        map.put("print_field_descending_weight", new Class<?>[]{});
        map.put("update", new Class<?>[]{String.class, Dragon.class});
        return map;
    }

    /**
     * Обновляет команду в коллекции, создавая новый экземпляр с заданными аргументами.
     * @param key имя команды
     * @param args аргументы для создания команды
     * @throws IllegalArgumentException если переданные аргументы не соответствуют сигнатуре команды
     */
    public static void updateCommand(String key, Object[] args) throws IllegalArgumentException{
        Class<?> commandClass = commandClasses.get(key);
        try{
            Constructor<?> commandConstructor = commandClass.getConstructor(commandSignatures.get(key));
            Command newCommand = (Command) commandConstructor.newInstance(args);
            commands.put(key, newCommand);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Ошибка: команда " + key + " не принимает такой набор аргументов. "
            + "Используйте /help, чтобы узнать подробности "
            + "о синтаксисе ввода команд и удостоверьтесь, что вводили данные в соответствии с указанными ограничениями.");
        }
    }

    /**
     * Выполняет команду с указанными аргументами и обновляет историю команд.
     * @param commandName имя выполняемой команды
     * @param commandArgs аргументы команды
     */
    public static String executeCommand(Request request){
        try{
            updateCommand(request.getKey(), request.getArgs());
            updateHistory(request.getKey());
            return commands.get(request.getKey()).execute();
        }catch(IllegalArgumentException e){
            return(e.getMessage());
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return("Ошибка: команды с именем " + request.getKey() + " не существует. Используйте /help для полного списка команд.");
        }
    }

    /**
     * Обновляет историю выполненных команд, сохраняя последние 13 команд.
     * @param key имя выполненной команды для добавления в историю
     */
    private static void updateHistory(String key){
        if (history.size() >= 13){
            history.poll();
        }
        history.add(key);
    }

    /**
     * Возвращает коллекцию существующих команд.
     * @return HashMap команд, где ключ - имя команды, значение - экземпляр команды
     */
    public static HashMap<String, Command> getCommands(){
        return commands;
    }

    /**
     * Возвращает историю выполненных команд.
     * @return LinkedList с именами последних выполненных команд
     */
    public static LinkedList<String> getHistory(){
        return history;
    }

    /**
     * Возвращает коллекцию сигнатур всех команд.
     * @return HashMap, где ключ - имя команды, значение - массив типов аргументов
     */
    public static HashMap<String, Class<?>[]> getCommandSignatures(){
        return commandSignatures;
    }

    public static HashMap<String, Class<?>> getCommandClasses() {
        return commandClasses;
    }
}
