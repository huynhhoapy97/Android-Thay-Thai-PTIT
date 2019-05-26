package truonghuynhhoa.ptit.buscity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressScreenAnimation extends Animation {
    private Activity context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from;
    private float to;

    public ProgressScreenAnimation(Activity context, ProgressBar progressBar, TextView textView, float from, float to) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    // Áp dụng biến đổi
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        // interpolatedTime có giá trị random từ 0 đến 1.0
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int)value + " %");

        if(value == to){
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }
}
