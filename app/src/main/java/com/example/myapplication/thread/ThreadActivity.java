package com.example.myapplication.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.myapplication.R;

public class ThreadActivity extends AppCompatActivity {

    private Handler threadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        new ThreadA().start();
        new ThreadB().start();

        testTask testTask = new testTask();
        testTask.cancel(true);


    }

    class ThreadA extends Thread {

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Log.v("wangxin111", "ThreadA run");

            /**threadHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //收到来自于ThreadB的消息，注意这里运行在ThreadA线程中
                    switch (msg.obj.toString()){
                        case  "ThreadB发送消息到ThreadA":
                            Log.v("wangxin111", "ThreadB发送消息到ThreadA");
                            Toast.makeText(getBaseContext(),"ThreadB发送消息到ThreadA", Toast.LENGTH_LONG).show();
                            break;
                    }


                    //......
                }
            };*/
            Log.v("wangxin111", "ThreadA threadHandler");
            Log.v("wangxin111", "looper A = " + Looper.myLooper());

            Looper.loop();

        }
    }

    class ThreadB extends Thread {

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.v("wangxin111", "ThreadB run");
            Looper looper = Looper.myLooper();
            Log.v("wangxin111", "looper B= " + looper);

            Message message = new Message();
            message.obj = "ThreadB发送消息到ThreadA";
            //......
            threadHandler.sendMessage(message);

            Looper.loop();
        }

    }

    class testTask extends AsyncTask<Void, Integer,Integer>{

        @Override
        protected  Integer doInBackground(Void... voids) {
            if (isCancelled()){
                return null;
            }
            return null;
        }
    }

}