package com.example.myapplication.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.services.AidlService;

import java.util.List;

public class AidlActivity extends AppCompatActivity {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IGameManager iGameManager = IGameManager.Stub.asInterface(service);
            Game game = new Game("game2", "des2");
            try {
                iGameManager.addGame(game);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                List<Game> list = iGameManager.getGameList();
                for (Game g : list) {
                    Log.v("wangxin", "game = " + g.name);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this, AidlService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        
    }

    @Override
    protected void onDestroy() {
        Log.v("AidlService", "activity onDestroy");
        super.onDestroy();
    }
}