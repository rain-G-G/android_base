package com.example.myapplication.view.Listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

/**
 *  listview android:entries的用法：（主要是布局文件中的xml属性）
 *      1、布局文件里用常用的XML属性：
 *          android:divider 设置列表项的分隔条（既可以用颜色，也可以用Drawable分隔）
 *          android:dividerHeight 设置分隔条的高度
 *          android:headerDividersEnabled 如果设置 false，则不在 header view 之前绘制分隔条
 *          android:footerDividersEnabled 如果设置 false，则不在 footer view 之前绘制分隔条
 *          android:entries 指定一个数组资源，Android 将根据该数组资源来生成 ListView
 *          android:listSelector="@android:color/transparent" 点击item 显示时的背景颜色
 *      2、注意点：
 *          android:headerDividersEnabled 生效的前提是listview要设置 headerview
 */
public class ListViewEntriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_entries_layout);

        ListView listView = findViewById(R.id.listview1);
        listView.setOnItemClickListener(null);
        View headerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_notification, null);
        listView.addHeaderView(headerView);
        listView.addFooterView(headerView);
    }
}