package yargo.inc.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import javax.inject.Inject;

import yargo.inc.R;
import yargo.inc.app.di.ApplicationComponent;
import yargo.inc.common.network.repository.LoginRepository;

public class FirebaseMessaging extends FirebaseMessagingService {
    @Inject
    protected LoginRepository loginRepository;

    public FirebaseMessaging() {
        super();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_yargo_notificate, options);

        Intent resultIntent = null;
//        Intent resultIntent = new Intent(this, OrderActivity.class);
//        resultIntent.putExtra("_id", 15);
//        resultIntent.setAction("_id");
//        resultIntent.putExtra("idd", Integer.valueOf(idd));
//        resultIntent.setAction("idd");
//        resultIntent.putExtra("number", Integer.valueOf(idd));
//        resultIntent.setAction("number");
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), "notify_001")
                .setSmallIcon(R.drawable.ic_yargo_notificate)
                .setLargeIcon(bitmap)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        notificationManager.notify(notificationId, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        saveToken(token);
    }

    private void saveToken(String token) {
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
    }
}
