package www.gatherup.com.gatherup.data;

/**
 * Created by Matthew Luce on 5/10/2017.
 */

public enum Event_Type{
    DEFAULT(0),
    SPORT(1),
    GATHERING(2),
    MUSIC(3),
    LEARNING(4),
    GAMES(5),
    FOOD(6),
    ANY(7),
    CUSTOM(8);
    private int type_number;
    private Event_Type(int num){
        type_number = num;
    }
    public int getTypeNumber(){
        return type_number;
    }
}
