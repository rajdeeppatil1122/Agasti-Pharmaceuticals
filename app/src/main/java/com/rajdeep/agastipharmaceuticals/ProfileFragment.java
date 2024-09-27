package com.rajdeep.agastipharmaceuticals;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    TextView name, address, email, contact_no;
    ImageView circleImageView;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.profileName);
        address = view.findViewById(R.id.profileAddress);
        email = view.findViewById(R.id.profileEmail);
        contact_no = view.findViewById(R.id.profileContactNo);
        circleImageView = view.findViewById(R.id.circlesImageView);
        logout = view.findViewById(R.id.btnLogout);

        // setting right size of circles image:
        int height  = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        circleImageView.getLayoutParams().height = (int) (height/2.8);

//        Toast.makeText(getContext(), "Width is : " + width + " Height is : " + height, Toast.LENGTH_SHORT).show();

        String[] stringArray = new String[4];

        MyDBHelper myDBHelper = new MyDBHelper(getContext());
        stringArray = myDBHelper.fetchData();

        //  System.out.println(stringArray[0] + " " + stringArray[1]);

        // Setting views...
        name.setText(stringArray[0]);
        email.setText(stringArray[1]);
        contact_no.setText(stringArray[2]);
        address.setText(stringArray[3]);

        // logout...
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // Set the message show for the Alert time
                builder.setMessage("Do you really want to logout?\n");

                // Set Alert Title
                builder.setTitle("Logging Out...");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                    // Setting shared-preference false...
                    SharedPreferences pref2 = getContext().getSharedPreferences("login", MODE_PRIVATE);      // Here, the object of shared preference is different, but we are taking the same reference (of login).
                    SharedPreferences.Editor editor = pref2.edit();

                    editor.putBoolean("flag", false);
                    editor.apply();

                    // Emptying the database...
                    myDBHelper.deleteEverythingFromTable();

                    Intent i = new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finishAffinity();

                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();


            }
        });

        return view;
    }
}