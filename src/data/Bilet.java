package data;

public class Bilet {
    private int flightID;
    private int userID;

    public Bilet(int flightID, int userID) {
        this.flightID = flightID;
        this.userID = userID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getUserID() {
        return userID;
    }
}

