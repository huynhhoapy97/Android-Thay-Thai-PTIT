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
import truonghuynhhoa.ptit.model.Instruction;


public class InstructionAdapter extends ArrayAdapter<Instruction> {

    private Activity context;
    private int resource;
    private String language;

    public InstructionAdapter(Activity context, int resource, String language) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.language = language;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View itemInstruction = layoutInflater.inflate(this.resource, null);

        TextView txtNumericalOrder = itemInstruction.findViewById(R.id.txtNumericalOrder);
        TextView txtBusRouteInstruction = itemInstruction.findViewById(R.id.txtBusRouteInstruction);
        TextView txtWalkDistance = itemInstruction.findViewById(R.id.txtWalkDistance);
        TextView txtBusDistance = itemInstruction.findViewById(R.id.txtBusDistance);

        Instruction instruction = getItem(position);
        txtNumericalOrder.setText(String.valueOf(instruction.getNumericalOrder()));

        if(language.equals("en") || language.equals("")){
            txtBusRouteInstruction.setText("Take " + instruction.getBusRoute());
        }
        else if(language.equals("vi")){
            txtBusRouteInstruction.setText("Tuyến " + instruction.getBusRoute());
        }

        if(instruction.getWalkDistance() > 1000){
            double walkDistance = instruction.getWalkDistance() / 1000;
            txtWalkDistance.setText(String.valueOf(walkDistance) + " km");
        }
        else{
            txtWalkDistance.setText(String.valueOf(instruction.getWalkDistance()) + " m");
        }

        if(instruction.getBusDistance() > 1000){
            double busDistance = instruction.getBusDistance() / 1000;
            txtBusDistance.setText(String.valueOf(busDistance) + " km");
        }
        else{
            txtBusDistance.setText(String.valueOf(instruction.getBusDistance()) + " m");
        }

        Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha);
        itemInstruction.startAnimation(animation);
        return itemInstruction;
    }
}
