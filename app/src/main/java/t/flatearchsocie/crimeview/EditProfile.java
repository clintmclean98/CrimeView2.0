package t.flatearchsocie.crimeview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfile extends Activity {
    DatabaseHandler databaseHandler;
    private Connection connection = null;
    private Statement preparedStatement = null;
    ResultSet resultSet = null;
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
