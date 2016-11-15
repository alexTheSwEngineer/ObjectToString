package objectToString.propertySettings;

/**
 * Created by atrposki on 11/15/2016.
 */
public  class  Property<T>
{
    String name;
    String value;

    public Property(String name,String value){
        this.name= name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}