package model;

import java.util.*;

import static model.RoomType.SINGLE;


public class Reservation {
    //Customer is the default modifier
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;


    public Reservation(Customer customer, IRoom room,
                       Date checkInDate, Date checkOutDate) {


        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    public IRoom getRoom() {
        return room;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Reservation other))
            return false;
        boolean roomBoolean = (this.room == null && other.room == null)
                || (this.room != null && this.room.equals(other.room));
        boolean customerBoolean = (this.customer == null && other.customer == null)
                || (this.customer != null && this.customer.equals(other.customer));
        boolean dateInBoolean = (this.checkInDate == null && other.checkInDate == null)
                || (this.checkInDate != null && this.checkInDate.equals(other.checkInDate));
        boolean dateOutBoolean = (this.checkOutDate == null && other.checkOutDate == null)
                || (this.checkOutDate != null && this.checkOutDate.equals(other.checkOutDate));

        return roomBoolean && customerBoolean && dateInBoolean && dateOutBoolean;
    }

    @Override
    public final int hashCode() {
        int result = 17;

        if (room != null) {
            result = 31 * result + room.hashCode();
        }
        if (customer != null) {
            result = 31 * result + customer.hashCode();
        }
        if (checkInDate != null) {
            result = 31 * result + checkInDate.hashCode();
        }
        if (checkOutDate != null) {
            result = 31 * result + checkOutDate.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return customer + " | Room: " + room + " | Check-in: " + checkInDate + " | Check-out: " + checkOutDate;
    }


    public static void main(String[] args) {

        HashSet<Reservation> allReservedRooms = new HashSet<>();

        Customer john = new Customer("jimsmith@gmail.com", "jim", "smith");
        Customer jim = new Customer("jimsmith@gmail.com", "jim", "smith");

        Room room2 = new Room("2", 29.0, SINGLE);
        Room room3 = new Room("3", 39.0, SINGLE);

        Date date = new Date();

        Reservation res1 = new Reservation(john, room2, date, date);
        Reservation res2 = new Reservation(john, room2, date, date);
        Reservation res3 = new Reservation(john, room2, date, date);

        allReservedRooms.add(res1);
        allReservedRooms.add(res2);
        allReservedRooms.add(res3);

        System.out.println(allReservedRooms);



    }


}
