package wlmn.character;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import wlmn.location.Location;
import wlmn.myenum.Color;
import wlmn.myenum.Country;

/**
 * Класс, представляющий человека (в рамках приложения - убийцу дракона).
 * Содержит информацию о характеристиках человека.
 * Реализует интерфейс Comparable для сравнения людей по имени.
 */
@Entity
public class Person implements Comparable<Person>, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

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
    private Color hairColor;

    /**
     * Национальность человека.
     * Не может быть null.
     */
    private Country nationality;

    /**
     * Местоположение человека.
     * Не может быть null.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
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
     * Сравнивает текущего человека с другим по имени.
     * 
     * @param person2 человек для сравнения
     * @return результат сравнения имен (как String.compareTo)
     */
    public int compareTo(Person person2){
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
}