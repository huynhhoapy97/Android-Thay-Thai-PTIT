package truonghuynhhoa.ptit.changeimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioGroup groupAnimal;
    RadioButton radChoice;
    ImageView imgAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        groupAnimal = findViewById(R.id.groupAnimal);
        imgAnimal = findViewById(R.id.imgAnimal);
    }

    private void addEvents() {

        groupAnimal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = groupAnimal.getCheckedRadioButtonId();
                radChoice = findViewById(checkedId);

                if(radChoice.getText().toString().equals("Bird")){
                    imgAnimal.setImageResource(R.drawable.bird);
                    Log.e("Notify", "Chim");
                }
                else if(radChoice.getText().toString().equals("Cat")){
                    imgAnimal.setImageResource(R.drawable.cat);
                    Log.e("Notify", "Mèo");
                }
                else if(radChoice.getText().toString().equals("Dog")){
                    imgAnimal.setImageResource(R.drawable.dog);
                    Log.e("Notify", "Chó");
                }
                else if(radChoice.getText().toString().equals("Rabbit")){
                    imgAnimal.setImageResource(R.drawable.rabbit);
                    Log.e("Notify", "Thỏ");
                }
                else if(radChoice.getText().toString().equals("Pig")){
                    imgAnimal.setImageResource(R.drawable.pig);
                    Log.e("Notify", "Heo");
                }
            }
        });
    }
}
