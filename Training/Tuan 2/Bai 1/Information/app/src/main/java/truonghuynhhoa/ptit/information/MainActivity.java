package truonghuynhhoa.ptit.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    CheckBox check1, check2, check3;
    RadioButton rd1, rd2, rd3;
    Button btn_gui;
    EditText edt_ten, edt_bosung, edt_cmnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void addControls() {
        edt_ten = findViewById(R.id.edt_ten);
        edt_cmnd = findViewById(R.id.edt_cmnd);
        edt_bosung = findViewById(R.id.edt_bosung);
        btn_gui = findViewById(R.id.btn_gui);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
    }

    public void xuLyGuiThongTin(View view) {
        Intent intent = new Intent(MainActivity.this, ThongTinCaNhanActivity.class);
        String msg = "";
        msg += edt_ten.getText().toString() + "\n" + edt_cmnd.getText().toString() + "\n";
        if (check1.isChecked()) {
            msg += check1.getText().toString() + "\n";

        }
        if (check2.isChecked()) {
            msg += check2.getText().toString() + "\n";
        }
        if (check3.isChecked()) {
            msg += check3.getText().toString() + "\n";
        }
        if (rd1.isChecked()) {
            msg += rd1.getText().toString() + "\n";
        } else if (rd2.isChecked()) {
            msg += rd2.getText().toString() + "\n";
        } else if (rd3.isChecked()) {
            msg += rd3.getText().toString() + "\n";
        }
        msg += "____________________\n";
        if(edt_bosung.getText() != null)
        {
            msg += edt_bosung.getText().toString() + "\n" + "____________________\n";
        }
        intent.putExtra("msg",msg);
        edt_ten.setText("");
        edt_cmnd.setText("");
        edt_bosung.setText("");
        if (check1.isChecked()) {
            check1.setChecked(true);

        }
        if (check2.isChecked()) {
            check2.setChecked(false);
        }
        if (check3.isChecked()) {
            check3.setChecked(false);
        }
        if (rd1.isChecked()) {
            rd1.setChecked(true);
        } else if (rd2.isChecked()) {
            rd2.setChecked(false);
        } else if (rd3.isChecked()) {
            rd3.setChecked(false);
        }

        startActivity(intent);
    }
}
