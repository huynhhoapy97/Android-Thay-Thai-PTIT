package truonghuynhhoa.ptit.buscity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static truonghuynhhoa.ptit.buscity.ScreenActivity.language;

public class DataActivity extends AppCompatActivity {

    // Tên CSDL
    private String DATABASE_NAME = "buscity.sqlite";
    // Thư mục lưu trữ SQLite trong thư mục gốc cài đặt
    private String DB_PATH_SUFFIX = "/databases/";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    private ImageView imgBack;
    private Button btnUpdate;
    private TextView txtNotifyUpdate, txtDateUpdate;

    private ProgressDialog progress;
    private int totalProgressTime;
    private int start;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        addControls();
        addEvents();
    }

    private void addControls() {

        sharedPreferences = DataActivity.this.getSharedPreferences("updates", MODE_PRIVATE);
        // editor giúp lưu dữ liệu xuống file .xml
        editor = sharedPreferences.edit();

        imgBack = findViewById(R.id.imgBack);
        btnUpdate = findViewById(R.id.btnUpdate);
        txtNotifyUpdate = findViewById(R.id.txtNotifyUpdate);
        txtDateUpdate = findViewById(R.id.txtDateUpdate);

        // từ đối tượng Context (Activity) có thể xác định vị trí lưu trữ của DB
        File databaseFile = getDatabasePath(DATABASE_NAME);
        if(!databaseFile.exists()){
            if(language.equals("en") || language.equals("")){
                txtNotifyUpdate.setText("Not Update");
            }
            else if(language.equals("vi")){
                txtNotifyUpdate.setText("Chưa cập nhật");
            }

            txtDateUpdate.setText("");
        }
        else{
            String notifyUpdate = "";
            if(language.equals("en") || language.equals("")){
                notifyUpdate = "Last updated date";
            }
            else if(language.equals("vi")){
                notifyUpdate = "Cập nhật gần nhất";
            }
            txtNotifyUpdate.setText(notifyUpdate);
            String dateUpdate = sharedPreferences.getString("date", "");
            txtDateUpdate.setText(dateUpdate);
        }
    }

    private void addEvents() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataActivity.this.finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyDatabaseFromAssetsToSystem();
            }
        });
    }

    // Phải chép vào hệ thống thì ứng dụng mới thao tác được với CSDL này
    private void copyDatabaseFromAssetsToSystem() {
        // từ đối tượng Context (Activity) có thể xác định vị trí lưu trữ của DB
        File databaseFile = getDatabasePath(DATABASE_NAME);

        if(databaseFile.exists()){
            if(language.equals("en") || language.equals("")){
                Toast.makeText(DataActivity.this, "Data now is the new version. Not need to update", Toast.LENGTH_LONG).show();
            }
            else if(language.equals("vi")){
                Toast.makeText(DataActivity.this, "Dữ liệu hiện tại là phiên bản mới nhất. Không cần cập nhật", Toast.LENGTH_LONG).show();
            }

        }
        else{
            try{
                // Khởi tạo chi tiết hộp thoại thông báo
                AlertDialog.Builder builder = new AlertDialog.Builder(DataActivity.this);
                if(language.equals("en") || language.equals("")){
                    // Thiết lập tiêu đề
                    builder.setTitle("Update data");
                    // Thiết lập icon
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    // Thiết lập nội dung cho hộp thoại
                    builder.setMessage("Data is ready to update. Do you want to update now?");
                    // Thiết lập các nút lệnh tương tác
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            copyDatabaseFromAssets();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ẩn hộp thoại đi
                            dialog.dismiss();
                        }
                    });
                }
                else if(language.equals("vi")){
                    // Thiết lập tiêu đề
                    builder.setTitle("Cập nhật dữ liệu");
                    // Thiết lập icon
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    // Thiết lập nội dung cho hộp thoại
                    builder.setMessage("Dữ liệu đã sẵn sàng. Bạn muốn cập nhật ngay bây giờ?");
                    // Thiết lập các nút lệnh tương tác
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            copyDatabaseFromAssets();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ẩn hộp thoại đi
                            dialog.dismiss();
                        }
                    });
                }

                // Tạo hộp thoại
                AlertDialog dialog = builder.create();
                // Không đóng hộp thoại khi nhấn ở ngoài
                dialog.setCanceledOnTouchOutside(false);
                // Hiển thị hộp thoại lên
                dialog.show();
            }
            catch (Exception e){
                Log.e("ERROR DATABASE", e.toString());
            }

        }
    }

    private void copyDatabaseFromAssets() {
        try{
            // Đọc CSDL đó trong assets, lưu vào inputStream
            InputStream inputStream = getAssets().open(DATABASE_NAME);

            // Lấy đường dẫn thư mục gốc mà CSDL đó phải nằm trong đó
            String outputFileName = getDatabasePath();

            // Nếu không kiểm tra thì lần chạy tiếp theo nó sẽ xóa dữ liệu đi, vì nó thấy tạo rồi nó sẽ không tạo nữa
            // Vì mới lần đầu chạy thì chắc chắn folder databases chưa được tạo
            File file = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            // Tạo 1 luồng để ghi dữ liệu ra outputStream
            OutputStream outputStream = new FileOutputStream(outputFileName);

            // Mỗi lần đọc 1024 bytes
            byte[] bytes = new byte[1024];
            int length;
            // đọc vào mảng bytes
            while ((length = inputStream.read(bytes)) > 0){
                // ghi xuống CSDL từ mảng bytes với vị trí đầu tiên và chiều dài bằng cả mảng bytes
                outputStream.write(bytes, 0, length);
            }

            // Những gì còn lại trong ống đó phải lấy ra hết
            outputStream.flush();
            // phải đóng kết nối lại như nói với HĐH rằng đã đọc xong hết từ mảng bytes đó, nếu không dữ liệu đọc ra vào CSDL bằng rỗng
            outputStream.close();
            inputStream.close();

            // Hiện hình ảnh Progress Bar
            download();


        }
        catch (Exception e){
            Log.e("ERROR COPY", e.toString());
        }
    }

    private String getDatabasePath(){
        // getApplicationInfo().dataDir trỏ đến đúng package của ta, nghĩa là trỏ tới thư mục gốc của ta
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void download(){
        progress = new ProgressDialog(DataActivity.this);
        if(language.equals("en") || language.equals("")){
            progress.setMessage("Downloading ...");
        }
        else if(language.equals("vi")){
            progress.setMessage("Đang tải ...");
        }

        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Hiển thị được phần trăm chạy
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.setMax(100);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        totalProgressTime = 100;
        start = 0;

        Thread t = new Thread(){
            @Override
            public void run() {
                while(start < totalProgressTime) {
                    try {
                        // Dừng việc thực thi tiểu trình hiện tại được chỉ định trong một khoảng thời gian tính bằng mili giây, giá trị này không được âm
                        sleep(200);
                        start += 5;
                        progress.setProgress(start);

                        if(start == 100){
                            // Hiển thị thông tin cập nhật mới nhất
                            String notifyUpdate = "";
                            if(language.equals("en") || language.equals("")){
                                notifyUpdate = "Last updated date";
                            }
                            else if(language.equals("vi")){
                                notifyUpdate = "Cập nhật gần nhất";
                            }
                            // Hiển thị thông tin cập nhật mới nhất

                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String dateUpdate = simpleDateFormat.format(date);

                            UpdateTask updateTask = new UpdateTask();
                            updateTask.execute(notifyUpdate, dateUpdate);
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                progress.dismiss();
            }
        };
        t.start();
    }

    class UpdateTask extends AsyncTask<String, String, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            txtNotifyUpdate.setText(values[0]);
            txtDateUpdate.setText(values[1]);
        }

        @Override
        protected Void doInBackground(String... strings) {
            // Lưu thông tin cập nhật vào SharedPreferences
            editor.putString("date" , strings[1]);
            // xác nhận lưu xuống file .xml
            editor.commit();

            publishProgress(strings[0], strings[1]);

            return null;
        }
    }
}
