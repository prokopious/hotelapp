package model;
import java.util.*;
public class FreeRoom extends Room {

    public static HashSet<Date> dateSet = new HashSet<>();

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " | This is one of our free rooms";
    }

    public static void main(String[] args) {

        Date today = new Date();

        dateSet.add(today);
        dateSet.add(today);
        dateSet.add(today);
        dateSet.add(today);

        System.out.println(dateSet);


    }


}
