package com.example.kh.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.kh.myapplication.Module.ToDo;
import com.example.kh.myapplication.Sqlite.MySqlite;
import com.facebook.stetho.Stetho;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName() ;
    private List<ToDo> list  = Collections.emptyList();
    @BindView(R.id.txtData)
    TextView txtData;

    @BindView(R.id.etId)
    TextView etId;

    @BindView(R.id.etTask)
    TextView etTask;
    @BindView(R.id.etDoi)
    TextView etDoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();

    }

    public void newList(){
        list  = MySqlite.getMySqlite(this).getAll();
        StringBuilder stringBuilder = new StringBuilder("");
        for(ToDo s : list){
            stringBuilder.append("Id: "+s.getId()+" ,Task "+s.getTask()+", Doi "+s.getDoi()+"\n");
        }

        txtData.setText(stringBuilder.toString());
    }

    @OnClick(R.id.btnLoadData)
    public void btnLoadData(){
        newList();
    }

    @OnClick(R.id.btnInsert)
    public void btnInsert(){
        Log.i(TAG, "btnInsert: "+"");
        MySqlite.getMySqlite(this).insert(etTask.getText().toString().trim(), etDoi.getText().toString().trim());
        newList();
    }
    @OnClick(R.id.btnUpdate)
    public void btnUpdate(){
        int id = Integer.valueOf( etId.getText().toString().trim());
        Log.i(TAG, "btnUpdate: ");
        MySqlite.getMySqlite(this).update(id, etTask.getText().toString().trim(),etDoi.getText().toString().trim());
        newList();
    }
    @OnClick(R.id.btnDelete)
    public void btnDelete(){
        int id = Integer.valueOf( etId.getText().toString().trim());
        MySqlite.getMySqlite(this).remove(id);
        newList();
    }
}
