package truonghuynhhoa.ptit.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThongTinCaNhanActivity extends AppCompatActivity {

    TextView txtData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincanhan);
        addControls();
    }

    private void addControls() {
        txtData = findViewById(R.id.txtData);
        Intent intent = getIntent();
        String text = intent.getStringExtra("msg");
        txtData.setText("");
        txtData.append(text);
    }

    public void Close(View view) {
        finish();
    }
}
