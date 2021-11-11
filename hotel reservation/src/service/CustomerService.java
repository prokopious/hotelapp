package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private static CustomerService instance = null;

    private static HashMap<String, Customer> customers = new HashMap();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }


    public static void main(String[] args) {
        //just running a few tests here...

        CustomerService.getInstance().addCustomer("jsmith@gmail.com", "john", "smith");
        System.out.println(CustomerService.getInstance().getCustomer("jsmith@gmail.com"));
        System.out.println(CustomerService.getInstance().getAllCustomers());
        System.out.println(CustomerService.getInstance().recordExists("jsmith@gmail.com"));
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(email, firstName, lastName);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    //just a practice method
    public boolean recordExists(String email) {
        return customers.containsKey(email);
    }
}