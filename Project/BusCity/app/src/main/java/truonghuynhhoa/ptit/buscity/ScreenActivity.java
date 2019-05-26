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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class ScreenActivity extends AppCompatActivity {

    private ProgressBar progressScreen;
    private TextView txtLoaded, txtAppName;
    private ImageView imgAppLogo;
    private Animation frombottom, fromtop, rotate;
    private SharedPreferences sharedPreferences;
    public String language;

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

        txtAppName = findViewById(R.id.txtAppName);
        imgAppLogo = findViewById(R.id.imgAppLogo);

        fromtop = AnimationUtils.loadAnimation(ScreenActivity.this, R.anim.fromtop);
        frombottom = AnimationUtils.loadAnimation(ScreenActivity.this, R.anim.frombottom);

        txtAppName.setAnimation(fromtop);
        imgAppLogo.setAnimation(frombottom);

        rotate = AnimationUtils.loadAnimation(ScreenActivity.this, R.anim.rotate);

        frombottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAppLogo.setAnimation(rotate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
