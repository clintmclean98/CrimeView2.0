package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class Login extends AppCompatActivity {
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);





        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon); // drawer closed-reset icon
        } else {
            //open drawer
            drawerLayout.openDrawer(GravityCompat.START);
            drawerToggle.setHomeAsUpIndicator(R.drawable.ic_new_icon); // set your back icon
        }*/


    }

    public void signIn(View view) {

        EditText username = findViewById(R.id.edtLogin);
        EditText password = findViewById(R.id.edtPassword);
        databaseHandler = DatabaseHandler.getInstance();
        User user = null;
        try {
            if (databaseHandler.signIn( password.getText().toString() , username.getText().toString())) {
                Toast.makeText(this, "Successful Login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                intent.putExtra("user" , username.getText().toString());
                startActivity(intent);


            } else {

                Log.d("Login Fail", username.getText().toString());
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
