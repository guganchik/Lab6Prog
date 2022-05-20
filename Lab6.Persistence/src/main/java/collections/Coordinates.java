package collections;

import java.io.Serializable;

/**
 *  Координаты исходного класса
 */
public class Coordinates implements Serializable {
    
    private float x; //Поле не может быть null
    private float y;

    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    @Override
    public String toString(){
        String s = "";
        s += "{\"x\" : " + Float.toString(x) + ", ";
        s += "\"y\" : " + Float.toString(y) + "}";
        return s;
    }
}
