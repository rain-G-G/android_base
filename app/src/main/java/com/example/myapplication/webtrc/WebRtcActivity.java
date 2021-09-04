package com.example.myapplication.webtrc;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioRecord;
import android.os.Binder;
import android.os.Bundle;
import android.os.Message;
import android.text.style.LeadingMarginSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WebRtcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_rtc);


        Map map = new ArrayMap();
        map.put("1", 1);
        map.put("2", 2);

        Toast.makeText(getApplicationContext(), " res = " + map.size(), Toast.LENGTH_LONG).show();

    }


}