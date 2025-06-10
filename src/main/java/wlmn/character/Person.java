package wlmn.character;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.EmbeddedColumnNaming;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Класс, представляющий человека (в рамках приложения - убийцу дракона).
 * Содержит информацию о характеристиках человека.
 * Реализует интерфейс Comparable для сравнения людей по имени.
 */
@Embeddable
public class Person implements Comparable<Person>, Serializable{
    /**
     * Имя человека.
     * Не может быть null или пустой строкой.
     */
    private String name;

    /**
     * Дата рождения человека.
     * Может быть null.
     */
    private java.time.LocalDateTime birthday;

    /**
     * Цвет волос человека.
     * Может быть null.
     */
    @Enumerated(EnumType.STRING)
    private Color hairColor;

    /**
     * Национальность человека.
     * Не может быть null.
     */
    @Enumerated(EnumType.STRING)
    private Country nationality;

    /**
     * Местоположение человека.
     * Не может быть null.
     */
    @Embedded
    @EmbeddedColumnNaming("%s")
    private Location location;

    public Person(){
        
    }

    /**
     * Конструктор для создания объекта Person. Также используется для сериализации в JSON.
     * 
     * @param name имя человека (не null и не пустое)
     * @param birthday дата рождения (может быть null)
     * @param hairColor цвет волос (может быть null)
     * @param nationality национальность (не null)
     * @param location местоположение (не null)
     * @throws Exception если обязательные параметры не соответствуют ограничениям
     */
    @JsonCreator
    public Person(@JsonProperty("name") String name, @JsonProperty("birthday") LocalDateTime birthday,
    @JsonProperty("hairColor") Color hairColor, @JsonProperty("nationality") Country nationality,
    @JsonProperty("location") Location location) throws Exception{
        // if (name == null | name == "" | nationality == null | location == null){
        //     throw new Exception();
        // }
        this.name = name;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    /**
     * Сравнивает текущего человека с другим по имени. При сравнении с {@code null} возвращает 1.
     * 
     * @param person2 человек для сравнения
     * @return результат сравнения имен (как String.compareTo)
     */
    public int compareTo(Person person2){
        if (person2 == null){
            return 1;
        }
        return this.name.compareTo(person2.name);
    }

    /**
     * Возвращает имя человека.
     * @return имя (не null)
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает дату рождения человека.
     * @return дата рождения (может быть null)
     */
    public java.time.LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * Возвращает цвет волос человека.
     * @return цвет волос (может быть null)
     */
    @Enumerated(EnumType.STRING)
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает национальность человека.
     * @return национальность (не null)
     */
    @Enumerated(EnumType.STRING)
    public Country getNationality() {
        return nationality;
    }

    /**
     * Возвращает местоположение человека.
     * @return объект Location (не null)
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return ("    Name: " + name + "\n    Birthday: " + birthday + "\n    Hair Color: " + hairColor.name() + "\n    Nationality: "
            + nationality.name() + "\n    Location: " + location.toString());
    }
}