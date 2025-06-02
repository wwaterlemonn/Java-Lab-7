package wlmn.character;

import java.io.Serializable;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import wlmn.location.Coordinates;
import wlmn.myenum.Color;

/**
 * Класс, представляющий дракона.
 * Содержит информацию о характеристиках дракона и его убийце (если есть).
 * Реализует интерфейс Comparable для сравнения драконов.
 */
public class Dragon implements Comparable<Dragon>, Serializable{

    /**
     * Уникальный идентификатор дракона.
     * Не может быть null, должен быть > 0, генерируется автоматически.
     */
    private Long id;

    /**
     * Имя дракона.
     * Не может быть null или пустой строкой.
     */
    private String name;

    /**
     * Координаты расположения дракона.
     * Не может быть null.
     */
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
    private Color color;

    /**
     * Убийца дракона (если есть).
     * Может быть null.
     */
    private Person killer;

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
     * Автоматически генерирует ID и дату создания.
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
        this.id = 1L;
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
     * Сравнение производится по ID, возрасту и весу.
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

    public void setId(long id){
        this.id = id;
    }

    /**
     * Возвращает дату создания дракона.
     * @return дата создания
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
}
