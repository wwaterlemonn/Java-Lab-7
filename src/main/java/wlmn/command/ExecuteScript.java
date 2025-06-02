package wlmn.command;

/**
 * Команда, исполняющая скрипт (последовательность команд) из указанного файла.
 */
public class ExecuteScript{
    //private String filename;

    /**
     * @param filename имя файла, из которого нужно исполнить скрипт.
     */
    public ExecuteScript(String filename){
        //this.filename = filename;
    }

    public String execute(){
        return "";
    }

    public static String getMessage(){
        return ("execute_script filename: исполняет скрипт из указанного файла. "
        + "При некорректном формате ввода в файле исполнение скрипта может быть прервано.");
    }
}
