package t.flatearchsocie.crimeview;

public class Location {

    String Suburb;
    float Latitude , Longitude;

    int Count , Severity;

    public Location(String suburb, float latitude, float longitude) {
        Suburb = suburb;
        Latitude = latitude;
        Longitude = longitude;
        Count = 0 ;
        Severity = 0;
    }

    public String getSuburb() {
        return Suburb;
    }

    public void setSuburb(String suburb) {
        Suburb = suburb;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public void increaseCount(){

        Count = Count +1 ;
    }

    public void increaseSeverity(int S){

        Severity += S;

    }

    public int AverageSeverity(){
        if (Count > 0) {
            return Severity/Count;
        }
        else return 0;

    }
}
