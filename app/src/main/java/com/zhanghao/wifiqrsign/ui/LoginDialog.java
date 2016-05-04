package com.zhanghao.wifiqrsign.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhanghao.wifiqrsign.R;

/**
 * Created by Ljx on 2016/4/21.
 */
public class LoginDialog extends Dialog{

    private Context mContext;

    private EditText location;
    private EditText name;

    private Button accept;
    private Button cancel;

    private String Loc;
    private String Name;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LoginDialog(Context context){
        super(context);
        this.mContext = context;
    }

    public LoginDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        sharedPreferences = mContext.getSharedPreferences("LoginIn", Context.MODE_WORLD_READABLE);
        editor = sharedPreferences.edit();

        accept = (Button) findViewById(R.id.accept);
        cancel = (Button) findViewById(R.id.cancel);
        location = (EditText) findViewById(R.id.location);
        name = (EditText) findViewById(R.id.name);

        Name = sharedPreferences.getString("name", null);
        Loc = sharedPreferences.getString("location", null);

        name.setText(Name);
        location.setText(Loc);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Loc = location.getText().toString();
                    Name = name.getText().toString();
                    Log.d("Loc", Loc);
                    Log.d("Name", Name);
                    editor.putString("location", Loc);
                    editor.putString("name", Name);
                    editor.commit();
                    LoginDialog.this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "请输入正确信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialog.this.dismiss();
            }
        });

    }
}
