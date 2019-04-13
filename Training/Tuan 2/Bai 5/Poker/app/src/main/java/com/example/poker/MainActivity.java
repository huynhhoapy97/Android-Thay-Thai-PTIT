package com.example.poker;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.poker.R.drawable.ks;

public class MainActivity extends AppCompatActivity {

    TextView txt_score;
    Button btn_play;
    ImageView img_1;
    ImageView img_2;
    ImageView img_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    @SuppressLint("ResourceType")
    private void addControls() {
        txt_score = findViewById(R.id.txt_score);
        btn_play = findViewById(R.id.btn_play);
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        card[0]=new pocker(R.drawable.ah,R.drawable.ad,R.drawable.ac,R.drawable.as);
        card[1]=new pocker(R.drawable.h2,(R.drawable.d2),(R.drawable.c2),R.drawable.s2);
        card[2]=new pocker((R.drawable.h3),(R.drawable.d3),(R.drawable.c3),(R.drawable.s3));
        card[3]=new pocker((R.drawable.h4),(R.drawable.d4),(R.drawable.c4),(R.drawable.s4));
        card[4]=new pocker((R.drawable.h5),(R.drawable.d5),(R.drawable.c5),(R.drawable.s5));
        card[5]=new pocker((R.drawable.h6),(R.drawable.d6),(R.drawable.c6),(R.drawable.s6));
        card[6]=new pocker((R.drawable.h7),(R.drawable.d7),(R.drawable.c7),(R.drawable.s7));
        card[7]=new pocker((R.drawable.h8),(R.drawable.d8),(R.drawable.c8),(R.drawable.s8));
        card[8]=new pocker((R.drawable.h9),(R.drawable.d9),(R.drawable.c9),(R.drawable.s9));
        card[9]=new pocker((R.drawable.h10),(R.drawable.d10),(R.drawable.c10),(R.drawable.s10));
        card[10]=new pocker((R.drawable.jh),(R.drawable.jd),(R.drawable.jc),(R.drawable.js));
        card[11]=new pocker((R.drawable.qh),(R.drawable.qd),(R.drawable.qc),(R.drawable.qs));
        card[12]=new pocker((R.drawable.kh),(R.drawable.kd),(R.drawable.kc),(R.drawable.ks));
    }
    public class pocker {
        private int id_h;
        private  int id_d;
        private int id_c;
        private int id_s;
        private int id_pocker;

        public pocker(int viewById, int viewById1, int viewById2, int viewById3) {
            id_h =  viewById;
            id_d =  viewById1;
            id_c = viewById2;
            id_s =  viewById3;
        }
        public int randompocker(){
            Integer [] pick = new Integer[4];
            pick[0] =  id_h;
            pick[1] =  id_d;
            pick[2] =  id_c;
            pick[3] = id_s;
            Random rand = new Random();
            int randomIndex = rand.nextInt(pick.length);
            id_pocker = pick[randomIndex];
            return id_pocker;
        }

    }
    pocker [] card = new pocker[13];
    @SuppressLint("ResourceType")
    public void playAgain(View view) {
        String score ="Score";
        btn_play.setText("PLAY AGAIN");
        int Diem1,Diem2,Diem3;
        Random rand = new Random();
        int randomCard1 = rand.nextInt(card.length);
        int randomCard2 = rand.nextInt(card.length);
        int randomCard3 = rand.nextInt(card.length);
        int card1,card2,card3;
        card1=card[randomCard1].randompocker();
        card2=card[randomCard2].randompocker();
        card3=card[randomCard3].randompocker();
        while( card1==card2 || card1 == card3 || card3 == card2){
            card1=card[randomCard1].randompocker();
            card2=card[randomCard2].randompocker();
            card3=card[randomCard3].randompocker();
        }
        if(randomCard1 < 10 ){
            Diem1 = randomCard1 + 1;
        } else Diem1 = 0;
        if(randomCard2 < 10 ){
            Diem2 = randomCard2 + 1;
        } else Diem2 = 0;
        if(randomCard3 < 10 ){
            Diem3 = randomCard3 + 1;
        } else Diem3 = 0;
        int Tong = Diem1 + Diem2 + Diem3;
        if(Tong>10)
        {
            Tong = Tong % 10;
        }else
            if(Tong==10){Tong = 0;}
        img_1.setImageResource(card1);
        img_2.setImageResource(card2);
        img_3.setImageResource(card3);
        score+=Tong;
        txt_score.setText(score);
    }
}
