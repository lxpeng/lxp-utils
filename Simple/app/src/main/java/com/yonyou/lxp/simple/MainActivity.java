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
import com.yonyou.lxp.lxp_utils.base.BaseActivity;
import com.yonyou.lxp.simple.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.IMianView {

    @BindView(R.id.rv)
    RecyclerView rv;

    private CommonAdapter<String> adapter;

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {

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
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void refreshError(String error) {

    }
}
