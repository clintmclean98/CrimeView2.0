package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

public class ViewCrimesByArea extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    private Connection connection = null;
    private Statement preparedStatement = null;
    ResultSet resultSet = null;
    Button filter;
    ArrayList<String> listOfAreas = new ArrayList<>();
    ArrayList<Crime> listViewOfCrimes = new ArrayList<>();
    ArrayList<String> CategoriesList = new ArrayList<>();
    String Suburb = "";
    int LocID;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_crimes_by_area);
        filter = (Button) findViewById(R.id.filterBut);

        databaseHandler = DatabaseHandler.getInstance();
        connection = databaseHandler.getCon();


        populateAreaList();
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listOfAreas);
        autoCompleteTextView.setAdapter(adapter);

        filter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Suburb = autoCompleteTextView.getText().toString();
                try {
                    listViewOfCrimes.clear();
                    CategoriesList.clear();
                    getLocationID(Suburb);
                    ProcessListViewOfCrimes();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void populateAreaList() {
        String sql = "Select * From Location";
        try {
            preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery(sql);
            //       Toast.makeText(this, "Got resultant set of Locations", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                String area = resultSet.getString("Suburb");
                listOfAreas.add(area);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not populate List with crimes", Toast.LENGTH_LONG).show();
        }
    }

    public void getLocationID(String Suburb) throws SQLException {
        String sql = "Select * From Location WHERE Suburb = '" + Suburb + "'";

        try {
            preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery(sql);
            Toast.makeText(this, "Called getLocationID", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                LocID = resultSet.getInt("LocationID");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not process resultSet", Toast.LENGTH_LONG).show();
        }
        getCrime(LocID);
    }

    public void getCrime(int LocID) {
        Crime crime;
        String sql = "Select * From Crime WHERE LocationID = '" + LocID + "'";
        try {
            preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery(sql);
            crime = null;
        } catch (SQLException e) {

        }
        try {
            while (resultSet.next()) {
                int crimeID = resultSet.getInt("CrimeID");
                int categoryID = resultSet.getInt("CategoryID");
                // int locationID = resultSet.getInt("LocationID");
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
                crime = new Crime(crimeID, categoryID, LocID, userID, bool, time, date);
                listViewOfCrimes.add(crime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ProcessListViewOfCrimes() throws SQLException {
        int count = 1;
        for (int i = 0; i < listViewOfCrimes.size(); i++) {
            Crime curCrime = listViewOfCrimes.get(i);
            String categoryName = getCategory(curCrime.getCategoryID());
            CategoriesList.add("Crime "+count+": " + categoryName);
            count++;
        }


        ListAdapter adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CategoriesList);
        listView = (ListView) findViewById(R.id.catLV);
        listView.setAdapter(adap);


//        listView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String userName = String.valueOf(parent.getItemAtPosition(position));
//                        Intent intent = new Intent(getApplication(), BanReasons.class);
//                        intent.putExtra("nameOfUser", userName);
//                        startActivity(intent);
//                        // Toast.makeText(BanUser.this, userName, Toast.LENGTH_LONG).show();
//                    }
//                }
//        );






    }

    public String getCategory(int categoryID) throws SQLException {
        preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = preparedStatement.executeQuery("SELECT * FROM CATEGORY WHERE CATEGORYID = " + categoryID + "");
        resultSet.next();
        return resultSet.getString("CateName");
    }

}
