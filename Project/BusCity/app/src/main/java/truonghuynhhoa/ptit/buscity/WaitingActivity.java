package truonghuynhhoa.ptit.buscity;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class WaitingActivity extends AppCompatActivity {
    private ImageView imgWaiting;
    private AnimationDrawable animationDrawable;

    private SharedPreferences sharedPreferences;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        setFinishOnTouchOutside(false);

        addControls();
        addEvents();
    }

    private void addControls() {
        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");

        if(language.equals("en") || language.equals("")){
            this.setTitle("Waiting...");
        }
        else if(language.equals("vi")){
            this.setTitle("Chờ giây lát...");
        }

        imgWaiting = findViewById(R.id.imgWaiting);
        imgWaiting.setBackgroundResource(R.drawable.run);

        animationDrawable = (AnimationDrawable) imgWaiting.getBackground();

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

    private void addEvents() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        animationDrawable.stop();
    }
}
