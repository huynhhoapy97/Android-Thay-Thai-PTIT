package com.example.bai2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private CheckBox chkWing;
    private CheckBox chkDress;
    private CheckBox chkHat;
    private CheckBox chkGlasses;
    private ImageView imgPicture;
    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        setEvent();
    }
    private void setEvent() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkWing.isChecked() && chkDress.isChecked() && chkHat.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.full_body);

                }
                else
                {
                    imgPicture.setImageResource(R.drawable.body);
                }
                if (chkWing.isChecked()) {
                    imgPicture.setImageResource(R.drawable.wing);

                }
                if (chkDress.isChecked()) {
                    imgPicture.setImageResource(R.drawable.dress);
                }
                if (chkHat.isChecked()) {
                    imgPicture.setImageResource(R.drawable.hat);
                }
                if (chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.glasses);
                }
                if (chkWing.isChecked() && chkDress.isChecked()) {
                    imgPicture.setImageResource(R.drawable.dress_wing);
                }
                if (chkWing.isChecked() && chkHat.isChecked()) {
                    imgPicture.setImageResource(R.drawable.hat_wing);
                }
                if (chkWing.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.glasses_wing);
                }
                if (chkDress.isChecked() && chkHat.isChecked()) {
                    imgPicture.setImageResource(R.drawable.hat_dress);
                }
                if (chkDress.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.dress_glasses);
                }
                if (chkHat.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.glasses_wing);
                }
                if (chkWing.isChecked() && chkDress.isChecked() && chkHat.isChecked()) {
                    imgPicture.setImageResource(R.drawable.hat_dress_wing);
                }
                if (chkWing.isChecked() && chkDress.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.dress_glasses_wing);
                }
                if (chkHat.isChecked() && chkDress.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.hat_dress_glasses);
                }
                if (chkWing.isChecked()&& chkHat.isChecked()&& chkDress.isChecked()){
                    imgPicture.setImageResource(R.drawable.hat_dress_wing);
                }
                if (chkWing.isChecked() && chkDress.isChecked() && chkHat.isChecked() && chkGlasses.isChecked()) {
                    imgPicture.setImageResource(R.drawable.full_body);

                }


            }
        });
    }

    private void setControl() {
        chkWing = (CheckBox) findViewById(R.id.chkWing);
        chkDress = (CheckBox) findViewById(R.id.chkDress);
        chkHat = (CheckBox) findViewById(R.id.chkHat);
        chkGlasses = (CheckBox) findViewById(R.id.chkGlasses);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        btnOK = (Button) findViewById(R.id.btnOK);
    }


}
