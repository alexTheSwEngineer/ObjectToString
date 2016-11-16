package chain;

/**
 * Created by atrposki on 23-Nov-16.
 */
public class ChainState {
    private boolean isPresent;
    private boolean isBetween;
    private boolean isLast;
    private boolean isFirst;

    public boolean isPresent() {
        return isPresent;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isBetween() {
        return isBetween;
    }

    public boolean isLast() {
        return isLast;
    }


    public ChainState isPresent(boolean bol) {
        this.isPresent = bol;
        return this;
    }

    public ChainState isBetween(boolean bol) {
        this.isBetween = bol;
        return this;
    }

    public ChainState isLast(boolean bol) {
        this.isLast = bol;
        return this;
    }

    public ChainState isFirst(boolean bol) {
        this.isFirst = bol;
        return this;
    }

    @Override
    public String toString(){
        return "isFirst:"+this.isFirst+" |isPresent:"+isPresent+" |isBetween:"+isBetween+" |isLast:"+isLast +" |";
    }
}