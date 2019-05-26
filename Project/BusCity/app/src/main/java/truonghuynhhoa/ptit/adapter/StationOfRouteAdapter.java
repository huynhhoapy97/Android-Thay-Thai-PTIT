package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import truonghuynhhoa.ptit.buscity.R;
import truonghuynhhoa.ptit.model.StationOfRoute;


public class StationOfRouteAdapter extends ArrayAdapter<StationOfRoute> {

    private Activity context;
    private int resource;
    private String language;

    public StationOfRouteAdapter(Activity context, int resource, String language) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.language = language;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View itemStaionOfRoute = layoutInflater.inflate(this.resource, null);

        TextView txtStationName = itemStaionOfRoute.findViewById(R.id.txtStationName);
        TextView txtNextStationTime = itemStaionOfRoute.findViewById(R.id.txtNextStationTime);

        StationOfRoute stationOfRoute = getItem(position);
        txtStationName.setText(stationOfRoute.getStation().getName());

        if(language.equals("en") || language.equals("")){
            txtNextStationTime.setText("+" + stationOfRoute.getNextStationTime().toString() + " minutes");
        }
        else if(language.equals("vi")){
            txtNextStationTime.setText("+" + stationOfRoute.getNextStationTime().toString() + " phút");
        }

        Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha);
        itemStaionOfRoute.startAnimation(animation);
        return itemStaionOfRoute;
    }
}
