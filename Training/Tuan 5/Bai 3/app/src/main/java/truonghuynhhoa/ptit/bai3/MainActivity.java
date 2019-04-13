package truonghuynhhoa.ptit.bai3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.PlayerAdapter;
import truonghuynhhoa.ptit.model.Player;

public class MainActivity extends AppCompatActivity {

    private ListView lvPlayer;
    private PlayerAdapter playerAdapter;

    private Integer[] imagePlayers;
    private String[] names;
    private String[] birthdays;
    private Integer[] imageCountries;

    private List<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {

        imagePlayers = new Integer[]{R.drawable.b3_ronaldo, R.drawable.b3_messi, R.drawable.b3_kante, R.drawable.b3_modrid, R.drawable.b3_neymar, R.drawable.b3_reus};
        names = new String[]{"C.Ronaldo", "Messi", "Kante", "Modrid", "Neymar Jr", "Reus"};
        birthdays = new String[]{"February 5, 1985 (age 34 years)", "June 24, 1987 (age 31 years", "March 29, 1991 (age 28 years)", "September 9, 1985 (age 33 years)", "February 5, 1992 (age 27 years)", "May 31, 1989 (age 29 years)"};
        imageCountries = new Integer[]{R.drawable.portugal, R.drawable.argentina, R.drawable.france, R.drawable.croatia, R.drawable.brazil, R.drawable.germany};

        lvPlayer = findViewById(R.id.lvPlayer);
        playerAdapter = new PlayerAdapter(MainActivity.this, R.layout.item_player);
        lvPlayer.setAdapter(playerAdapter);

        playerList = new ArrayList<Player>();
        int size = imagePlayers.length;
        for(int i = 0; i < size; i++){
            Player player = new Player(imagePlayers[i], names[i], birthdays[i], imageCountries[i]);
            playerList.add(player);
        }

        playerAdapter.clear();
        playerAdapter.addAll(playerList);
    }

    private void addEvents() {

    }
}
