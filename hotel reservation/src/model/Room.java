package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room implements IRoom {

    public String roomNumber;
    public Double price;
    public RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {

        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return enumeration;
    }

    public boolean isFree() {

        return false;
    }

    @Override
    public String toString() {

        return "Room: " + roomNumber + " | Price: " + price + "0 | Size: " + enumeration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Room other))
            return false;
        boolean roomNumberBoolean = (this.roomNumber == null && other.roomNumber == null)
                || (this.roomNumber != null && this.roomNumber.equals(other.roomNumber));
        boolean priceBoolean = (this.price == null && other.price == null)
                || (this.price != null && this.price.equals(other.price));
        boolean enumerationBoolean = (this.enumeration == null && other.enumeration == null)
                || (this.enumeration != null && this.enumeration.equals(other.enumeration));

        return roomNumberBoolean && priceBoolean && enumerationBoolean;
    }

    @Override
    public final int hashCode() {

        int result = 17;

        if (roomNumber != null) {
            result = 31 * result + roomNumber.hashCode();
        }
        if (price != null) {
            result = 31 * result + price.hashCode();
        }
        if (enumeration != null) {
            result = 31 * result + enumeration.hashCode();
        }
        return result;
    }

    public static void main(String[] args) {

        List<Room> rooms = new ArrayList<Room>();


        Room nine = new Room("5", 70.0, RoomType.DOUBLE);
        Room ten = new Room("7", 70.0, RoomType.DOUBLE);

        rooms.add(nine);
        rooms.add(ten);

        boolean search = rooms.contains(nine);

        System.out.println(search);

    }
}