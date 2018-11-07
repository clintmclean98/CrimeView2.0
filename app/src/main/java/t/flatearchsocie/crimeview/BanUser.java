package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BanUser extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    private Connection connection = null;
    private Statement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<String> ListOfUsers = new ArrayList<>();
    String Suburb;

    String menutype = "menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_user);
        Intent intent = this.getIntent();
        menutype = intent.getStringExtra("menu");
        databaseHandler = DatabaseHandler.getInstance();
        connection = databaseHandler.getCon();
        fillListOfUsers();
        ArrayList<String> AL = ListOfUsers;
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        if (menutype.equals("admin")) {
            getMenuInflater().inflate(R.menu.adminmenu , menu);

        }
        else {
            getMenuInflater().inflate(R.menu.mainmenu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.editprofile) {
            Intent intent = new Intent(getApplicationContext(),EditProfile.class);

            startActivity(intent);
        }
        else if (item.getItemId() == R.id.viewcrime) {
            Intent intent = new Intent(getApplicationContext(), ViewCrimesByArea.class);
            intent.putExtra("menu" , menutype);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.viewmap) {
            Intent intent = new Intent(getApplicationContext(), SeverityIdicator.class);
            intent.putExtra("menu" , menutype);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.managecrime) {

            Intent intent = new Intent(getApplicationContext(), ManageCrime.class);
            intent.putExtra("menu" , menutype);
            startActivity(intent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;




    }
    public void fillListOfUsers() {
        // connection = databaseHandler.getConnection();
        if (connection == null) {
            Toast.makeText(this, "Could not get connection", Toast.LENGTH_LONG).show();
        }
        String sql = "Select * From UserTable WHERE isBanned = 'False'";
        try {
            preparedStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery(sql);
            Toast.makeText(this, "Got resultant set of Users", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                String userName = resultSet.getString("Username");
                ListOfUsers.add(userName);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not process resultSet", Toast.LENGTH_LONG).show();
        }
    }


    public void populateListView() {

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListOfUsers);
        ListView listView = (ListView) findViewById(R.id.ListViewOfUser);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String userName = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(getApplication(), BanReasons.class);
                        intent.putExtra("nameOfUser", userName);
                        startActivity(intent);
                        // Toast.makeText(BanUser.this, userName, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void createConnection() {
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
}
