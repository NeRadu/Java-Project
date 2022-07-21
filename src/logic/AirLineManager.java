package logic;

import constants.Mesaje;
import data.Bilet;
import data.Flight;
import data.User;
import database.FlightPersistence;
import database.UserPersistence;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class AirLineManager {

    private WriterManager writer = new WriterManager();
    private List<User> allUsers = new ArrayList<>();
    private Map<Integer, Flight> allFlights = new HashMap<>();
    private List<Bilet> bilete = new ArrayList<>();
    private User currentUser = null;
    private FlightPersistence flightPersistence = new FlightPersistence();
    private UserPersistence userPersistence = new UserPersistence();

    private boolean isNoUserLoggedin() {
        if (currentUser == null) {
            writer.write(("There is no connected user!"));
            return true;
        }
        return false;
    }

    public AirLineManager() throws IOException {
    }

    public void afisareUtilizatori() {
        for (User user : allUsers) {
            writer.write(user.toString());
        }
    }

    public void signUp(String[] comenzi) {

        //parseara informatiilor primite
        String email = comenzi[1];
        String nume = comenzi[2];
        String parola = comenzi[3];
        String parola_confirmata = comenzi[4];

        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                //TODO de scris in fisier
                writer.write(Mesaje.getUserAlreadyExist());
                return;
            }
        }
        //verificari parola
        if (parola.length() < 8) {
            //scriu in fisier mesaj respectiv
            writer.write("parola prea mica");
            return;
        }
        if (!parola.equals(parola_confirmata)) {
            writer.write("parolele nu sunt identice");
            return;
        }
        User user = new User(email, nume, parola);
        allUsers.add(user);
        writer.write(Mesaje.getUserAdded(user));
    }

    public void login(String[] comenzi) {
        //parseara informatiilor primite
        String email = comenzi[1];
        String parola = comenzi[2];

        //verificam daca deja e un user conectat
        if (currentUser != null) {
            writer.write("Another user is already connected");
            return;
        }
        //verificam data user exists
        Optional<User> userOptional = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (userOptional.isEmpty()) {
            writer.write(Mesaje.getCannotFindUser(email));
            return;
        }
        // verificam parola corecta
        User user = userOptional.get();
        if(!user.getPassword().equals(parola)){
            writer.write("Incorrect password !! ");
            return;
        }

        // utilizatorul va deveni user curent
        currentUser = user;
        writer.write("User with email "+ user.getEmail()+"is the current user started from "+ LocalDateTime.now());
    }

    public void logout(String[] comenzi) {

        //verificari
        String email = comenzi[1];
        if (currentUser == null) {
            writer.write("Cannot find user with email" + email );
            return;
        }
        if (currentUser.getEmail().equals(email)) {
            writer.write("User with email email successfully disconnected at :" + LocalDate.now());
            currentUser = null;
        } else {
            writer.write("The user with email: " + email + " was not connected!");
        }
    }


    public void addFlightDetails(String[] comenzi) {
        int id = Integer.parseInt(comenzi[1]);
        String from = comenzi[2];
        String to = comenzi[3];
        String dateAsString = comenzi[4];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateAsString, dateTimeFormatter);
        int duration = Integer.parseInt(comenzi[5]);
        // daca exista zbor:  “Cannot add flight! There is already a flight with id = <id>”
        //de incercat si cu stream: hint: anyMatch()
        if (allFlights.containsKey(id)) {
            writer.write("Cannot add flight! There is already a flight with id " + id);
            return;
        }

        // Flight from <from> to <to>, date <date>, duration <duration> successfully added!
        Flight flight = new Flight(id, from, to, date, duration);
        allFlights.put(id, flight);
        writer.write("Flight from " +from + " to " + to + " date "+date+" duration "+ duration + " succesesfully added !");

    }

    public void addUserFlight(String[] comenzi) {
        int flightId = Integer.parseInt(comenzi[1]);
        if (isNoUserLoggedin()) {
            return;
        }
        if (!allFlights.containsKey(flightId)) {
            writer.write("The flight with id " + flightId + " does not exist!");
            return;
        }

        // verificam daca userul are deja bilet
        if (bilete.stream().anyMatch(bilet -> bilet.getFlightID() == flightId && bilet.getUserID() == currentUser.getId())) {
            writer.write("User with email " + currentUser.getEmail() + " already has a ticket on flight " + flightId);
            return;
        }

        // Adaugare bilet
        bilete.add(new Bilet(flightId, currentUser.getId()));
        writer.write("The flight with id " + flightId + " was successfully added for user with email " + currentUser.getEmail());
    }

    // Display my flights
    public void displayMyFlights() {
        if (isNoUserLoggedin()) {
            return;
        }
        bilete.stream()
                .filter(bilet -> bilet.getUserID() == currentUser.getId())
                .forEach(bilet ->
                        writer.write("Flight from " + allFlights.get(bilet.getFlightID()).getFrom()
                                + " to " + allFlights.get(bilet.getFlightID()).getTo()
                                + " date " + allFlights.get(bilet.getFlightID()).getDate()
                                + " duration " + allFlights.get(bilet.getFlightID()).getDuration()));

    }

    // Cancel flight
    public void cancelFlight(String[] comenzi) {
        int flightID = Integer.parseInt(comenzi[1]);
        if (isNoUserLoggedin()) {
            return;
        }
        if (allFlights.get(flightID) == null) {
            writer.write("The flight with ID " + flightID + " doesn't exist!");
            return;
        }
        if (!bilete.stream().anyMatch(bilet -> bilet.getFlightID() == flightID && bilet.getUserID() == currentUser.getId())) {
            writer.write("The user with email " + currentUser.getEmail() + "does not have a ticket for the flight with id " + flightID);
            return;
        }

        for (int i = 0; i < bilete.size(); i++) {
            if (bilete.get(i).getFlightID() == flightID && currentUser.getId() == bilete.get(i).getUserID()) {
                bilete.remove(i);
                writer.write("The user with email " + currentUser + " has successfuly canceled his ticket for flight with id " + flightID);
                break;
            }
        }
    }
    //delete flight
    public void deleteFlight(String[] comenzi){
        int flightID = Integer.parseInt(comenzi[1]);
        if(!allFlights.containsKey(flightID)){
            writer.write("The flight withy id " +flightID+ " does not exist!");
            return;
        }

        bilete.stream().filter(bilet -> flightID == bilet.getFlightID())
                .forEach(bilet ->{
                    for (User user: allUsers){
                        if(user.getId() == bilet.getUserID()) {
                            writer.write("The user with email " + user.getEmail() + "was notified that the flight with id " + flightID + " was canceled!");
                            break;
                        }
                    }
        });

    }
    public void persistFlights(){
        flightPersistence.persistFlights(allFlights.values());
        writer.write("The flights was successfulluy saved in the database at " + LocalDateTime.now());
    }
    public void persistUsers(){
        userPersistence.persistUsers(allUsers);
        writer.write("The users was successfully saved in the database at " + LocalDateTime.now());
    }

}
