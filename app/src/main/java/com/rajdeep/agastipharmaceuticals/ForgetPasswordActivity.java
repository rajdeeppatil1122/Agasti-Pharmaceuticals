package com.rajdeep.agastipharmaceuticals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText forget_pass_edittext;
    Button send_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_forget_password);

        forget_pass_edittext = findViewById(R.id.etxt_email_otp);
        send_otp = findViewById(R.id.btn_send_otp);

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int randomNumber = random.nextInt(9000) + 1000;     // at least 1000 and max 10000
                System.out.println("Random 4-digit number: " + randomNumber);

                String email = forget_pass_edittext.getText().toString();
                System.out.println(email);
                EmailSender emailSender = new EmailSender(ForgetPasswordActivity.this);
                emailSender.sendEmail(email, "Reset your password (Agasti Pharmaceuticals)", String.valueOf(randomNumber), randomNumber);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}