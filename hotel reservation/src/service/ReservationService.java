package service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.*;

import model.*;

public class ReservationService {

    private static ReservationService instance = null;

    private static final HashMap<String, IRoom> rooms = new HashMap<>();
    private static final HashSet<Reservation> reservations = new HashSet<>();


    ReservationService() { //constructor has the default modifier
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> listRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        HashMap<String, IRoom> freeRooms = new HashMap<>(rooms);
        HashMap<String, IRoom> occupiedRooms = new HashMap<>();

        for (Reservation reservation : reservations) {
            LocalDate cIn = reservation.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate cOut = reservation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate thisIn = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate thisOut = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long epochIn = cIn.toEpochDay();
            long epochOut = cOut.toEpochDay();
            long thisEpochIn = thisIn.toEpochDay();
            long thisEpochOut = thisOut.toEpochDay();

            if ((epochIn <= thisEpochOut) && (thisEpochIn <= epochOut)) {
                occupiedRooms.put(reservation.getRoom().getRoomNumber(), reservation.getRoom());

                for (String roomNumber : occupiedRooms.keySet()) {
                    freeRooms.remove(roomNumber, getARoom(roomNumber));
                }
            }
        }
        return freeRooms.values();
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        HashSet<Reservation> resSet = new HashSet<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                resSet.add(reservation);
            }
        }
        return resSet;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("There are currently no reservations.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    public static void main(String[] args) {

        //just testing my methods here in the main method. you may disregard this.

        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Date checkInDate = null;
        try {
            System.out.println("Please choose a check-in date: (e.g., 05-29-1453)");
            String d1 = "11-11-1111";
            LocalDate c1 = LocalDate.parse(d1, form);
            checkInDate = Date.from(c1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            System.out.println("Invalid date.");
        }
        Date checkOutDate = null;
        try {
            System.out.println("Please choose a check-out date: (e.g., 05-29-1453)");
            String d2 = "13-11-1111";
            LocalDate c2 = LocalDate.parse(d2, form);
            checkOutDate = Date.from(c2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            System.out.println("Invalid date.");
        }

        FreeRoom room1 = new FreeRoom("1", 0.0, RoomType.SINGLE);
        Room room2 = new Room("2", 29.0, RoomType.SINGLE);
        Room room3 = new Room("3", 19.0, RoomType.SINGLE);
        Room room4 = new Room("4", 29.0, RoomType.SINGLE);
        Room room5 = new Room("5", 29.0, RoomType.SINGLE);
        rooms.put("1", room1);
        rooms.put("2", room2);
        rooms.put("3", room3);
        rooms.put("4", room4);
        rooms.put("5", room5);
        Customer john = new Customer("jsmith@gmail.com", "john", "smith");
        Customer bill = new Customer("bill@gmail.com", "bill", "williams");
        Date today = new Date();

        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(bill, room1, today, today);
        ReservationService.getInstance().reserveARoom(john, room1, today, today);
        ReservationService.getInstance().reserveARoom(john, room1, today, today);
        ReservationService.getInstance().reserveARoom(john, room1, today, today);
        ReservationService.getInstance().reserveARoom(john, room1, today, today);

        for (Reservation reservation : reservations) {
            System.out.println("here's a reservation" + reservation);
        }


    }
}