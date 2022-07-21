package database;

import data.Flight;
import data.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collection;

public class UserPersistence {
    public void persistUsers(Collection<User> users){
        try {
            // creare baza mysql
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/proiect_final";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "proiect", "password");


            String query = "REPLACE INTO user (id, email, name, password) VALUES(%d,'%s','%s','%s')";

            Statement st = conn.createStatement();
            for (User user : users) {
                // creare java statement
                st.execute(String.format(query, user.getId(), user.getEmail(), user.getName(), user.getPassword()));
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
