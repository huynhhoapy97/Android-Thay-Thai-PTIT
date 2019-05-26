package truonghuynhhoa.ptit.buscity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import truonghuynhhoa.ptit.adapter.InstructionAdapter;
import truonghuynhhoa.ptit.model.Instruction;

public class InstructionActivity extends AppCompatActivity {

    private String origin, destination, stationOrigin, stationDestination;
    private ArrayList<Instruction> listInstruction;
    private ArrayList<Integer> listNumericalOrderOrigin, listNumericalOrderDestination, listTurn;
    private TextView txtOrigin, txtDestination;
    private ListView lvInstruction;
    private InstructionAdapter instructionAdapter;

    private Instruction instruction;
    private int numericalOrderOrigin;
    private int numericalOrderDestination;
    private int turn;

    private boolean clickDetail;

    private Intent intent;
    private Bundle bundle;

    private SharedPreferences sharedPreferences;
    public String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        addControls();
        addEvents();
    }

    private void addControls() {

        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");

        txtOrigin = findViewById(R.id.txtOrigin);
        txtDestination = findViewById(R.id.txtDestination);
        lvInstruction = findViewById(R.id.lvInstruction);
        instructionAdapter =
                new InstructionAdapter(
                        InstructionActivity.this,
                        R.layout.item_instruction,
                        language);

        lvInstruction.setAdapter(instructionAdapter);

        // Lấy dữ liệu từ Find gửi qua
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        origin = bundle.getString("origin");
        destination = bundle.getString("destination");
        listInstruction = (ArrayList<Instruction>) bundle.getSerializable("listInstruction");
        listNumericalOrderOrigin = (ArrayList<Integer>) bundle.getSerializable("listNumericalOrderOrigin");
        listNumericalOrderDestination = (ArrayList<Integer>) bundle.getSerializable("listNumericalOrderDestination");
        listTurn = (ArrayList<Integer>) bundle.getSerializable("listTurn");
        stationOrigin = bundle.getString("stationOrigin");
        stationDestination = bundle.getString("stationDestination");

        instructionAdapter.clear();
        instructionAdapter.addAll(listInstruction);

        txtOrigin.setText(origin);
        txtDestination.setText(destination);

        instruction = new Instruction();
        numericalOrderOrigin = -1;
        numericalOrderDestination = -1;
        turn = -1;

        clickDetail = false;
    }

    private void addEvents() {
        lvInstruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                instruction = (Instruction) parent.getItemAtPosition(position);
                numericalOrderOrigin = listNumericalOrderOrigin.get(position);
                numericalOrderDestination = listNumericalOrderDestination.get(position);
                turn = listTurn.get(position);

                intent = new Intent(InstructionActivity.this, DetailInstructionActivity.class);
                bundle = new Bundle();
                bundle.putString("origin", origin);
                bundle.putString("destination", destination);
                bundle.putInt("busRoute", instruction.getBusRoute());
                bundle.putInt("walkDistance", instruction.getWalkDistance());
                bundle.putInt("busDistance", instruction.getBusDistance());
                bundle.putInt("numericalOrderOrigin", numericalOrderOrigin);
                bundle.putInt("numericalOrderDestination", numericalOrderDestination);
                bundle.putInt("turn", turn);
                bundle.putString("stationOrigin", stationOrigin);
                bundle.putString("stationDestination", stationDestination);

                intent.putExtras(bundle);

                clickDetail = true;

                Intent intentWaiting = new Intent(InstructionActivity.this, WaitingActivity.class);
                startActivity(intentWaiting);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("HUONGDAN_PAUSE", "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("HUONGDAN_STOP", "STOP");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("HUONGDAN_START", "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("HUONGDAN_RESUME", "RESUME");

        if(clickDetail){
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            //overridePendingTransition (0, 0);
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            clickDetail = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("HUONGDAN_DESTROY", "DESTROY");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
