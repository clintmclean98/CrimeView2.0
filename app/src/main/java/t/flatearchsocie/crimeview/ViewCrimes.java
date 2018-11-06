package t.flatearchsocie.crimeview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCrimes extends Activity {

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_crimes);

        databaseHandler = DatabaseHandler.getInstance();

        Spinner areasSpinner = findViewById(R.id.dropDown_area);

        ArrayList<String> areas = new ArrayList<>();
        areas.add("All");
            try {
                areas = databaseHandler.getAreas();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item , areas);

        areasSpinner.setAdapter(arrayAdapter);





    }
}
