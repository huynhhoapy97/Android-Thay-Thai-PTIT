package truonghuynhhoa.ptit.buscity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;

import truonghuynhhoa.ptit.adapter.FunctionAdapter;
import truonghuynhhoa.ptit.model.Function;

public class MainActivity extends AppCompatActivity {

    // Toolbar và Menu
    private Toolbar toolbar;
    private ImageView imgMenu;

    // Bản đồ
    private GoogleMap mMap;

    // Thuộc tính lắng nghe thay đổi vị trí trên bản đồ
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener;

    // Hai fuction chính
    private ListView lvFunction;
    private FunctionAdapter functionAdapter;

    // Nút điều khiển function
    private ImageView imgFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bản đồ sẵn sàng sử dụng, sẽ xuất hiện tại fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Thiết lập kiểu hiển thị bản đồ
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                // Cấp quyền truy cập vị trí
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);

                // Thiết đặt cho bản đồ hiểu được sự thay đổi vị trí của chúng ta
                mMap.setOnMyLocationChangeListener(myLocationChangeListener);
            }
        });

        addControls();
        addEvents();
        addDatas();
    }

    private void addDatas() {
        functionAdapter.add(new Function(R.drawable.find, "Find"));
        functionAdapter.add(new Function(R.drawable.search, "Search"));
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgMenu = findViewById(R.id.imgMenu);

        myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                // Vĩ tuyến và kinh tuyến tại vị trí lắng nghe
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                // Chi tiết của marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                /*
                    Cung cấp thông tin chi tiết cho marker
                    Khi bản đồ hiện ra sẽ nhảy tới marker đó và phóng to ra
                 */
                Marker marker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                /*LatLng latLng12 = new LatLng(10.848322, 106.774995);
                mMap.addMarker(new MarkerOptions().position(latLng12).icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop)));

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(latLng);
                polylineOptions.add(latLng12);
                mMap.addPolyline(polylineOptions.width(5).color(Color.GREEN));*/
            }
        };

        lvFunction = findViewById(R.id.lvFunction);
        functionAdapter = new FunctionAdapter(MainActivity.this, R.layout.item_function);

        lvFunction.setAdapter(functionAdapter);

        imgFunction = findViewById(R.id.imgPlus);

        /*try {
            InetAddress addr = InetAddress.getByName("HOA");
            String ip = addr.getHostAddress();
            Log.e("IPNE", ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
    }

    private void addEvents() {
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        // Nhấn nút điều khiển function, nếu listview chứa function đang đóng thì mở ra và ngược lại
        imgFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFunction.setVisibility(lvFunction.isShown() ? View.GONE : View.VISIBLE);
            }
        });

        lvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent =
                            new Intent(MainActivity.this, FindActivity.class);
                    startActivity(intent);
                }
                else if(position == 1){
                    Intent intent =
                            new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
