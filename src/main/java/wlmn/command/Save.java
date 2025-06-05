package wlmn.command;

/**
 * Команда, сохраняющая изменненную коллекцию в файл.
 */
public class Save extends Command {
    //private static String filename = CollectionManager.getCollectionFileName();

    public Save(){

    }

    public String execute() {
        // FileEditor.stringToFile(FileEditor.dragonToJson(CollectionManager.getCollection()), filename);
        return ("");
    }

    public static String getMessage(){
        return ("save: сохраняет коллекцию в рабочий файл, указанный при запуске приложения.");
    }
}
