package truonghuynhhoa.ptit.buscity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import truonghuynhhoa.ptit.model.BusRoute;
import truonghuynhhoa.ptit.model.BusStation;
import truonghuynhhoa.ptit.model.BusTurn;
import truonghuynhhoa.ptit.model.Instruction;
import truonghuynhhoa.ptit.model.StationOfRoute;

import static java.lang.Double.MAX_VALUE;

public class FindActivity extends AppCompatActivity {
    private EditText edtOrigin;
    private EditText edtDestination;
    private Button btnFind;
    private Button btnExit;
    private ImageView imgChangePosition;
    private AnimationDrawable animationDrawable;
    private Animation animationShake, animationTotalShake;
    private Geocoder geocoder;

    private ArrayList<BusStation> listBusStation;
    private ArrayList<StationOfRoute> listStationOrigin, listStationDestination;

    private ArrayList<BusRoute> listRouteFinded;
    private ArrayList<Instruction> listInstruction;

    private ArrayList<Integer> listNumericalOrderOrigin, listNumericalOrderDestination, listTurn;

    private boolean isWifi;
    private boolean isMobile;

    private SharedPreferences sharedPreferences;
    public String language;

    private String origin;
    private String destination;
    private List<Address> addressesOrigin;
    private List<Address> addressesDestination;
    private Address locationOrigin;
    private Address locationDestination;
    private LatLng latLngOrigin;
    private LatLng latLngDestination;
    private int countRouteFind;

    private BusStation stationOrigin;
    private BusStation stationDestination;

    public boolean clickFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        setFinishOnTouchOutside(false);

        this.setTitle("");
        Log.e("CU_CREATE", "CREATE");

        addControls();
        addEvents();
    }

    private void addControls() {
        sharedPreferences = getSharedPreferences("languages", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "");

        edtOrigin = findViewById(R.id.edtOrigin);
        edtDestination = findViewById(R.id.edtDestination);

        btnFind = findViewById(R.id.btnFind);
        btnExit = findViewById(R.id.btnExit);

        imgChangePosition = findViewById(R.id.imgChangePosition);

        clickFind = false;

        animationShake = AnimationUtils.loadAnimation(FindActivity.this, R.anim.shake);
        animationTotalShake = AnimationUtils.loadAnimation(FindActivity.this, R.anim.total_shake);

        geocoder = new Geocoder(FindActivity.this);

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyDK7z6qtgizSwMqbe7CXwgPSWH27xQ2ohQ");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(FindActivity.this);

        listBusStation = new ArrayList<BusStation>();
        listStationDestination = new ArrayList<StationOfRoute>();

        listRouteFinded = new ArrayList<BusRoute>();
        listInstruction = new ArrayList<Instruction>();

        listNumericalOrderOrigin = new ArrayList<Integer>();
        listNumericalOrderDestination = new ArrayList<Integer>();
        listTurn = new ArrayList<Integer>();

        BusStationListTask busStationListTask = new BusStationListTask();
        busStationListTask.execute();
        try {
            listBusStation = busStationListTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {
        /*edtOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("VN")
                        .build(FindActivity.this);


                // kiểm tra mã request code đã chuyển cho intent
                startActivityForResult(intent, 1);
            }
        });

        edtDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("VN")
                        .build(FindActivity.this);

                // kiểm tra mã request code đã chuyển cho intent
                startActivityForResult(intent, 2);
            }
        });*/

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickFind = true;

                checkNetwork();
                if(isWifi == false && isMobile == false){
                    if(language.equals("en") || language.equals("")){
                        Toast.makeText(FindActivity.this, "Please connect internet to find Bus", Toast.LENGTH_SHORT).show();
                    }
                    else if(language.equals("vi")){
                        Toast.makeText(FindActivity.this, "Vui lòng kết nối mạng để tìm kiếm", Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                listInstruction.clear();
                listNumericalOrderOrigin.clear();
                listNumericalOrderDestination.clear();
                listTurn.clear();

                origin = edtOrigin.getText().toString();
                destination = edtDestination.getText().toString();
                addressesOrigin = new ArrayList<Address>();
                addressesDestination = new ArrayList<Address>();
                locationOrigin = null;
                locationDestination = null;
                latLngOrigin = null;
                latLngDestination = null;

                // Kiểm tra xem đã nhập điểm đầu, điểm đích hay chưa?
                if(origin.equals("")){
                    if(language.equals("en") || language.equals("")){
                        Toast.makeText(FindActivity.this, "Please enter origin", Toast.LENGTH_SHORT).show();
                        edtOrigin.startAnimation(animationShake);
                    }
                    else if(language.equals("vi")){
                        Toast.makeText(FindActivity.this, "Vui lòng nhập điểm đầu", Toast.LENGTH_SHORT).show();
                        edtOrigin.startAnimation(animationShake);
                    }

                    return;
                }
                if(destination.equals("")){
                    if(language.equals("en") || language.equals("")){
                        Toast.makeText(FindActivity.this, "Please enter destination", Toast.LENGTH_SHORT).show();
                        edtDestination.startAnimation(animationShake);
                    }
                    else if(language.equals("vi")){
                        Toast.makeText(FindActivity.this, "Vui lòng nhập điểm đích", Toast.LENGTH_SHORT).show();
                        edtDestination.startAnimation(animationShake);
                    }

                    return;
                }

                try {
                    addressesOrigin = geocoder.getFromLocationName(origin, 5);
                    addressesDestination = geocoder.getFromLocationName(destination, 5);

                    if (addressesOrigin.size() == 0) {
                        if(language.equals("en") || language.equals("")){
                            Toast.makeText(FindActivity.this, "Can't find origin", Toast.LENGTH_SHORT).show();
                            edtOrigin.startAnimation(animationShake);
                        }
                        else if(language.equals("vi")){
                            Toast.makeText(FindActivity.this, "Không tìm thấy điểm xuất phát", Toast.LENGTH_SHORT).show();
                            edtOrigin.startAnimation(animationShake);
                        }

                        return;
                    }
                    else{
                        Log.e("COUNT_ORIGIN", "" + addressesOrigin.size());
                        for(int i = 0; i < addressesOrigin.size(); i++){
                            Log.e("DETAILS_ORIGIN_" + i, "" +
                                    addressesOrigin.get(i).getAdminArea() + " - " +
                                    addressesOrigin.get(i).getSubAdminArea() + " - " +
                                    addressesOrigin.get(i).getLocality() + " - " +
                                    addressesOrigin.get(i).getSubLocality() + " - " +
                                    addressesOrigin.get(i).getCountryCode() + " - " +
                                    addressesOrigin.get(i).getCountryName() + " - " +
                                    addressesOrigin.get(i).getFeatureName());
                        }

                        locationOrigin = addressesOrigin.get(0);
                        latLngOrigin = new LatLng(locationOrigin.getLatitude(), locationOrigin.getLongitude() );
                    }

                    if (addressesDestination.size() == 0) {
                        if(language.equals("en") || language.equals("")){
                            Toast.makeText(FindActivity.this, "Can't find destination", Toast.LENGTH_SHORT).show();
                            edtDestination.startAnimation(animationShake);
                        }
                        else if(language.equals("vi")){
                            Toast.makeText(FindActivity.this, "Không tìm thấy điểm đến", Toast.LENGTH_SHORT).show();
                            edtDestination.startAnimation(animationShake);
                        }

                        return;
                    }
                    else{
                        Log.e("COUNT_DESTINATION", "" + addressesDestination.size());
                        for(int i = 0; i < addressesDestination.size(); i++){
                            Log.e("DETAILS_DESTINATION_" + i, "" +
                                    addressesDestination.get(i).getAdminArea() + " - " +
                                    addressesDestination.get(i).getSubAdminArea() + " - " +
                                    addressesDestination.get(i).getLocality() + " - " +
                                    addressesDestination.get(i).getSubLocality() + " - " +
                                    addressesDestination.get(i).getCountryCode() + " - " +
                                    addressesDestination.get(i).getCountryName() + " - " +
                                    addressesDestination.get(i).getFeatureName());
                        }

                        locationDestination = addressesDestination.get(0);
                        latLngDestination = new LatLng(locationDestination.getLatitude(), locationDestination.getLongitude() );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if((addressesOrigin.size() != 0) && (addressesDestination.size() != 0)){
                    // Tìm khoảng cách gần nhất khoảng 1000m ở mỗi trạm

                    Location lOrigin = new Location(LocationManager.GPS_PROVIDER);
                    Location lDestination = new Location(LocationManager.GPS_PROVIDER);

                    stationOrigin = new BusStation();
                    stationDestination = new BusStation();

                    double minOrigin = MAX_VALUE;
                    double minDestination = MAX_VALUE;

                    lOrigin.setLatitude(latLngOrigin.latitude);
                    lOrigin.setLongitude(latLngOrigin.longitude);

                    lDestination.setLatitude(latLngDestination.latitude);
                    lDestination.setLongitude(latLngDestination.longitude);

                    for(int i = 0; i < listBusStation.size(); i++){
                        Location lBusStation = new Location(LocationManager.GPS_PROVIDER);
                        lBusStation.setLatitude(listBusStation.get(i).getLatitude());
                        lBusStation.setLongitude(listBusStation.get(i).getLongtitude());

                        if(lOrigin.distanceTo(lBusStation) < 1000){
                            if(lOrigin.distanceTo(lBusStation) < minOrigin){
                                minOrigin = lOrigin.distanceTo(lBusStation);
                                stationOrigin = listBusStation.get(i);
                            }
                        }

                        if(lDestination.distanceTo(lBusStation) < 1000){
                            if(lDestination.distanceTo(lBusStation) < minDestination){
                                minDestination = lDestination.distanceTo(lBusStation);
                                stationDestination = listBusStation.get(i);
                            }
                        }
                    }

                    // Khi không tìm thấy trạm đầu hoặc trạm cuối với địa chỉ cung cấp thì thông báo và kết thúc
                    if(stationOrigin.getId() == null || stationDestination.getId() == null){
                        if(language.equals("en") || language.equals("")){
                            Toast.makeText(FindActivity.this, "No bus routes found matching the address provided", Toast.LENGTH_LONG).show();
                            edtOrigin.startAnimation(animationTotalShake);
                            edtDestination.startAnimation(animationTotalShake);
                        }
                        else if(language.equals("vi")){
                            Toast.makeText(FindActivity.this, "Không tìm thấy tuyến xe phù hợp với địa chỉ cung cấp", Toast.LENGTH_LONG).show();
                            edtOrigin.startAnimation(animationTotalShake);
                            edtDestination.startAnimation(animationTotalShake);
                        }

                        return;
                    }
                    else{
                        // Lấy khoảng cách trạm bắt và trạm dừng, kiểm tra trạm đầu có cùng lượt với trạm cuối trong tuyến đó
                        Location takeOrigin = new Location(LocationManager.GPS_PROVIDER);
                        Location takeDestination = new Location(LocationManager.GPS_PROVIDER);

                        takeOrigin.setLatitude(stationOrigin.getLatitude());
                        takeOrigin.setLongitude(stationOrigin.getLongtitude());

                        takeDestination.setLatitude(stationDestination.getLatitude());
                        takeDestination.setLongitude(stationDestination.getLongtitude());

                        // Tìm trạm đầu dựa vào trạm cuối tìm được
                        StationOfRouteListTask stationOfRouteListTask = new StationOfRouteListTask();
                        stationOfRouteListTask.execute(stationDestination.getId());

                        try {
                            listStationDestination = stationOfRouteListTask.get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Số tuyến tìm được phù hợp với 2 địa điểm cung cấp
                        countRouteFind = 0;

                        for(int i = 0; i < listStationDestination.size(); i++){
                            int checkStation = -1;

                            CheckStationTask checkStationTask = new CheckStationTask();
                            checkStationTask.execute(listStationDestination.get(i).getRoute().getId(),
                                    listStationDestination.get(i).getTurn().getId(),
                                    stationOrigin.getId(),
                                    listStationDestination.get(i).getNumericalOrder());

                            try {
                                checkStation = checkStationTask.get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(checkStation != -1){

                                countRouteFind++;

                                int numericalOrder = countRouteFind;
                                int busRoute = listStationDestination.get(i).getRoute().getId();
                                int walkDistance = (int)minOrigin;
                                int busDistance = (int)(takeOrigin.distanceTo(takeDestination));

                                Instruction instruction = new Instruction(numericalOrder, busRoute, walkDistance, busDistance);
                                listInstruction.add(instruction);

                                // Lấy chỉ số thú tự của 2 trạm tìm thấy hợp lý trong 1 tuyến
                                listNumericalOrderOrigin.add(checkStation);
                                listNumericalOrderDestination.add(listStationDestination.get(i).getNumericalOrder());
                                listTurn.add(listStationDestination.get(i).getTurn().getId());
                            }
                        }

                        Intent intentLoading = new Intent(FindActivity.this, LoadingActivity.class);
                        startActivity(intentLoading);
                        /*if(countRouteFind == 0){
                            if(language.equals("en") || language.equals("")){
                                Toast.makeText(FindActivity.this, "No bus routes found matching the address provided", Toast.LENGTH_LONG).show();
                            }
                            else if(language.equals("vi")){
                                Toast.makeText(FindActivity.this, "Không tìm thấy tuyến xe phù hợp với địa chỉ cung cấp", Toast.LENGTH_LONG).show();
                            }

                            return;
                        }*/

                        /*// Hiện lên danh sách các tuyến phù hợp được tìm thấy
                        Intent intent = new Intent(FindActivity.this, InstructionActivity.class);

                        // Gửi dữ liệu qua Instruction
                        Bundle bundle = new Bundle();
                        bundle.putString("origin", origin);
                        bundle.putString("destination", destination);
                        bundle.putSerializable("listInstruction", listInstruction);
                        bundle.putSerializable("listNumericalOrderOrigin", listNumericalOrderOrigin);
                        bundle.putSerializable("listNumericalOrderDestination", listNumericalOrderDestination);
                        bundle.putSerializable("listTurn", listTurn);
                        bundle.putString("stationOrigin", stationOrigin.getName());
                        bundle.putString("stationDestination", stationDestination.getName());

                        intent.putExtras(bundle);

                        startActivity(intent);*/
                    }
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CU_EXIT", "EXIT");
                finish();
            }
        });

        imgChangePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = edtOrigin.getText().toString();
                String destination = edtDestination.getText().toString();

                if(!(origin.equals("") && destination.equals(""))){
                    edtOrigin.setText(destination);
                    edtDestination.setText(origin);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("CU_PAUSE", "PAUSE");
    }

    @Override
    protected void onStart() {
        super.onStart();

        clickFind = false;
        Log.e("CU_START", "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("CU_RESUME", "RESUME");

        if(clickFind){
            if(countRouteFind == 0) {
                if (language.equals("en") || language.equals("")) {
                    Toast.makeText(FindActivity.this, "No bus routes found matching the address provided", Toast.LENGTH_LONG).show();
                    edtOrigin.startAnimation(animationTotalShake);
                    edtDestination.startAnimation(animationTotalShake);
                }
                else if (language.equals("vi")) {
                    Toast.makeText(FindActivity.this, "Không tìm thấy tuyến xe phù hợp với địa chỉ cung cấp", Toast.LENGTH_LONG).show();
                    edtOrigin.startAnimation(animationTotalShake);
                    edtDestination.startAnimation(animationTotalShake);
                }

                return;
            }

            // Hiện lên danh sách các tuyến phù hợp được tìm thấy
            Intent intent = new Intent(FindActivity.this, InstructionActivity.class);

            // Gửi dữ liệu qua Instruction
            Bundle bundle = new Bundle();
            bundle.putString("origin", origin);
            bundle.putString("destination", destination);
            bundle.putSerializable("listInstruction", listInstruction);
            bundle.putSerializable("listNumericalOrderOrigin", listNumericalOrderOrigin);
            bundle.putSerializable("listNumericalOrderDestination", listNumericalOrderDestination);
            bundle.putSerializable("listTurn", listTurn);
            bundle.putString("stationOrigin", stationOrigin.getName());
            bundle.putString("stationDestination", stationDestination.getName());

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Log.i("SEE_DETAILS_1", "Place: " + place.getAddress() + ", " + place.getLatLng());
                edtOrigin.setText(place.getAddress());
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("SEE_DETAILS_1", status.getStatusMessage());
            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Log.i("SEE_DETAILS_2", "Place: " + place.getAddress() + ", " + place.getLatLng());
                edtDestination.setText(place.getAddress());
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("SEE_DETAILS_2", status.getStatusMessage());
            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    class BusStationListTask extends AsyncTask<Void, Void, ArrayList<BusStation>>{

        @Override
        protected ArrayList<BusStation> doInBackground(Void... voids) {
            ArrayList<BusStation> busStationList = new ArrayList<BusStation>();

            try {
                URL url = new URL("http://buscity.somee.com/api/busstation");
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
                    double latitude = jsonObject.getDouble("Latitude");
                    double longtitude = jsonObject.getDouble("Longtitude");
                    String address = jsonObject.getString("Address");

                    BusStation busStation =
                            new BusStation(id, name, latitude, longtitude, address);

                    busStationList.add(busStation);
                }

                bufferedReader.close();
                inputStreamReader.close();
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }

            return busStationList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<BusStation> busStations) {
            super.onPostExecute(busStations);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    class StationOfRouteListTask extends AsyncTask<Integer, Void, ArrayList<StationOfRoute>>{

        @Override
        protected ArrayList<StationOfRoute> doInBackground(Integer... integers) {
            ArrayList<StationOfRoute> stationOfRouteList = new ArrayList<StationOfRoute>();

                try{
                String params = "?stationid=" + integers[0];

                URL url = new URL("http://buscity.somee.com/api/stationofroutedetail/" + params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setConnectTimeout(20000);

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();
                inputStreamReader.close();

                BusStation station = callHttpGetBusStation(integers[0]);

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for(int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    int routeId = jsonObject.getInt("RouteId");
                    int turnId = jsonObject.getInt("TurnId");

                    BusRoute route = callHttpGetBusRoute(routeId);
                    BusTurn turn = callHttpGetBusTurn(turnId);
                    int numericalOrder = jsonObject.getInt("NumericalOrder");
                    int nextStationTime = jsonObject.getInt("NextStationTime");

                    StationOfRoute stationOfRoute = new StationOfRoute(route, station, turn, numericalOrder, nextStationTime);
                    stationOfRouteList.add(stationOfRoute);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return stationOfRouteList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<StationOfRoute> stationOfRoutes) {
            super.onPostExecute(stationOfRoutes);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    class CheckStationTask extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            int check = -1;
            try{
                String params = "?routeid=" + integers[0] +
                        "&turnid=" + integers[1] +
                        "&stationid=" + integers[2] +
                        "&numericalorder=" + integers[3];

                URL url = new URL("http://buscity.somee.com/api/stationofroutedetail/" + params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setConnectTimeout(20000);

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();
                inputStreamReader.close();

                check = Integer.valueOf(stringBuilder.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return check;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            animationDrawable.stop();
        }
    }

    public void setList(ArrayList<StationOfRoute> list) {
        this.listStationDestination = list;
    }

    public ArrayList<StationOfRoute> getList() {
        return this.listStationDestination;
    }

    public void checkNetwork(){
        isWifi = false;
        isMobile = false;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo networkInfo : networkInfos){
            if(networkInfo.getTypeName().equalsIgnoreCase("WIFI")){
                if(networkInfo.isConnected()){
                    isWifi = true;
                }
            }
            if(networkInfo.getTypeName().equalsIgnoreCase("MOBILE")){
                if(networkInfo.isConnected()){
                    isMobile = true;
                }
            }
        }
    }

    public BusStation callHttpGetBusStation(int stationId){
        BusStation busStation = new BusStation();

        try {
            URL url = new URL("http://buscity.somee.com/api/busstation?id="+stationId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setConnectTimeout(20000);

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            int id = jsonObject.getInt("Id");
            String name = jsonObject.getString("Name");
            Double latitude = jsonObject.getDouble("Latitude");
            Double longtitude = jsonObject.getDouble("Longtitude");
            String address = jsonObject.getString("Address");

            busStation = new BusStation(id, name, latitude, longtitude, address);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return busStation;
    }

    public BusRoute callHttpGetBusRoute(int routeId){
        BusRoute busRoute = new BusRoute();

        try {
            URL url = new URL("http://buscity.somee.com/api/busroute?id="+routeId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setConnectTimeout(20000);

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            int id = jsonObject.getInt("Id");
            String name = jsonObject.getString("Name");
            String startTime = jsonObject.getString("StartTime");
            String endTime = jsonObject.getString("EndTime");
            int routeLength = jsonObject.getInt("RouteLength");
            int runTime = jsonObject.getInt("RunTime");
            int spacingTime = jsonObject.getInt("SpacingTime");

            busRoute = new BusRoute(id, name, startTime, endTime, routeLength, runTime, spacingTime);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return busRoute;
    }

    public BusTurn callHttpGetBusTurn(int turnId){
        BusTurn busTurn = new BusTurn();

        try {
            URL url = new URL("http://buscity.somee.com/api/busturn?id="+turnId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setConnectTimeout(20000);

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            int id = jsonObject.getInt("Id");
            String name = jsonObject.getString("Name");

            busTurn = new BusTurn(id, name);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return busTurn;
    }
}
