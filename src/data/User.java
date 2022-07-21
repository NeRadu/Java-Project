package data;

import javax.swing.plaf.basic.BasicBorders;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {

    private int id;
    private String email;
    private String name;
    private String password;
    private List<Flight> userFlights = new ArrayList<>();

    public User(String email, String name, String password) {
        Random random = new Random();
        id = random.nextInt();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Flight> getUserFlights() {
        return userFlights;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {

        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void addFlight(Flight flight) {
        userFlights.add(flight);
    }
}
