package database;

import data.Flight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collection;

public class FlightPersistence {

    public void persistFlights(Collection<Flight> flights){
        try {
            // creare baza mysql
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/proiect_final";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "proiect", "password");


            String query = "REPLACE INTO flight (id, flight_from, flight_to, date, duration) VALUES(%d,'%s','%s','%s',%s)";

            Statement st = conn.createStatement();
            for (Flight flight : flights) {
                // creare java statement
                st.execute(String.format(query, flight.getId(), flight.getFrom(), flight.getTo(), flight.getDate(), flight.getDuration()));
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
}

//            }
