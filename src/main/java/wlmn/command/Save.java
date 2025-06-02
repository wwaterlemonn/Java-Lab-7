package wlmn.command;

import wlmn.dbeditor.CollectionManager;
import wlmn.dbeditor.FileEditor;

/**
 * Команда, сохраняющая изменненную коллекцию в файл.
 */
public class Save implements Command {
    private static String filename = CollectionManager.getCollectionFileName();

    public Save(){

    }

    public String execute() {
        FileEditor.stringToFile(FileEditor.dragonToJson(CollectionManager.getCollection()), filename);
        return ("Коллекция успешно сохранена в рабочий файл.");
    }

    public static String getMessage(){
        return ("save: сохраняет коллекцию в рабочий файл, указанный при запуске приложения.");
    }
}
