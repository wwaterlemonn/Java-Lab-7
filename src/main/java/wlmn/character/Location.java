package wlmn.character;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;

/**
 * Класс, представляющий трехмерные координаты в пространстве.
 */
@Embeddable
public class Location implements Serializable{
    /**
     * Координата X.
     * Не может быть null.
     */
    private Long x;

    /**
     * Координата Y.
     * Не может быть null.
     */
    private Long y;

    /**
     * Координата Z.
     * Может быть любым числом с плавающей точкой.
     */
    private double z;

    public Location(){

    }

    /**
     * Создает новый объект Location с указанными координатами.
     *

     * @param x координата X (не может быть null)
     * @param y координата Y (не может быть null)
     * @param z координата Z
     * @throws Exception если параметры x или y равны null
     */
    @JsonCreator
    public Location(@JsonProperty("x") Long x, @JsonProperty("y") Long y, @JsonProperty("z") double z) throws Exception{
        if (x == null | y == null){
            throw new Exception();
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Возвращает координату X.
     * @return значение координаты X (не null)
     */
    public Long getX() {
        return x;
    }

    /**
     * Возвращает координату Y.
     * @return значение координаты Y (никогда не null)
     */
    public Long getY() {
        return y;
    }

     /**
     * Возвращает координату Z.
     * @return значение координаты Z
     */
    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return ("X: " + x + ", Y: " + y + ", Z: " + z);
    }
}
