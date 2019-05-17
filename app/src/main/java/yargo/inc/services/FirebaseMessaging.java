package yargo.inc.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.app.App;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.app.AppResponse;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.orders.R;

import static yargo.inc.common.dto.CommonSharedPreferences.FCM_KEY;

public class FirebaseMessaging extends FirebaseMessagingService {
    @Inject
    protected LoginRepository loginRepository;
    @Inject
    protected CommonSharedPreferences commonSharedPreferences;

    public static final String TAG = FirebaseMessaging.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        App.getApplicationComponent().inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "fsdfsdfsdf");
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_yargo_notificate, options);

        Log.d(TAG, notification.getBody());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), "notify_001")
                .setSmallIcon(R.drawable.ic_yargo_notificate)
                .setLargeIcon(bitmap)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

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
        commonSharedPreferences.putObject(FCM_KEY, token);
    }
}
