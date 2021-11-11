package menus;

import api.HotelResource;
import api.AdminResource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import model.*;

import static model.RoomType.DOUBLE;
import static model.RoomType.SINGLE;

public class AdminMenu {
    public static HashSet<Reservation> testMap = new HashSet<>();
    private static HotelResource hotel = HotelResource.getInstance();
    private static AdminResource admin = AdminResource.getInstance();

    public static void adminMenu() {

        boolean on = true;
        Scanner scanner = new Scanner(System.in);
        while (on) {
            try {
                System.out.println("Administrative Menu:");
                System.out.println("1. See all customers");
                System.out.println("2. See all rooms");
                System.out.println("3. See all reservations");
                System.out.println("4. Add a room");
                System.out.println("5. Main");
                System.out.println("6. Add dummy data");
                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        getAllCustomers();
                        break;
                    case 2:
                        listRooms();
                        break;
                    case 3:
                        displayAllReservations();
                        break;
                    case 4:
                        addRoom();
                        break;
                    case 5:
                        MainMenu.main();
                        break;
                    case 6:
                        addDummyData();
                        break;
                    default:
                        adminMenu();
                        System.out.println("Please select from the menu.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("You must select a number.");
                adminMenu();
            }
        }
    }

    private static void addDummyData() {

        ArrayList<IRoom> list = new <IRoom>ArrayList();

        Room room2 = new Room("2", 29.0, SINGLE);


        list.add(room2);

        admin.addRoom(list);




        hotel.createACustomer("jsmith@gmail.com", "john", "smith");
        hotel.createACustomer("jsmith@gmail.com", "john", "smith");
        hotel.createACustomer("jsmith@gmail.com", "john", "smith");
        hotel.createACustomer("jsmith@gmail.com", "john", "smith");

        hotel.createACustomer("jdouglas@gmail.com", "jim", "douglas");
        Date today = new Date();
        LocalDate lCheckInDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lCheckOutDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date checkInDate = Date.from(lCheckInDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date checkOutDate = Date.from(lCheckOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Reservation res1 = hotel.bookARoom("jsmith@gmail.com", room2, checkInDate, checkInDate);


        testMap.add(res1);


        System.out.println(testMap);

    }

    private static void addRoom() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<IRoom> newRooms = new ArrayList<>();
        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();
        double roomPrice = doubleParser();
        RoomType roomType = enumSelector();

        if (roomPrice == 0.0) {
            FreeRoom myRoom = new FreeRoom(roomNumber, 0.0, roomType);
            newRooms.add(myRoom);
        } else {
            Room myRoom = new Room(roomNumber, roomPrice, roomType);
            newRooms.add(myRoom);
        }
        admin.addRoom(newRooms);
        System.out.println("Room added successfully!");
        adminMenu();
    }

    private static void listRooms() {

        Collection<IRoom> rooms = admin.getAllRooms();
        if (rooms.size() == 0) {
            System.out.println("There are currently no rooms.");
        } else {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private static void getAllCustomers() {

        Collection<Customer> customers = admin.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("There are currently no customers.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private static void displayAllReservations() {
        admin.displayAllReservations();
    }

    public static double doubleParser() {
        System.out.println("What is the price for this room?");
        double input;
        Scanner scanner = new Scanner(System.in);
        try {
            input = Double.parseDouble(scanner.nextLine());
            return input;
        } catch (Exception ex) {
            //I say "price should be a number" below in the error message because I know that whether
            //or not the user types it with a decimal, it will automatically become a double due to
            // type casting.
            System.out.println("The price should be a number. Please try again.");
            return doubleParser();
        }
    }

    public static RoomType enumSelector() {
        RoomType myEnum = null;
        boolean on = true;
        Scanner scanner = new Scanner(System.in);
        while (on) {
            try {
                System.out.println("Please select a room size.");
                System.out.println("1. SINGLE");
                System.out.println("2. DOUBLE");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        myEnum = SINGLE;
                        on = false;
                        return myEnum;
                    }
                    case 2 -> {
                        myEnum = DOUBLE;
                        on = false;
                        return myEnum;
                    }
                    default -> {
                        enumSelector();
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("Please choose a number.");
            }
        }
        return myEnum;
    }
}