package com.example.formattext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String rs;
    Random r = new Random();
    RadioButton rabOdd, rabEven, rabBoth;
    CheckBox cbkTextColor, cbkCenter, cbkBackground;
    EditText edtResult;
    Button btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }


    private void addEvents() {
        rabBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chan  = 2*(r.nextInt(100)) + "";
                String le  = 2*(r.nextInt(100))+1 + "";
                rs = chan + " " + le;
            }
        });
        rabOdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 2*(r.nextInt(100))+1;
                rs = n + "" ;
            }
        });
        rabEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 2*(r.nextInt(100));
                rs = n + "";
            }
        });
    }

    private void addControls() {
        rabBoth = findViewById(R.id.rabBoth);
        rabEven = findViewById(R.id.rabEven);
        rabOdd = findViewById(R.id.rabOdd);

        cbkBackground = findViewById(R.id.cbkBackground);
        cbkCenter = findViewById(R.id.cbkCenter);
        cbkTextColor = findViewById(R.id.cbkTextColor);

        edtResult = findViewById(R.id.edtResult);
        btnResult = findViewById(R.id.btnResult);
    }

    public void xuLyResult(View view) {
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbkBackground.isChecked()) {
                    edtResult.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    edtResult.setBackgroundColor(getResources().getColor(R.color.colorxam));
                }
                if (cbkTextColor.isChecked()) {
                    edtResult.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    edtResult.setTextColor(getResources().getColor(R.color.colorblack));
                }
                if (cbkCenter.isChecked()) {
                    edtResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    edtResult.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }

                edtResult.setText(rs);
            }
        });



    }


}

