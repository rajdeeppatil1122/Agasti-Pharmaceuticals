package com.rajdeep.agastipharmaceuticals;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
    LinearLayout my_orders_ll, new_orders_ll, profile_ll;
    TextView my_orders_txt, new_orders_txt, profile_txt;
    ImageView my_orders_image, new_orders_image, profile_image;
//    RecyclerViewAdapterNewOrder recyclerViewAdapterNewOrder;
//    RecyclerView recyclerView;

    // We will have the tabs ranging from 1-3
    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        ArrayList<NewOrderMetaData> arrOrders = new ArrayList<>();
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        my_orders_ll = findViewById(R.id.my_orders_ll);
        new_orders_ll = findViewById(R.id.new_order_ll);
        profile_ll = findViewById(R.id.profile_ll);

        my_orders_txt = findViewById(R.id.my_orders_txt);
        new_orders_txt = findViewById(R.id.new_order_txt);
        profile_txt = findViewById(R.id.profile_txt);

        my_orders_image = findViewById(R.id.my_orders_image);
        new_orders_image = findViewById(R.id.new_orders_image);
        profile_image = findViewById(R.id.profile_image);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, MyOrdersFragment.class, null).commit();

        my_orders_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (selectedTab != 1 && selectedTab == 2) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Switching tab will lose the orders you put. Proceed?\n");

                    // Set Alert Title
                    builder.setTitle("Switch Tab");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                        // set my_orders fragment
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, MyOrdersFragment.class, null).commit();

                        // Unselect all tabs except the my_orders
                        new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        new_orders_txt.setVisibility(View.INVISIBLE);
                        profile_txt.setVisibility(View.INVISIBLE);

                        new_orders_image.setImageResource(R.drawable.online_shopping);
                        profile_image.setImageResource(R.drawable.profile);

                        // Select the my_orders tab
                        my_orders_txt.setVisibility(View.VISIBLE);
                        my_orders_image.setImageResource(R.drawable.check_list);
                        my_orders_ll.setBackgroundResource(R.drawable.background_my_orders);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        my_orders_ll.startAnimation(scaleAnimation);

                        // set 1st tab as selected
                        selectedTab = 1;

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

                else{
                    // set my_orders fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, MyOrdersFragment.class, null).commit();

                    // Unselect all tabs except the my_orders
                    new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    new_orders_txt.setVisibility(View.INVISIBLE);
                    profile_txt.setVisibility(View.INVISIBLE);

                    new_orders_image.setImageResource(R.drawable.online_shopping);
                    profile_image.setImageResource(R.drawable.profile);

                    // Select the my_orders tab
                    my_orders_txt.setVisibility(View.VISIBLE);
                    my_orders_image.setImageResource(R.drawable.check_list);
                    my_orders_ll.setBackgroundResource(R.drawable.background_my_orders);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    my_orders_ll.startAnimation(scaleAnimation);

                    // set 1st tab as selected
                    selectedTab = 1;
                }

            }
        });

        new_orders_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 2) {

                    // set new_orders fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, NewOrdersFragment.class, null).commit();

                    // Unselect all tabs except the new_order
                    my_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    my_orders_txt.setVisibility(View.INVISIBLE);
                    profile_txt.setVisibility(View.INVISIBLE);

                    my_orders_image.setImageResource(R.drawable.check_list);
                    profile_image.setImageResource(R.drawable.profile);

                    // Select the new_order tab
                    new_orders_txt.setVisibility(View.VISIBLE);
                    new_orders_image.setImageResource(R.drawable.online_shopping);
                    new_orders_ll.setBackgroundResource(R.drawable.background_new_orders);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    new_orders_ll.startAnimation(scaleAnimation);

                    // set 1st tab as selected
                    selectedTab = 2;
                }

            }
        });

        profile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selectedTab != 3 && selectedTab == 2) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Switching tab will lose the orders you put. Proceed?\n");

                    // Set Alert Title
                    builder.setTitle("Switch Tab");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                        // set profile fragment
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, ProfileFragment.class, null).commit();

                        // Unselect all tabs except the new_order
                        my_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        my_orders_txt.setVisibility(View.INVISIBLE);
                        new_orders_txt.setVisibility(View.INVISIBLE);

                        my_orders_image.setImageResource(R.drawable.check_list);
                        new_orders_image.setImageResource(R.drawable.online_shopping);

                        // Select the new_order tab
                        profile_txt.setVisibility(View.VISIBLE);
                        profile_image.setImageResource(R.drawable.profile);
                        profile_ll.setBackgroundResource(R.drawable.background_profile);

                        // create animation
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        scaleAnimation.setDuration(200);
                        scaleAnimation.setFillAfter(true);
                        profile_ll.startAnimation(scaleAnimation);

                        // set 1st tab as selected
                        selectedTab = 3;

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

                else{
                    // set profile fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, ProfileFragment.class, null).commit();

                    // Unselect all tabs except the new_order
                    my_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    my_orders_txt.setVisibility(View.INVISIBLE);
                    new_orders_txt.setVisibility(View.INVISIBLE);

                    my_orders_image.setImageResource(R.drawable.check_list);
                    new_orders_image.setImageResource(R.drawable.online_shopping);

                    // Select the new_order tab
                    profile_txt.setVisibility(View.VISIBLE);
                    profile_image.setImageResource(R.drawable.profile);
                    profile_ll.setBackgroundResource(R.drawable.background_profile);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profile_ll.startAnimation(scaleAnimation);

                    // set 1st tab as selected
                    selectedTab = 3;
                }

            }
        });
    }

    public void changeBottomBarToNewOrders() {

//        Context context = new_orders_ll.getContext();

        // Unselect all tabs except the new_order
        my_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        my_orders_txt.setVisibility(View.INVISIBLE);
        profile_txt.setVisibility(View.INVISIBLE);

        my_orders_image.setImageResource(R.drawable.check_list);
        profile_image.setImageResource(R.drawable.profile);

        // Select the new_order tab
        new_orders_txt.setVisibility(View.VISIBLE);
        new_orders_image.setImageResource(R.drawable.online_shopping);
        new_orders_ll.setBackgroundResource(R.drawable.background_new_orders);

        // create animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        new_orders_ll.startAnimation(scaleAnimation);

        // set 1st tab as selected
        selectedTab = 2;
    }

    @Override
    public void onBackPressed() {

        if (selectedTab == 2) {

            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);

            // Set the message show for the Alert time
            builder.setMessage("Switching tab will lose the orders you put. Proceed?\n");

            // Set Alert Title
            builder.setTitle("Switch Tab");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                // set my_orders fragment
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, MyOrdersFragment.class, null).commit();

                // Unselect all tabs except the my_orders
                new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                new_orders_txt.setVisibility(View.INVISIBLE);
                profile_txt.setVisibility(View.INVISIBLE);

                new_orders_image.setImageResource(R.drawable.online_shopping);
                profile_image.setImageResource(R.drawable.profile);

                // Select the my_orders tab
                my_orders_txt.setVisibility(View.VISIBLE);
                my_orders_image.setImageResource(R.drawable.check_list);
                my_orders_ll.setBackgroundResource(R.drawable.background_my_orders);

                // create animation
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                my_orders_ll.startAnimation(scaleAnimation);

                // set 1st tab as selected
                selectedTab = 1;

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
        else if(selectedTab==3){
            // set my_orders fragment
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, MyOrdersFragment.class, null).commit();

            // Unselect all tabs except the my_orders
            new_orders_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            profile_ll.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            new_orders_txt.setVisibility(View.INVISIBLE);
            profile_txt.setVisibility(View.INVISIBLE);

            new_orders_image.setImageResource(R.drawable.online_shopping);
            profile_image.setImageResource(R.drawable.profile);

            // Select the my_orders tab
            my_orders_txt.setVisibility(View.VISIBLE);
            my_orders_image.setImageResource(R.drawable.check_list);
            my_orders_ll.setBackgroundResource(R.drawable.background_my_orders);

            // create animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            my_orders_ll.startAnimation(scaleAnimation);

            // set 1st tab as selected
            selectedTab = 1;
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);

            // Set the message show for the Alert time
            builder.setMessage("Do you really want to exit Agasti App?\n");

            // Set Alert Title
            builder.setTitle("Exit App");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                super.onBackPressed();
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
    }
}