package com.example.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.myapplication.aidl.Game;
import com.example.myapplication.aidl.IGameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxin on 2021/7/16.
 */

public class AidlService extends Service {

    private List<Game> mList = new ArrayList<>();
    private Binder mBinder = new IGameManager.Stub() {
        @Override
        public List<Game> getGameList() throws RemoteException {
            return mList;
        }



        @Override
        public void addGame(Game game) throws RemoteException {
            mList.add(game);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mList.add(new Game("game1", "desc1"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    
}
