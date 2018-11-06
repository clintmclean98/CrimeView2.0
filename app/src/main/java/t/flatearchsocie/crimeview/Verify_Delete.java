package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Verify_Delete extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    Crime crime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__delete);
        Intent intent = getIntent();
        crime = (Crime) intent.getSerializableExtra("crime");
        databaseHandler = DatabaseHandler.getInstance();
        Button btnDelete = findViewById(R.id.btnDeleteCrime);
        Button btnVerify = findViewById(R.id.btnVerifyCrime);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteCrime(v);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    verifyCrime(v);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            populateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void populateUI() throws SQLException {


        TextView txtCrimeType = findViewById(R.id.txtCrimeType);
        txtCrimeType.setText("Crime Type : " + databaseHandler.getCategory(crime.getCategoryID()));

        TextView txtUser = findViewById(R.id.txtUserReported);
        txtUser.setText("Reported By : " + databaseHandler.getUser(crime.getUserID()));

        TextView txtTime = findViewById(R.id.txtTimeReported);
        txtTime.setText("Time Reported : " + crime.getTimeRecorded().toString());

        TextView txtDate = findViewById(R.id.txtDateRecorded);
        txtDate.setText("Date Reported : " + crime.getDate().toString());
        TextView txtVerified = findViewById(R.id.txtVerified);

        if (crime.getVerified()) {
            txtVerified.setText("Verified : Yes");
        } else {
            txtVerified.setText("Verified : No");
        }


    }


    public void deleteCrime(View view) throws SQLException {

        if (databaseHandler.deleteCrime(crime.getCrimeID())) {
            Toast.makeText(getApplicationContext() , "Crime Deleted" , Toast.LENGTH_LONG);
            Intent intent = new Intent(getApplicationContext() , ManageCrime.class);
            startActivity(intent);

        }







    }

    public void verifyCrime(View view) throws SQLException {




        if (databaseHandler.verifyCrime(crime.getCrimeID())) {

            Toast.makeText(getApplicationContext(), "Crime Verified", Toast.LENGTH_LONG);
            TextView txtView = findViewById(R.id.txtVerified);
            txtView.setText("Verified : Yes");
            crime.setVerified(true);
        }


    }
}
