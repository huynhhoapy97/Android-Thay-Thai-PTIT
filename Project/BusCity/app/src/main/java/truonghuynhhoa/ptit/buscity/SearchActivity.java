package truonghuynhhoa.ptit.buscity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import truonghuynhhoa.ptit.adapter.RouteAdapter;
import truonghuynhhoa.ptit.model.BusRoute;
import truonghuynhhoa.ptit.model.BusTurn;
import truonghuynhhoa.ptit.model.Host;

public class SearchActivity extends AppCompatActivity {

    private ListView lvRoutes;
    private RouteAdapter routeAdapter;
    private EditText edtSearch;

    private ArrayList<BusRoute> busRouteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addControls();
        addEvents();
    }

    private void addControls() {
        busRouteList = new ArrayList<BusRoute>();

        lvRoutes = findViewById(R.id.lvRoutes);
        routeAdapter = new RouteAdapter(SearchActivity.this, R.layout.item_route);

        lvRoutes.setAdapter(routeAdapter);

        // Triệu gọi tiểu trình cho nó chạy
        BusRouteListTask busRouteListTask = new BusRouteListTask();
        busRouteListTask.execute();
        try {
            busRouteList = busRouteListTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        edtSearch = findViewById(R.id.edtSearch);
    }

    private void addEvents() {
        lvRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =
                        new Intent(SearchActivity.this, RouteActivity.class);

                BusRoute busRoute = (BusRoute) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("routeId", busRoute.getId());
                bundle.putString("routeName", busRoute.getName());

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<BusRoute> busRoutes = new ArrayList<BusRoute>();

                if(s != null && s.length() > 0){
                    for(int i = 0; i < busRouteList.size(); i++){
                        if(busRouteList.get(i).getId().toString().contains(s.toString())){
                            busRoutes.add(busRouteList.get(i));
                        }
                    }

                    routeAdapter.clear();
                    routeAdapter.addAll(busRoutes);
                }
                else{
                    routeAdapter.clear();
                    routeAdapter.addAll(busRouteList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class BusRouteListTask extends AsyncTask<Void, Void, ArrayList<BusRoute>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Nhận kết quả trả về sau khi kết thúc 1 tiểu trình
        @Override
        protected void onPostExecute(ArrayList<BusRoute> busRoutes) {
            super.onPostExecute(busRoutes);
            routeAdapter.clear();
            routeAdapter.addAll(busRoutes);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // Xử lý dữ liệu với Web Service
        @Override
        protected ArrayList<BusRoute> doInBackground(Void... voids) {
            ArrayList<BusRoute> busRouteList = new ArrayList<BusRoute>();

            try {
                URL url = new URL("http://" + Host.ip + "/buscity/api/busroute");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                InputStreamReader inputStreamReader =
                        new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt("Id");
                    String name = jsonObject.getString("Name");

                    BusRoute busRoute =
                    new BusRoute(id, name);

                    busRouteList.add(busRoute);
                }

                bufferedReader.close();
                inputStreamReader.close();
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }

            return busRouteList;
        }
    }
}
