package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import truonghuynhhoa.ptit.bai3.R;
import truonghuynhhoa.ptit.model.Player;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private Activity context;
    private int resource;

    public PlayerAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.item_player, null);

        ImageView imgPlayer = convertView.findViewById(R.id.imgplayer);
        TextView txtPlayerName = convertView.findViewById(R.id.txtPlayerName);
        TextView txtPlayerBirthday = convertView.findViewById(R.id.txtPlayerBirthday);
        ImageView imgCountry = convertView.findViewById(R.id.imgcountry);

        Player player = getItem(position);
        imgPlayer.setImageResource(player.getImagePlayer());
        txtPlayerName.setText(player.getName());
        txtPlayerBirthday.setText(player.getBirthday());
        imgCountry.setImageResource(player.getImageCountry());

        return convertView;
    }
}
