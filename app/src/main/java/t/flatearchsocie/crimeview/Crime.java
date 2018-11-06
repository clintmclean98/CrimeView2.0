package t.flatearchsocie.crimeview;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Crime implements Serializable {

    private int crimeID, categoryID, locationID, userID;
    private Boolean verified;
    private Time timeRecorded;

    private Date date;
    private String location;
    private int severity;



    public Crime(int crimeID, int categoryID, int locationID, int userID, Boolean verified, Time timeRecorded, Date date) {
        this.crimeID = crimeID;
        this.categoryID = categoryID;
        this.locationID = locationID;
        this.userID = userID;
        this.verified = verified;
        this.timeRecorded = timeRecorded;
        this.date = date;
    }



    public int getCrimeID() {
        return crimeID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getUserID() {
        return userID;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Time getTimeRecorded() {
        return timeRecorded;
    }



    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Date getDate() {return date;}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }
}
