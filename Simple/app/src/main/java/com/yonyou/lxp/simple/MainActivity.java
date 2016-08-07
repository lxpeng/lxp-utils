package com.yonyou.lxp.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yonyou.lxp.lxp_utils.adapter.CommonAdapter;
import com.yonyou.lxp.lxp_utils.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        ImageView imageView= (ImageView) findViewById(R.id.image);
//        Glide.with(this).load("http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg").into(imageView);


//        Http http=new Http();
//        http.post("", null, new Http.HttpCallBack() {
//            @Override
//            public void isSuccess(String data, Map<String, Object> mapData) {
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });

        initView();
    }


    private void initView() {

        List list=new ArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(i+"============");
        }

        adapter=new CommonAdapter<String>(this,list,android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setText(android.R.id.text1,item);
            }
        };
        adapter.setmOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
            Log.e("11111",item+"");
            Toast.makeText(MainActivity.this,item+"",Toast.LENGTH_SHORT).show();
//            }
        }
        });

//

        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
    }
}
