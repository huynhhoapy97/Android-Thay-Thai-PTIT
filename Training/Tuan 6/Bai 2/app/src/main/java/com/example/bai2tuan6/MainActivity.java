package com.example.bai2tuan6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvcauthu;
    Button btninsert, btnupdate, btndelete;
    ArrayList<CauThu> data= new ArrayList<>();
    CauThuAdapter adapter=null;
    EditText tencauthu,giacauthu,teamcauthu;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        data.add(new CauThu(R.drawable.messi,"messi","12","Barca"));
        adapter = new CauThuAdapter(this, R.layout.activity_listitem_cauthu,data);
        lvcauthu.setAdapter(adapter);
        getCauThu();
        InsertCauThu();
        DeleteCauThu();
        UpdateCauThu();
        lvcauthu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CauThu cauThu=data.get(position);
                index=position;
                tencauthu.setText(cauThu.getTencauthu());
                giacauthu.setText(cauThu.getGiacauthu());
                teamcauthu.setText(cauThu.getTeamcauthu());

            }
        });




    }

    private void setControl() {

        tencauthu =(EditText) findViewById(R.id.tencauthu);
        giacauthu= (EditText) findViewById(R.id.giacauthu);
        teamcauthu= (EditText) findViewById(R.id.teamcauthu);
        lvcauthu= (ListView) findViewById(R.id.lisviewcauthu);
        btndelete=(Button) findViewById(R.id.buttondelete);
        btninsert=(Button) findViewById(R.id.buttoninsert);
        btnupdate=(Button) findViewById(R.id.buttonupdate);
    }

    private void UpdateCauThu() {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten= tencauthu.getText().toString();
                String gia= giacauthu.getText().toString();
                String team= teamcauthu.getText().toString();
                data.get(index).setTencauthu(ten);
                data.get(index).setGiacauthu(gia);
                data.get(index).setTeamcauthu(team);
                Toast.makeText(MainActivity.this,"Update Thanh Cong",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();

            }
        });}

    private void DeleteCauThu() {
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index>=0){
                    data.remove(index);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this," Xoa Thanh Cong",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(MainActivity.this,"Xoa Khong THanh Cong",Toast.LENGTH_LONG).show();

            }
        });
      }

    private void InsertCauThu() {
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CauThu cauThu= getCauThu();
                data.add(cauThu);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private CauThu getCauThu() {
        CauThu cauThu = new CauThu();
        cauThu.setTencauthu(tencauthu.getText().toString());
        cauThu.setGiacauthu(giacauthu.getText().toString());
        cauThu.setTeamcauthu(teamcauthu.getText().toString());
        return cauThu;
    }
}
