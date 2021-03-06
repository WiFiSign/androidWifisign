package com.zhanghao.wifiqrsign.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhanghao.wifiqrsign.Event.EventHelper;
import com.zhanghao.wifiqrsign.R;
import com.zhanghao.wifiqrsign.utils.SharedPreHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by 张浩 on 2016/4/12.
 */
public class SignHomeFragment extends Fragment {
    @Bind(R.id.sign_tip)
    TextView signTip;
    @Bind(R.id.sign_name)
    TextView signName;
    private String result;
    private boolean isSigned;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signhome, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        SharedPreHelper sharedPreHelper = new SharedPreHelper(getContext());
        String name = sharedPreHelper.getName();
        signName.setText(name);
        Log.d("Msg", name);
    }

    @Subscribe(threadMode = ThreadMode.PostThread)
    public void getMessage(EventHelper event) {
        result = event.getResult();
        isSigned = event.isSignEd();
        if (isSigned) {
            Log.v("isSigned", String.valueOf(isSigned));
            initView();
        }
        if (result != null && !result.isEmpty()) {
            signTip.setText(result);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Log.d("test", result);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
