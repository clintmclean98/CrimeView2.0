package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

public class ManageCrime extends AppCompatActivity {
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_crime);
        databaseHandler = DatabaseHandler.getInstance();
        try {
            populateCrimes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateCrimes() throws SQLException {


        final List<Crime> crimes = databaseHandler.getCrimeDetails();

        CrimeAdapter crimeAdapter = new CrimeAdapter(this, crimes);
        ListView listView = findViewById(R.id.crimesList);
        listView.setAdapter(crimeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(ManageCrime.this,Verify_Delete.class);
                intent.putExtra("crime" , crimes.get(position));
                //based on item add info to intent
                startActivity(intent);
            }

        });


    }



}
