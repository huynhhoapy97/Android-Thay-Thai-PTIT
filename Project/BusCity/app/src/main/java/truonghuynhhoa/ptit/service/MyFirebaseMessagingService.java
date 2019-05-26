package truonghuynhhoa.ptit.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import truonghuynhhoa.ptit.buscity.MainActivity;
import truonghuynhhoa.ptit.buscity.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*
        *  Có 2 kênh gửi, 1 là sử dụng Web riêng chúng ta tự viết, 2 là sử dụng kênh sẵn của Google Notification
        */

        // Cái này tức là gửi từ Server của Google xuống
        if (remoteMessage.getNotification() != null){
            showMessage(remoteMessage.getNotification().getBody());
        }
        showMessage(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
    }

    private void showMessage(String body) {
        showMessage(body, "Notify From Google");
    }

    private void showMessage(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
