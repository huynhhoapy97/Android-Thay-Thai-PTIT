package truonghuynhhoa.ptit.buscity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {

    private RadioButton radVietnamese, radEnglish;
    private Button btnClose, btnOK;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setFinishOnTouchOutside(false);

        this.setTitle("");
        addControls();
        addEvents();
    }

    private void addControls() {
        radVietnamese = findViewById(R.id.radVietnamese);
        radEnglish = findViewById(R.id.radEnglish);
        btnClose = findViewById(R.id.btnClose);
        btnOK = findViewById(R.id.btnOK);

        sharedPreferences = LanguageActivity.this.getSharedPreferences("languages", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        language = sharedPreferences.getString("language", "");

        if(language.equals("en")){
            radEnglish.setChecked(true);
        }
        else if(language.equals("vi")){
            radVietnamese.setChecked(true);
        }
    }

    private void addEvents() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!radVietnamese.isChecked() && !radEnglish.isChecked()){
                    if(language.equals("en") || language.equals("")){
                        Toast.makeText(LanguageActivity.this, "Please choose languages", Toast.LENGTH_SHORT).show();
                    }
                    else if(language.equals("vi")){
                        Toast.makeText(LanguageActivity.this, "Vui lòng chọn ngôn ngữ", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(radVietnamese.isChecked()){

                        if(language.equals("vi")){
                            finish();
                        }
                        else{
                            changeLanguage("vi", "VN");

                            // Lưu vào shared preferences
                            editor.putString("language" , "vi");
                            editor.commit();
                        }
                    }
                    else if(radEnglish.isChecked()){

                        if(language.equals("en")){
                            finish();
                        }
                        else{
                            changeLanguage("en", "US");

                            // Lưu vào shared preferences
                            editor.putString("language" , "en");
                            editor.commit();
                        }
                    }
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageActivity.this.finish();
            }
        });
    }

    private void changeLanguage(String language, String country) {
        // Inject language (vùng ngôn ngữ)
        Locale locale = new Locale(language, country);
        // Configure device (đối tượng cấu hình)
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        // Display information (đối tượng lưu thông tin kích thước trình bày)
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        // Update res strings.xml to install language (cài đặt ngôn ngữ, không ảnh hưởng đến hệ thống)
        getBaseContext().getResources().updateConfiguration(
                configuration,
                displayMetrics
        );
        // Load display with language (cập nhật lại màn hình thiết bị với ngôn ngữ mới)
        Intent intent = new Intent(LanguageActivity.this, ScreenActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
