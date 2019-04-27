package truonghuynhhoa.ptit.bai1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    // Tên CSDL
    private String DATABASE_NAME = "quanlymonhoc.sqlite";
    // Thư mục lưu trữ SQLite trong thư mục gốc cài đặt
    private String DB_PATH_SUFFIX = "/databases/";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    private Button btnMonHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        saoChepCoSoDuLieuTuAssetsVaoHeThong();

        btnMonHoc = findViewById(R.id.btnMonHoc);
    }

    private void addEvents() {
        btnMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MonHocActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phải chép vào hệ thống thì ứng dụng mới thao tác được với CSDL này
    private void saoChepCoSoDuLieuTuAssetsVaoHeThong() {
        // từ đối tượng Context (Activity) có thể xác định vị trí lưu trữ của DB
        File databaseFile = getDatabasePath(DATABASE_NAME);

        if(!databaseFile.exists()){
            try{
                saoChepCoSoDuLieuTuAssets();
            }
            catch (Exception e){
                Log.e("LOI DATABASE", e.toString());
            }

        }
    }

    private void saoChepCoSoDuLieuTuAssets() {
        try{
            // Đọc CSDL đó trong assets, lưu vào inputStream
            InputStream inputStream = getAssets().open(DATABASE_NAME);

            // Lấy đường dẫn thư mục gốc mà CSDL đó phải nằm trong đó
            String outputFileName = layDuongDan();

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
        }
        catch (Exception e){
            Log.e("LOI COPY", e.toString());
        }
    }

    private String layDuongDan(){
        // getApplicationInfo().dataDir trỏ đến đúng package của ta, nghĩa là trỏ tới thư mục gốc của ta
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}
