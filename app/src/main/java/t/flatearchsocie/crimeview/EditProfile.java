package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfile extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    private Connection connection = null;
    private Statement preparedStatement = null;
    ResultSet resultSet = null;
    String menutype = "menu";
//    Button add = (Button)findViewById(R.id.button4);
//
//    Button remove = findViewById(R.id.button2);
//    ArrayList<String> listOfAreas = new ArrayList<>();
//    ArrayList<Crime> listViewOfCrimes = new ArrayList<>();
//    ArrayList<String> CategoriesList = new ArrayList<>();
//    final AutoCompleteTextView comp = (AutoCompleteTextView) findViewById(R.id.bb);


    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("user");
        EditText txtView = findViewById(R.id.nameChange);
        txtView.setText(username);
        databaseHandler = DatabaseHandler.getInstance();


        if (username.equals("Admin")) {
            menutype = "admin";
        }







//        add.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String area = comp.getText().toString();
//                listOfAreas.add(area);
//            }
//        });
//
//
//        remove.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                String area = comp.getText().toString();
//                listOfAreas.remove(area);
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        if (username.equals("Admin")) {
            getMenuInflater().inflate(R.menu.adminmenu , menu);

        }
        else {
            getMenuInflater().inflate(R.menu.mainmenu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.managecrime) {
            Intent intent = new Intent(getApplicationContext(), ManageCrime.class);
            intent.putExtra("menu" , menutype);
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
        else if (item.getItemId() == R.id.banuser) {

                Intent intent = new Intent(getApplicationContext(), BanUser.class);
            intent.putExtra("menu" , menutype);
                startActivity(intent);
            }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;



        
    }

    public Integer getUserId() {
        return null;
    }

    public void editProfileSQL(View view) {
        EditText edtTextUsername = findViewById(R.id.nameChange);
        EditText edtTextPassword = findViewById(R.id.surnameChange);
        // Integer UserID = 2;
        // username = edtTextUsername.getText().toString();
        password = edtTextPassword.getText().toString();


//
        Integer UserType = 1;

        try {
            //Query works
            // String sql = " UPDATE UserTable SET Username = '" + username + "', Password = '" + password + "', UserType=1  WHERE UserID = 2 ";
            // String sql = " UPDATE UserTable SET Username = '" + username + "', Password = '" + password + "', Name ='" + Name + "', Surname='" + Surname + "', UserType=1  WHERE UserID = 2 ";
            //   String sql = " UPDATE UserTable SET Username = '" + Username + "', Password = '" + Password + "', Name ='" + Name + "', Surname='" + Surname + "', UserType=1  WHERE User
            if (databaseHandler.editProfile(password, username)) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(this, "Could not update", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

            Log.d("Fail", "editProfileSQL: Fail");
        }
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

            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not populate List with crimes", Toast.LENGTH_LONG).show();
        }
    }





}
