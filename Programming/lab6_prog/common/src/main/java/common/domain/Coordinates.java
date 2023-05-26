package common.domain;

import common.utility.Validateble;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс координат
 *
 * @author belovlaska
 * @version 1.0
 */
public class Coordinates implements Validateble, Serializable {
    private final double x;
    private final Long y; // Поле не может быть null

    public Coordinates(double x, Long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    public double getX(){
        return x;
    }

    public Long getY(){
        return y;
    }
    @Override
    public boolean validate() {
        return y != null;
    }
}
