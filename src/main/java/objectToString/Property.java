package objectToString;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Property{
    public String name;
    public String value;

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name+" "+value;
    }
}