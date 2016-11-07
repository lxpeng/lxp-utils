package com.yonyou.lxp.simple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yonyou.lxp.lxp_utils.adapter.CommonAdapter;
import com.yonyou.lxp.lxp_utils.adapter.ViewHolder;
import com.yonyou.lxp.lxp_utils.base.BaseActivity;
import com.yonyou.lxp.lxp_utils.net.ParamsMap;
import com.yonyou.lxp.lxp_utils.utils.JsonUtils;
import com.yonyou.lxp.lxp_utils.utils.PasswordHash;
import com.yonyou.lxp.simple.contract.MainContract;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

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

        List list = new ArrayList();
        list.add("http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201609/18/3C48ABF04D39DD712769DFDEFF4EBFB9.gif");
        list.add("http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201609/12/0BD5D302207A10E007875506BA4E3F43.gif");

        adapter = new CommonAdapter<String>(this, list, R.layout.item_test) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setImageGifByUrl(R.id.img_gif, item);
            }
        };
        adapter.setmOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
                Log.e("11111", item + "");
                Toast.makeText(MainActivity.this, item + "", Toast.LENGTH_SHORT).show();
//            }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
    }

    public static String JOKE_URL = "http://v.juhe.cn/";
    public static String JOKE_URL_RAND = "joke/randJoke.php";
    public static String HTTP_URL_NEWS = "toutiao/index";
    public static String JUHE_JOKE_KEY = "246c3f91174a8e0dec575fc56da75adf";

    @Override
    public void doBusiness() {
//        Uri uri=Uri.parse("http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201609/18/3C48ABF04D39DD712769DFDEFF4EBFB9.gif");
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setAutoPlayAnimations(true)
//                .build();
//        imgGif.setImageGifURI(uri,controller);

        ParamsMap paramsMap = new ParamsMap(JOKE_URL, JOKE_URL_RAND)
                .put("key", JUHE_JOKE_KEY);
//        ResponseMap responseMap=new ResponseMap().putData()
//        Http.post(paramsMap, null, new Http.HttpCallBack() {
//            @Override
//            public void isSuccess(String data, Map<String, Object> mapData) {
//                Observable.just(data).filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return JsonUtils.isSuccess(s, "code", "1");
//                    }
//                });
//
//
//                final bean[] bs = new bean[]{};
//                Observable.from(bs).map(new Func1<bean, String>() {
//                    @Override
//                    public String call(bean bean) {
//                        return bean.name;
//                    }
//                }).subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//
//
//                Observable.from(bs).subscribe(new Action1<bean>() {
//                    @Override
//                    public void call(bean bean) {
//                        for (String ss : bean.list) {
//                            System.out.println(ss);
//                        }
//                    }
//                });
//
//
//                Observable.from(bs).
//                        flatMap(b -> Observable.from(b.list))
//                        .subscribe(s -> System.out.println(s));
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });


        String jsonStrData = "{\"code\":\"1\",\"data\":{\"name\":\"阿三\",\"age\":\"18\"},\"count\":\"1\"}";

        Observable.just(jsonStrData)
                .filter(strData -> JsonUtils.isSuccess(strData, "code", "1"))
                .map(s -> getInfo(JsonUtils.getJsonStr(s, "data"), User.class))
                .subscribe(u -> Logger.e(u.getName())
                        , throwable -> Logger.e(throwable.getMessage()));


        if (JsonUtils.isSuccess(jsonStrData, "code", "1")) {
            getInfo(JsonUtils.getJsonStr(jsonStrData, "data"), User.class);
        } else {
            showToast("err");
        }
        try {
            String pass = PasswordHash.createHash("a137708871");
            Logger.e(pass);
            String pass2 = PasswordHash.createHash("a137708871");
            boolean isOk = PasswordHash.validatePassword("a137708871", pass);
            boolean isOk2 = pass.equals(pass2);
            boolean isOk3 = PasswordHash.validatePassword("a137708871", pass2);
            Logger.e(pass + "\n" + pass2);
            Logger.e(isOk + "==" + isOk2+ "==" + isOk3);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }


    private <T> T getInfo(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    private Gson gson = new Gson();


    @Override
    public void refreshError(String error) {

    }


    class bean {
        private String name;
        private List<String> list;
    }


    class User {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
