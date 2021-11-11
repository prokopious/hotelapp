package model;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;
    private boolean isValid;

    public Customer(String email, String firstName, String lastName) {

        this.isValid = this.eValidate(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        if (!isValid) {
            throw new IllegalArgumentException("Invalid email address. Please try again.");
        }
    }

    public boolean eValidate(String email) {
        if (email != null) {
        }
        String zebra = "^(.+)@(.+)$";
        Pattern zebraStripes = Pattern.compile(zebra);
        Matcher matcher = zebraStripes.matcher(email);
        return matcher.matches();
    }

    public String getFirst() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public static void main(String[] args) {

        //some tests
        HashMap<String, Customer> customerMap = new HashMap<>();

        Customer jim = new Customer("kimdouglas@gmail.com", "im", "douglas");
        Customer jim2 = new Customer("jimdouglas@gmail.com", "jim", "douglas");

        customerMap.put("jimdouglas@gmail.com", jim);
        customerMap.put("jimdouglas@gmail.com", jim2);
        System.out.println(customerMap);

    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Customer other))
            return false;
        boolean firstNameBoolean = (this.firstName == null && other.firstName == null)
                || (this.firstName != null && this.firstName.equals(other.firstName));
        boolean lastNameBoolean = (this.lastName == null && other.lastName == null)
                || (this.lastName != null && this.lastName.equals(other.lastName));
        boolean emailBoolean = (this.email == null && other.email == null)
                || (this.email != null && this.email.equals(other.email));


        return firstNameBoolean && lastNameBoolean && emailBoolean;
    }

    @Override
    public final int hashCode() {
        int result = 17;

        if (firstName != null) {
            result = 31 * result + firstName.hashCode();
        }
        if (lastName != null) {
            result = 31 * result + lastName.hashCode();
        }
        if (email != null) {
            result = 31 * result + email.hashCode();
        }
        return result;
    }
    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + " | Email: " + email;
    }
}

