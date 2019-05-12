package truonghuynhhoa.ptit.buscity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class ScreenActivity extends AppCompatActivity {

    private ProgressBar progressScreen;
    private TextView txtLoaded;
    private SharedPreferences sharedPreferences;
    public static String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addControls();
        addEvents();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addControls() {
        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");
        if(language.equals("")){
            changeLanguage("en", "US");
        }

        progressScreen = findViewById(R.id.progressScreen);
        txtLoaded = findViewById(R.id.txtLoaded);

        ProgressScreenAnimation progressScreenAnimation =
                new ProgressScreenAnimation(
                        ScreenActivity.this,
                        progressScreen,
                        txtLoaded,
                        0f,
                        100f);

        progressScreenAnimation.setDuration(10000);

        progressScreen.setMax(100);
        progressScreen.setScaleY(3f);
        // Đổi màu áp dụng cho API 21+
        progressScreen.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
        progressScreen.setAnimation(progressScreenAnimation);
    }

    private void addEvents() {
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
    }
}
