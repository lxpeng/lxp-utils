package com.yonyou.lxp.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yonyou.lxp.lxp_utils.net.Http;

import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView= (ImageView) findViewById(R.id.image);
        Glide.with(this).load("http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg").into(imageView);


        Http http=new Http();
        http.post("", null, new Http.HttpCallBack() {
            @Override
            public void isSuccess(String data, Map<String, Object> mapData) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
