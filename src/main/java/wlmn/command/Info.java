package wlmn.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeMap;

import wlmn.character.Dragon;
import wlmn.dbeditor.CollectionManager;

/**
 * Команда, выводящая общую информацию о коллекции: тип элементов, кол-во элементов, даты создания,
 * последнего открытия и последнего изменения.
 */
public class Info implements Command{
    private static String filename = CollectionManager.getCollectionFileName();

    public Info(){

    }

    public String execute(){
        TreeMap<String, Dragon> collection = CollectionManager.getCollection();
        String message = ("Тип коллекции: Dragon.");
        File file = new File(filename);
        Path filepath = file.toPath();
        BasicFileAttributes attr;
        try {
            attr = Files.readAttributes(filepath, BasicFileAttributes.class);
            message += ("\nДата создания: " + attr.creationTime());
            message += ("\nДата последнего получения доступа к коллекции: " + attr.lastAccessTime());
            message += ("\nДата последнего изменения коллекции: " + attr.lastModifiedTime());
        } catch (IOException e) {
            return ("Ошибка: непредвиденная ошибка при получении информации о файле коллекции.");
        }
        message += ("\nКоличество элементов в коллекции: "+collection.size()+".");
        return message;
    }

    public static String getMessage(){
        return ("info: выводит общую информацию о коллекции.");
    }
}
