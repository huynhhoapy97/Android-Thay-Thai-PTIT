package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import truonghuynhhoa.ptit.buscity.R;
import truonghuynhhoa.ptit.model.BusRoute;

public class RouteAdapter extends ArrayAdapter<BusRoute> {

    private Activity context;
    private int resource;

    public RouteAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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

        // position là vị trí của một đối tượng(route) trong array adapter
        BusRoute busRoute = getItem(position);
        btnRouteId.setText(busRoute.getId().toString());
        txtRouteId.setText("Route " + busRoute.getId().toString());
        txtRouteName.setText(busRoute.getName());

        return itemRoute;
    }

}
