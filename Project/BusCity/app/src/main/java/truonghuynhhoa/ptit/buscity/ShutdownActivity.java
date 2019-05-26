package truonghuynhhoa.ptit.buscity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ShutdownActivity extends AppCompatActivity {

    private TextView txtAppName, txtShutdownTitle;
    private ImageView imgAppLogo;

    Animation shutdownAlpha, shutdownRotate, shutdownFromBottom;

    private SharedPreferences sharedPreferences;
    public String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shutdown);

        addControls();
        addEvents();
    }

    private void addControls() {

        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");

        txtAppName = findViewById(R.id.txtAppName);
        txtShutdownTitle = findViewById(R.id.txtShutdownTitle);
        imgAppLogo = findViewById(R.id.imgAppLogo);

        if(language.equals("en") || language.equals("")){
            txtShutdownTitle.setText("See you again");
        }
        else if(language.equals("vi")){
            txtShutdownTitle.setText("Hẹn gặp lại");
        }

        shutdownAlpha = AnimationUtils.loadAnimation(ShutdownActivity.this, R.anim.shutdown_alpha);
        shutdownFromBottom = AnimationUtils.loadAnimation(ShutdownActivity.this, R.anim.shutdown_frombottom);
        shutdownRotate = AnimationUtils.loadAnimation(ShutdownActivity.this, R.anim.shutdown_rotate);

        txtAppName.startAnimation(shutdownRotate);
        txtShutdownTitle.startAnimation(shutdownFromBottom);

        shutdownFromBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAppLogo.startAnimation(shutdownAlpha);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        shutdownAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // finish: Xóa Activity hiện tại và quay ra Activity trước đó
                // finishAffinity: Xóa hết tất cả các Activity trong Task
                // Task là tập hợp các Activities mà người dùng tương tác khi thực hiện một công việc nhất định
                // .Một Task giữ các Activities, được sắp xếp trong một ngăn xếp(Stack) gọi là ngăn xếp ngược(Back Stack)
                finishAffinity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void addEvents() {
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
