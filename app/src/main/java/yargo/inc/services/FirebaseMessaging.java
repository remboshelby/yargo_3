package yargo.inc.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import yargo.inc.common.network.repository.LoginRepository;

public class FirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
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
