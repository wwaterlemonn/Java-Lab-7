package wlmn.dbeditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.AccessDeniedException;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import wlmn.character.Dragon;

/**
 * Класс для работы с файлами, включая чтение/запись строк и сериализацию/десериализацию коллекции объектов {@link Dragon}.
 */
public class FileEditor {
    /**
     * Читает содержимое файла и возвращает его в виде строки.
     *
     * @param filename имя файла для чтения
     * @return содержимое файла в виде строки или null, если произошла ошибка
     */
    public static String fileToString(String filename){
        try {
            FileInputStream stream = new FileInputStream(filename);
            InputStreamReader streamReader = new InputStreamReader(stream);

            int t;
            String result = "";
            while ((t = streamReader.read()) != -1) {
                result += (char)t;
            }
            stream.close();

            return result;

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл с указанным именем не найден.");
        } catch (AccessDeniedException e){
            System.out.println("Ошибка: Нет прав доступа к указанному файлу.");
        } catch (NullPointerException e){
            System.out.println("Ошибка: Необходимо указать имя файла.");
        } catch (IOException e) {
            System.out.println("Ошибка: непредвиденная ошибка при попытке открыть файл.");
        }
        return null;
    }

    /**
     * Записывает строку в указанный файл.
     *
     * @param str строка для записи
     * @param filename имя файла для записи
     */
    public static void stringToFile(String str, String filename){
        try (FileWriter writer = new FileWriter(filename)) 
        {
            writer.write(str);
        } 
          catch (IOException e) {
            System.out.println("Ошибка: не удалось записать данные в файл.");
        }
    }

    /**
     * Десериализует JSON из файла в коллекцию объектов Dragon, используя библиотеку Jackson.
     *
     * @param filename имя JSON-файла с данными о драконах
     * @return TreeMap, где ключ - строка, значение - объект Dragon
     */
    public static TreeMap<String, Dragon> jsonToDragon(String filename){
        TreeMap<String, Dragon> dragons = new TreeMap<String, Dragon>();
        TypeReference<TreeMap<String, Dragon>> typeRef = new TypeReference<TreeMap<String, Dragon>>() {};
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            dragons = mapper.readValue(fileToString(filename), typeRef);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString()+"Ошибка: произошла ошибка при импорте коллекции из файла. Проверьте корректность ввода данных в файле.");
            System.exit(-1);
        }

        return dragons;
    }

    /**
     * Сериализует коллекцию драконов в JSON-строку при помощи библиотеки Jackson.
     *
     * @param dragons коллекция драконов для сериализации
     * @return JSON-строка, представляющая коллекцию драконов
     */
    public static String dragonToJson(TreeMap<String, Dragon> dragons){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dragons);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка: произошла непредвиденная ошибка при сериализации коллекции.");
            return "";
        }
    }
}
