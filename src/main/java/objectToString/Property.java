package objectToString;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Property{
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name+" : "+value;
    }
}