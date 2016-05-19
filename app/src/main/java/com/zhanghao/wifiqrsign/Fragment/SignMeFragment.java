package com.zhanghao.wifiqrsign.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhanghao.wifiqrsign.Bean.SignBean;
import com.zhanghao.wifiqrsign.R;
import com.zhanghao.wifiqrsign.utils.SharedPreHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * Created by 张浩 on 2016/4/12.
 */
public class SignMeFragment extends Fragment {

    private String name;
    @Bind(R.id.list)
    ListView listView;
    @Bind(R.id.Refresh_SignData)
    SwipeRefreshLayout RefreshSignData;
    private List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signme, null);
        ButterKnife.bind(this, view);
        initView();
        initRefreshData();
        return view;
    }


    public void initView(){
        SharedPreHelper sharedPreHelper = new SharedPreHelper(getContext());
        name = sharedPreHelper.getName();
        //GetSign();
    }


    public void GetSign() {
        BmobQuery<SignBean> query = new BmobQuery<SignBean>();
        query.addWhereEqualTo("name", name);

        query.findObjects(getContext(), new FindListener<SignBean>() {
            @Override
            public void onSuccess(List<SignBean> list) {
                Collections.reverse(list);
                for (SignBean signBean : list) {
                    String Name = signBean.getName();
                    String Loc = signBean.getLocation();
                    String Time = signBean.getCreatedAt();
                    Log.v("Name:", Name);
                    Log.v("Loc:", Loc);
                    Log.v("Time", Time);
                    data.add(map(Name, Loc, Time));
                }
                String [] from = new String[] {"Name", "Location", "Time"};
                int [] to = new int[] {R.id.SignMe_name, R.id.SignMe_location, R.id.SignMe_time};
                SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                        R.layout.list_item, from, to);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public HashMap<String, String> map(String Name, String Location, String Time) {

        HashMap<String, String> map = new HashMap<>();

        map.put("Name", Name);
        map.put("Location", Location);
        map.put("Time", Time);
        return map;
    }

    public void initRefreshData(){
        RefreshSignData.setColorSchemeColors(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent
        );
        RefreshSignData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        Log.v("refreshed", "");
                        handler.sendEmptyMessage(200);
                    }
                }).start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    GetSign();
                    initView();
                    RefreshSignData.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
