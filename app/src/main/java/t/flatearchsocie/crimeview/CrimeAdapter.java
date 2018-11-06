package t.flatearchsocie.crimeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

public class CrimeAdapter extends ArrayAdapter<Crime> {
    public CrimeAdapter(Context context, List<Crime> resource) {
        super(context, R.layout.individual_crime_layout, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View personView = inflater.inflate(R.layout.individual_crime_layout, parent, false);

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        // keep track of person this view is working with
        personView.setTag(getItem(position));

        // get text views that will hold strings
        TextView txtCategory = personView.findViewById(R.id.txtCrimeCategory);
        TextView txtCrimeUser = personView.findViewById(R.id.txtCrimeUser);
        TextView txtDateReported = personView.findViewById(R.id.txtTimeReported);


        try {
            txtCategory.setText(databaseHandler.getCategory(getItem(position).getCategoryID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            txtCrimeUser.setText(databaseHandler.getUser(getItem(position).getUserID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtDateReported.setText(getItem(position).getTimeRecorded().toString());



        return personView;
    }

}
