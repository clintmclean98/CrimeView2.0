package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

public class ManageCrime extends AppCompatActivity {
    DatabaseHandler databaseHandler;
String menutype = "menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_crime);
        databaseHandler = DatabaseHandler.getInstance();
        Intent intent = this.getIntent();
        menutype = intent.getStringExtra("menu");
        try {
            populateCrimes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
