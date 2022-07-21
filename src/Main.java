import constants.Commands;
import logic.AirLineManager;
import logic.ReaderManager;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        AirLineManager manager = new AirLineManager();

        BufferedReader reader = ReaderManager.createReader();

        String linie = reader.readLine();

        while (linie != null){
            //proceseaza
            System.out.println("----------rand nou-------------------");
            String[] comenzi = linie.split(" ");
            Commands command ;
            try {
                command = Commands.valueOf(comenzi[0]);
            } catch (IllegalArgumentException e){
                command = Commands.DEFAULT_COMMAND;
            }

            switch (command) {
                case SIGNUP: {
                    manager.signUp(comenzi);
                    break;
                }
                case LOGIN : {
                    manager.login(comenzi);
                    break;
                }
                case ADD_FLIGHT: {
                    manager.addUserFlight(comenzi);
                    break;
                }
                case ADD_FLIGHT_DETAILS: {
                    manager.addFlightDetails(comenzi);
                    break;
                }
                case DISPLAY_MY_FLIGHTS:{
                    manager.displayMyFlights();
                    break;
                }
                case DELETE_FLIGHTS: {
                    manager.deleteFlight(comenzi);
                    break;
                }
                case LOGOUT: {
                    manager.logout(comenzi);
                    break;
                }
                case CANCEL_FLIGHT: {
                    manager.cancelFlight(comenzi);
                    break;
                }
                case PERSIST_FLIGHTS: {
                    manager.persistFlights();
                    break;
                }
                case PERSIST_USERS: {
                    manager.persistUsers();
                    break;
                }
                case DEFAULT_COMMAND:{
                    break;
                }
            }

            linie = reader.readLine();
        }
    }
}
