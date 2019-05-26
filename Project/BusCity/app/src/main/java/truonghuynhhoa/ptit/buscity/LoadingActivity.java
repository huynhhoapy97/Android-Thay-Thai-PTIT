package truonghuynhhoa.ptit.buscity;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class LoadingActivity extends AppCompatActivity {

    private ImageView imgLoading;
    private AnimationDrawable animationDrawable;
    private SharedPreferences sharedPreferences;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        setFinishOnTouchOutside(false);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {

        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");

        if(language.equals("en") || language.equals("")){
            this.setTitle("Finding...");
        }
        else if(language.equals("vi")){
            this.setTitle("Đang tìm...");
        }

        imgLoading = findViewById(R.id.imgLoading);
        animationDrawable = (AnimationDrawable) imgLoading.getDrawable();

        animationDrawable.start();

        Thread thread = new Thread(){
            @Override
            public void run() {
                int progress = 0;

                while (progress < 100){

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progress++;
                }

                finish();
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MOI_DESTROY", "DESTROY");
        animationDrawable.stop();
    }
}
