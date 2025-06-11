package wlmn.character;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Класс, представляющий координаты на плоскости.
 */
@Embeddable
public class Coordinates implements Serializable, Comparable<Coordinates>{
    /**
     * Координата X.
     * Значение должно быть > -240.
     */
    @NotNull(message = "Координата X координат на плоскости не может быть null.")
    @Min(value = -240, message = "Координата X координат на плоскости должна быть > -240.")
    private long x;

    /**
     * Координата Y.
     * Не может быть null, должно быть <= 929.
     */
    @NotNull(message = "Координата Y координат на плоскости не может быть null.")
    @Max(value = 929, message = "Координата Y координат на плоскости должна быть <= 929.")
    private Double y;

    public Coordinates(){

    }
    
    /**
     * Конструктор для создания объекта Coordinates. Используется для сериализации в JSON.

     * 
     * @param x координата X (должна быть > -240)
     * @param y координата Y (не может быть null и должна быть ≤ 929)
     * @throws Exception если параметры не соответствуют ограничениям
     */
    @JsonCreator
    public Coordinates(@JsonProperty("x") long x,@JsonProperty("y") Double y) throws Exception{
        if (x <= -240 | y > 929 | y == null){
            throw new Exception();
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату X.
     * @return значение координаты X (> -240)
     */
    public long getX() {
        return x;
    }

     /**
     * Возвращает координату Y.
     * @return значение координаты Y (не null, ≤ 929)
     */
    public Double getY() {
        return y;
    }

    @Override
    public int compareTo(Coordinates o) {
        if (Math.sqrt(this.x*this.x + this.y*this.y) > (Math.sqrt(o.x*o.x + o.y*o.y))){
            return 1;
        }
        else if (Math.sqrt(this.x*this.x + this.y*this.y) == (Math.sqrt(o.x*o.x + o.y*o.y))){
            return 0;
        }
        else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return ("X: " + x + ", Y: " + y);
    }
}
