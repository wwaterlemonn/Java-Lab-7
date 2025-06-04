package wlmn.character;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.hibernate.annotations.EmbeddedColumnNaming;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import wlmn.location.Coordinates;
import wlmn.myenum.Color;

/**
 * Класс, представляющий дракона.
 * Содержит информацию о характеристиках дракона и его убийце (если есть).
 * Реализует интерфейс Comparable для сравнения драконов.
 */
@Entity
public class Dragon implements Comparable<Dragon>, Serializable{
    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * Уникальный идентификатор дракона.
     * Не может быть null, должен быть > 0, генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id = null;

    /**
     * Уникальный ключ элемента в таблице.
     */
    @Column(unique = true)
    private String key = null;

    /**
     * Имя дракона.
     * Не может быть null или пустой строкой.
     */
    private String name;

    /**
     * Координаты расположения дракона.
     * Не может быть null.
     */
    @Embedded
    @EmbeddedColumnNaming("%s")
    private Coordinates coordinates;

    /**
     * Дата создания записи о драконе.
     * Не может быть null, генерируется автоматически.
     */
    private ZonedDateTime creationDate;

    /**
     * Возраст дракона.
     * Должен быть > 0.
     */
    private int age;

    /**
     * Вес дракона.
     * Не может быть null, должен быть > 0.
     */
    private Double weight;

    /**
     * Умеет ли дракон говорить.
     */
    private boolean speaking;

    /**
     * Цвет дракона.
     * Может быть null.
     */
    @Enumerated(EnumType.STRING)
    private Color color;

    /**
     * Убийца дракона (если есть).
     * Может быть null.
     */
    @Embedded
    @EmbeddedColumnNaming("killer_%s")
    private Person killer;

    public Dragon(){

    }

    /**
     * Конструктор для десериализации из JSON.
     * 
     * @param id уникальный идентификатор
     * @param name имя дракона
     * @param coordinates координаты
     * @param creationDate дата создания
     * @param age возраст
     * @param weight вес
     * @param speaking умеет ли говорить
     * @param color цвет
     * @param killer убийца
     * @throws Exception если параметры не соответствуют ограничениям
     */
    @JsonCreator
    public Dragon(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("coordinates") Coordinates coordinates,
    @JsonProperty("creationDate") ZonedDateTime creationDate, @JsonProperty("age") int age,
    @JsonProperty("weight") Double weight, @JsonProperty("speaking") boolean speaking, @JsonProperty("color") Color color,
    @JsonProperty("killer") Person killer) throws Exception{  
        this.id = id;
        
        if (name == null | name == "" | coordinates == null | age <= 0 | weight == null | weight <= 0){
            throw new Exception();
        }

        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.color = color;
        this.killer = killer;
    }

    /**
     * Основной конструктор для создания нового дракона.
     * Автоматически генерирует дату создания.
     * 
     * @param name имя дракона
     * @param coordinates координаты
     * @param age возраст
     * @param weight вес
     * @param speaking умеет ли говорить
     * @param color цвет
     * @param killer убийца
     */
    public Dragon(String name, Coordinates coordinates,int age,
    Double weight, boolean speaking, Color color,Person killer) {  
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.color = color;
        this.killer = killer;
    }

    /**
     * Сравнивает текущего дракона с другим.
     * Сравнение производится по местоположению.
     * 
     * @param dragon2 дракон для сравнения
     * @return 1 если текущий дракон "больше", -1 если "меньше", 0 если равны
     */
    @Override
    public int compareTo(Dragon dragon2) {
        return this.coordinates.compareTo(dragon2.coordinates);
    }

    /**
     * Возвращает имя дракона.
     * @return имя дракона
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты дракона.
     * @return объект Coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает возраст дракона.
     * @return возраст (целое число > 0)
     */
    public int getAge() {
        return age;
    }

    /**
     * Возвращает вес дракона.
     * @return вес (число > 0)
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Проверяет, умеет ли дракон говорить.
     * @return true если дракон умеет говорить
     */
    public boolean isSpeaking() {
        return speaking;
    }

    /**
     * Возвращает цвет дракона.
     * @return цвет (может быть null)
     */
    public Color getColor() {
        return color;
    }

    /**
     * Возвращает убийцу дракона (если есть).
     * @return объект Person или null
     */
    public Person getKiller() {
        return killer;
    }

    /**
     * Возвращает уникальный идентификатор дракона.
     * @return ID дракона
     */
    public Long getId() {
        return id;
    }

    /**
     * Впервые устанавливает значение id.
     * @param id
     */
    public void setId(Long id){
        if (this.id == null && id != null){
            this.id = id;
        }
    }

    /**
     * Возвращает дату создания дракона.
     * @return дата создания
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает уникальный ключ дракона.
     * @return ключ дракона
     */
    public String getKey() {
        return key;
    }

    /**
     * Впервые устанавливает значение ключа.
     * @param key
     */
    public void setKey(String key) {
        if (this.key == null && key != null){
            this.key = key;
        }
    }

    @Override
    public String toString() {
        return "Dragon [id=" + id + ", name=" + name + ", coordinates=" + coordinates + ", creationDate=" + creationDate
                + ", age=" + age + ", weight=" + weight + ", speaking=" + speaking + ", color=" + color + ", killer="
                + killer + "]";
    }
    
}
