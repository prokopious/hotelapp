package menus;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import java.util.Date;
import java.util.Collection;

import api.HotelResource;
import api.AdminResource;
import model.Customer;
import model.Reservation;
import model.IRoom;
import model.RoomType;
import service.ReservationService;

import java.lang.*;
import java.util.*;

public class MainMenu {

    private static HotelResource hotelResource = HotelResource.getInstance();
    private static AdminResource adminResource = AdminResource.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();

    public static void main() {
        boolean on = true;
        Scanner scanner = new Scanner(System.in);
        while (on) {
            try {
                System.out.println("Main Menu");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");

                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        findARoom();
                        break;
                    case 2:
                        getCustomersReservation();
                        break;
                    case 3:
                        createACustomer();
                        break;
                    case 4:
                        AdminMenu.adminMenu();
                        break;
                    case 5:
                        on = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please select from the menu.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("You must select a number.");
                main();
            }
        }
    }

    private static void findARoom() {

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        Date checkIn = null;
        try {
            System.out.println("Please choose a check-in date: (e.g., 05-29-1453)");
            String d1 = scanner.nextLine();
            LocalDate checkInDate = LocalDate.parse(d1, form);
            checkIn = Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            System.out.println("Invalid date.");
            findARoom();
        }

        Date checkOut = null;
        try {
            System.out.println("Please choose a check-out date: (e.g., 05-29-1453)");
            String d2 = scanner.nextLine();
            LocalDate checkOutDate = LocalDate.parse(d2, form);
            checkOut = Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            System.out.println("Invalid date.");
            findARoom();
        }

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
        System.out.println("Available rooms: " + availableRooms);

        if (availableRooms.isEmpty()) {

            //here I convert dates to epoch days, add 7 days, and convert back to java.util.Date to get recommended rooms

            LocalDate newInDate = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate newOutDate = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long newLongIn = newInDate.toEpochDay() + 7;
            long newLongOut = newOutDate.toEpochDay() + 7;

            LocalDate newLocalIn = LocalDate.ofEpochDay(newLongIn);
            LocalDate newLocalOut = LocalDate.ofEpochDay(newLongOut);

            Date newCheckIn = Date.from(newLocalIn.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newCheckOut = Date.from(newLocalOut.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Collection<IRoom> recommendedRooms = hotelResource.findARoom(newCheckIn, newCheckOut);

            if (recommendedRooms.isEmpty()) {
                System.out.println("Sorry, but there are currently no rooms available. Please check back with us for new rooms.");
            } else {
                System.out.println("Sorry, but no ro oms are available on those dates. However," +
                        " the following rooms are available from " + newLocalIn + " to " + newLocalOut + ": ");
                System.out.println(recommendedRooms);

                //below I use a switch statement for yes/no questions called yesNoSwitch() instead of if-else statements, which
                //can be difficult to follow. The String argument is the "no" response, which hopefully improves
                // developer experience.
                System.out.println("Would you like to book one of these options?");
                yesNoSwitch("Please check back with us soon");
                System.out.println("Do you have an account with us? yes | no ?");
                yesNoSwitch("Please create an account.");
                System.out.println("Please enter your email address");
                String answer3 = scanner.nextLine();
                boolean account = hotelResource.getCustomer(answer3) == null;
                if (!account) {
                    System.out.println("Please choose a room number and hit enter to reserve room.");
                    String answer4 = scanner.nextLine();
                    boolean boule = false;
                    for (IRoom room : recommendedRooms) {
                        if (room.getRoomNumber().equals(answer4)) {
                            boule = true;
                            break;
                        } else {
                            boule = false;
                        }
                    }
                    if (boule) {
                        IRoom broom = hotelResource.getRoom(answer4);
                        hotelResource.bookARoom(answer3, broom, newCheckIn, newCheckOut);
                        System.out.println("Success!");
                    } else {
                        System.out.println("The room you selected is currently unavailable.");
                    }

                } else {
                    System.out.println("The email address that you entered does not match any of our records. Please try again or create an account.");
                }
                main();
            }

        } else {
            System.out.println("Would you like to book one of these? yes | no ?");
            yesNoSwitch("Please check back with us soon");
            System.out.println("Do you have an account with us? yes | no ?");
            yesNoSwitch("Please create an account.");
            System.out.println("Please enter your email address");
            String response3 = scanner.nextLine();
            boolean account2 = hotelResource.getCustomer(response3) == null;
            if (!account2) {
                System.out.println("Please choose a room number and hit enter to reserve room.");
                String response4 = scanner.nextLine();
                boolean boule = false;
                for (IRoom room : availableRooms) {
                    if (room.getRoomNumber().equals(response4)) {
                        boule = true;
                        break;
                    } else {
                        boule = false;
                    }
                }
                if (boule) {
                    IRoom broom = hotelResource.getRoom(response4);
                    hotelResource.bookARoom(response3, broom, checkIn, checkOut);
                    System.out.println("Success!");
                } else {
                    System.out.println("The room you selected is currently unavailable.");
                }

            } else {
                System.out.println("The email address that you entered does not match any of our records. Please try again or create an account.");
            }
            main();
        }
    }

    public static void yesNoSwitch(String message) {
        boolean on = true;
        Scanner scanner = new Scanner(System.in);
        while (on) {
            try {
                System.out.println("1. Yes");
                System.out.println("2. No");
                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        on = false;
                        break;
                    case 2:
                        System.out.println(message);
                        on = false;
                        main();
                        break;
                    default:
                        yesNoSwitch("Please choose a number");
                        main();
                        break;
                }
            } catch (Exception e) {
                yesNoSwitch("Please choose a number");
            }
        }
    }

    private static void getCustomersReservation() {
        String email = null;
        Scanner scanner = new Scanner(System.in);
        try {
            email = eValidate(scanner);
        } catch (
                IllegalArgumentException ex) {
            System.out.println("Invalid email address.");
            getCustomersReservation();
        }
        Collection<Reservation> reserveList = null;
        try {
            reserveList = hotelResource.getCustomersReservation(email);
        } catch (Exception e) {
            System.out.println("There are no reservations associated with this email address.");
        } finally {
            if (reserveList == null) {
                Customer dummy = hotelResource.getCustomer(email);
                if (dummy == null) {
                    System.out.println("Please create an account.");
                    main();
                }
            } else {
                for (Reservation reservation : reserveList) {
                    System.out.println(reservation);
                }
            }
        }
    }

    private static void createACustomer() {

        String email = null;
        Scanner scanner = new Scanner(System.in);
        try {
            email = eValidate(scanner);
        } catch (
                IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createACustomer();
        }
        System.out.println("First name:");
        String firstName = scanner.nextLine();
        System.out.println("Last name:");
        String lastName = scanner.nextLine();
        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully!");
        main();
    }


    public static void main(String[] args) {
    }

    public static RoomType enumFilter(Scanner scanner) {

        try {
            RoomType roomChoice = RoomType.valueOf(scanner.nextLine());
            System.out.println("You have selected size: " + roomChoice);
            return roomChoice;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Try again.");
            enumFilter(scanner);
            return null;
        }
    }

    public static boolean emailFilter(String email) {
        String zebra = "^(.+)@(.+)$";
        Pattern zebraStripes = Pattern.compile(zebra);
        Matcher matcher = zebraStripes.matcher(email);
        return matcher.matches();
    }

    public static String eValidate(Scanner scanner) {
        System.out.println("Please enter your email address (e.g., johnsmith@gmail.com)");
        String eAddress = scanner.nextLine();
        boolean emailBoolean = emailFilter(eAddress);
        if (!emailBoolean) {
            throw new IllegalArgumentException("Invalid email address. Please try again.");
        } else {
            return eAddress;
        }
    }
}