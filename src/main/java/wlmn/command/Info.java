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
public class Info extends Command{
    private static String filename = CollectionManager.getCollectionFileName();

    public Info(){

    }

    public String execute(){
        return "Имя базы данных: studs\nТип базы данных: PostgreSQL\nИмя таблицы: DRAGON";
    }

    public static String getMessage(){
        return ("info: выводит общую информацию о коллекции.");
    }
}
