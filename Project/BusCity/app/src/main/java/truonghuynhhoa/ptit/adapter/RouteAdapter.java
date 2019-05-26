package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import truonghuynhhoa.ptit.buscity.R;
import truonghuynhhoa.ptit.model.BusRoute;

import static android.content.Context.MODE_PRIVATE;

public class RouteAdapter extends ArrayAdapter<BusRoute> {

    private Activity context;
    private int resource;

    private BusRoute busRoute;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String language;

    public RouteAdapter(Activity context, int resource, String language) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.language = language;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // mode private có nghĩa chỉ mình ứng dụng này xài thôi vì nó lưu trong hệ thống app này
        // muốn tất cả ứng dụng khác xài thì lưu trông SD Card
        // sharedPreferences luôn luôn khác null vì getSharedPreferences() sẽ kiểm tra nếu tên tập tin chưa có thì nó tạo mới với định dạng .xml, có rồi thì đọc
        sharedPreferences = context.getSharedPreferences("routes", MODE_PRIVATE);
        // editor giúp lưu dữ liệu xuống file .xml
        editor = sharedPreferences.edit();
        // đánh dấu lưu trữ trạng thái

        /*
            Công việc của LayoutInflater là đọc xml layout file
            và chuyển đổi các thuộc tính của nó thành 1 View trong Java code
          */
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View itemRoute = layoutInflater.inflate(this.resource, null);

        // controls nằm trong activity nào thì lấy activity đó gọi ra
        Button btnRouteId = itemRoute.findViewById(R.id.btnRouteId);
        TextView txtRouteId = itemRoute.findViewById(R.id.txtRouteId);
        TextView txtRouteName = itemRoute.findViewById(R.id.txtRouteName);
        CheckBox chkSaveRoute = itemRoute.findViewById(R.id.chkSaveRoute);

        // position là vị trí của một đối tượng(route) trong array adapter
        busRoute = getItem(position);
        btnRouteId.setText(busRoute.getId().toString());

        if(language.equals("en") || language.equals("")){
            txtRouteId.setText("Route " + busRoute.getId().toString());
        }
        else if(language.equals("vi")){
            txtRouteId.setText("Tuyến " + busRoute.getId().toString());
        }
        txtRouteName.setText(busRoute.getName());

        int routeId = sharedPreferences.getInt(busRoute.getId().toString(), -1);
        if(routeId != -1){
            chkSaveRoute.setChecked(true);
        }

        chkSaveRoute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    BusRoute open = getItem(position);

                    editor.putInt(open.getId().toString() , open.getId());
                    // xác nhận lưu xuống file .xml
                    editor.commit();

                }
                else{
                    BusRoute close = getItem(position);

                    // đánh dấu lưu trữ trạng thái
                    editor.remove(close.getId().toString());
                    // xác nhận lưu xuống file .xml
                    editor.commit();

                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha);
        itemRoute.startAnimation(animation);
        return itemRoute;
    }

}
