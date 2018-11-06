package t.flatearchsocie.crimeview;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

public class DatabaseHandler{

    private static DatabaseHandler databaseHandler;
    private Connection connection = null;
    private Statement preparedStatement = null;
    //private CallableStatement storedProcedure = null;

    private DatabaseHandler() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connURL = "jdbc:jtds:sqlserver://openbox.nmmu.ac.za/JN07;instance=WRR";
            connection = DriverManager.getConnection(connURL, "JN07User", "u7WVFDBj");
        } catch (ClassNotFoundException e) {
            Log.e("Class not found Error", e.getMessage());
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        }
    }
    public Connection getCon() {
        return connection;
    }


    public static DatabaseHandler getInstance() {

        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler();
        }
        return databaseHandler;
    }

    public Boolean signIn(String password,String username) throws SQLException {

        //preparedStatement = connection.prepareStatement("SELECT * FROM USERTABlE WHERE USERNAME = ? AND PASSWORD = ?");

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM USERTABLE WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "' AND isBanned = 'False' ");


        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public Statement getStatement() throws SQLException {
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return preparedStatement;
    }
    public ArrayList<Crime> getUserCrimes(int ID) throws SQLException {


        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM CRIME WHERE UserID =" +ID);
        ArrayList<Crime> crimes = new ArrayList<>();

        while (resultSet.next()) {
            int crimeID = resultSet.getInt("CrimeID");
            int categoryID = resultSet.getInt("CategoryID");
            int locationID = resultSet.getInt("LocationID");
            int userID = resultSet.getInt("UserID");
            int verified = resultSet.getInt("Verified");
            Time time = resultSet.getTime("TimeRecorded");
            float latitude = resultSet.getFloat("Latitude");
            float longitude = resultSet.getFloat("Longitude");
            Boolean bool;
            Date date = resultSet.getDate("DateRecorded");
            if (verified == 0) {
                bool = false;
            } else {
                bool = true;
            }
            Crime crime = new Crime(crimeID, categoryID, locationID, userID, bool, time, date);
            crimes.add(crime);

        }
        return crimes;
    }

    public ArrayList<Crime> getCrimeDetails() throws SQLException {


        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM CRIME , CATEGORY , LOCATION WHERE CRIME.LOCATIONID = LOCATION.LOCATIONID AND CRIME.CATEGORYID = CATEGORY.CATEGORYID");
        ArrayList<Crime> crimes = new ArrayList<>();

        while (resultSet.next()) {
            int crimeID = resultSet.getInt("CrimeID");
            int categoryID = resultSet.getInt("CategoryID");
            int locationID = resultSet.getInt("LocationID");
            int userID = resultSet.getInt("UserID");
            int verified = resultSet.getInt("Verified");
            Time time = resultSet.getTime("TimeRecorded");

            Boolean bool;
            Date date = resultSet.getDate("DateRecorded");
            if (verified == 0) {
                bool = false;
            } else {
                bool = true;
            }
            Crime crime = new Crime(crimeID, categoryID, locationID, userID, bool, time, date);
            crimes.add(crime);
            crime.setLocation(resultSet.getString("Suburb"));
            crime.setSeverity(resultSet.getInt("SeverityIndicator"));

        }
        return crimes;

    }
    public ArrayList<User> getUserDetails() throws SQLException {


        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM USERS");
        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            int userID = resultSet.getInt("UserID");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");
            String name = resultSet.getString("Name");
            String surname = resultSet.getString("Surname");
            User user = new User(userID, username, password, name, surname);
            users.add(user);

        }
        return users;
    }



    public Boolean editProfile(String password, String username) throws SQLException {


        // ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM USERTABLE WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'");

        // String sql = " UPDATE UserTable SET Username = '" + username + "', Password = '" + password + "', UserType=1  WHERE UserID = 2 ";

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


        int resultSet = preparedStatement.executeUpdate("UPDATE UserTable SET Password = '" + password + "'  WHERE Username = '" + username + "'");


        if (resultSet == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean usernameExists(String username) throws SQLException {

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM USERTABLE WHERE USERNAME = '" + username + "' ");
        if (resultSet.getFetchSize() == 0) {
            return true;
        } else {
            return false;
        }


    }

    public String getCategory(int categoryID) throws SQLException {


        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM CATEGORY WHERE CATEGORYID = " + categoryID + "");

        resultSet.next();
        return resultSet.getString("CateName");


    }

    public String getUser(int userID) throws SQLException {

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM USERTABLE WHERE USERID = " + userID + "");

        resultSet.next();
        return resultSet.getString("Username");


    }

    public int getUserID(String Username) throws SQLException {
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM USERTABLE WHERE Username = '" + Username + "' AND isBanned = 'False' ";
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        int userID = -1;
        while (resultSet.next()) {
            userID = resultSet.getInt("UserID");
        }
        return userID;
    }
//    public User getUserObject(String username) throws SQLException {
//        String sql = "Select * From UserTable Where Username = '" + username +"'" ;
//        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        ResultSet resultSet = preparedStatement.executeQuery(sql);
//        int UserID = -1;
//        String password="";
//        String Surname="";
//        int UserType=-1;
//
//        while(resultSet.next()){
//             UserID = resultSet.getInt("UserID");
//             password = resultSet.getString("Password");
//             Surname = resultSet.getString("Surname");
//             UserType = resultSet.getInt("UserType");
//        }
//        User curUser = new User(UserID,username,password,Surname,UserType);
//        return null;
//    }

    public Crime getCrime(int crimeID) throws SQLException {

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM CRIME");

        Crime crime = null;
        while (resultSet.next()) {

            int categoryID = resultSet.getInt("CategoryID");
            int locationID = resultSet.getInt("LocationID");
            int userID = resultSet.getInt("UserID");
            int verified = resultSet.getInt("Verified");
            Time time = resultSet.getTime("TimeRecorded");

            Boolean bool;
            Date date = resultSet.getDate("DateRecorded");
            if (verified == 0) {
                bool = false;
            } else {
                bool = true;
            }
            crime = new Crime(crimeID, categoryID, locationID, userID, bool, time,  date);


        }
        return crime;


    }

//    public void BanUser(String username, int count) throws SQLException {
//
//        String sql = " SET IDENTITY_INSERT DeletedUser ON\n" +
//                "INSERT INTO DeletedUser ( UserID, Name, Surname, Bancode)" +
//                " SELECT UserID,  Name, Surname, " + count + " FROM UserTable WHERE UserName = '" + username + "'\n" +
//                "SET IDENTITY_INSERT DeletedUser OFF  ";
//        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        int resultSet = preparedStatement.executeUpdate(sql);
//        if (resultSet == 0) {
//            System.out.println("Test console");
//        }
//    }

    public void BanUser(String username, String Reason) throws SQLException {
        String sql = " SET IDENTITY_INSERT DeletedUser ON INSERT INTO DeletedUser ( UserID, Name, Surname) SELECT UserID,  Name, Surname FROM UserTable WHERE UserName = '" + username + "' SET IDENTITY_INSERT DeletedUser OFF";
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int resultSet = preparedStatement.executeUpdate(sql);
        if (resultSet == 1) {
            System.out.println("Test console");
            addBanReason(username, Reason);
        }
    }


//    public void addBanReason(String reason, String username) throws SQLException {
//        String sql = "INSERT INTO BanReasons(Description) VALUES('" + reason + "')";
//        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        int resultSet = preparedStatement.executeUpdate(sql);
//        if (resultSet == 1) {
//           int p = co(reason);
//            BanUser(username,p);
//        }
//    }

    public void addBanReason(String username, String reason) throws SQLException {
        int curUserID = getUserID(username);
        String sql = "INSERT INTO DeletedUser(Reason) VALUES('" + reason + "') WHERE UserID = '" + curUserID + "' ";
        String correctSQL = "UPDATE DeletedUser SET Reason = '"+ reason +"' WHERE UserID = '"+curUserID +"'";
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int resultSet = preparedStatement.executeUpdate(correctSQL);
        if (resultSet == 1) {
            isBannedChange(username);
        }
    }

    public void isBannedChange(String userName) throws SQLException {
        String sql = "UPDATE UserTable SET isBanned = 'True' WHERE UserName = '"+ userName +"'";
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int resultSet = preparedStatement.executeUpdate(sql);
        if (resultSet == 1) {

            // Toast.makeText(,"Banned Successfully", Toast.LENGTH_LONG).show();
        }
    }

    public Boolean verifyCrime(int crimeID) throws SQLException {

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int resultSet = preparedStatement.executeUpdate("UPDATE CRIME SET VERIFIED = " + 1 + "  WHERE CRIMEID = " + crimeID + "");
        if (resultSet == 0 ){return true;}
        else return true;



    }

    public ArrayList<String> getAreas() throws SQLException {


        ArrayList<String> areas = new ArrayList();

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT SUBURB FROM LOCATION");

        while(resultSet.next()){


            String area = resultSet.getString("SUBURB");
            areas.add(area);

        }


        return areas;

    }

    public ArrayList<Location> getLocations() throws SQLException{


        ArrayList<Location> areas = new ArrayList();

        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM LOCATION");

        while(resultSet.next()){


            String area = resultSet.getString("SUBURB");
            float Lat = resultSet.getFloat("Latitude");
            float Long = resultSet.getFloat("Longitude");

            areas.add(new Location(area , Lat , Long));

        }

        return areas;
    }


    public Boolean deleteCrime(int CrimeID) throws SQLException {


        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);



            ResultSet resultSet = preparedStatement.executeQuery("DELETE FROM CRIME WHERE CRIMEID = " + CrimeID + "");
            return true;






    }

}
