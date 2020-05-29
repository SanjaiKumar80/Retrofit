package com.app.assesment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.assesment.adapter.CustomUserAdapter;
import com.app.assesment.api.RetrofitClient;
import com.app.assesment.model.ChildItems;
import com.app.assesment.model.GroupHeader;
import com.app.assesment.model.SliderResponse;
import com.app.assesment.utils.Constant;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feed extends AppCompatActivity {
    private final static String TAG = "Feed";
    ArrayList<SliderResponse> slres = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();
    private SliderLayout mDemoSlider;
    /* RecyclerView recyclerView;
     ListAdapter adapter;
     List<Event> t;*/
    Button ProfBtn;
    ArrayList<ChildItems> ch_list;
    OkHttpClient Client;
    CustomUserAdapter adapters;
    List<GroupHeader> listDataHeader;
    ExpandableListView expandable_list;
    Constant cont = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        //   t = new ArrayList<>();
        SliderInital();
        SliderValue();
        //TimeValue();
        OnClick();
     /*   adapter = new ListAdapter(t, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
        Client = new OkHttpClient();

        listDataHeader = new ArrayList<>();
        expandable_list = findViewById(R.id.expandable_list);
       // InitUsers();
        RetrofitInitUsers();
        adapters = new CustomUserAdapter(this, listDataHeader);
        Log.d(TAG, "customadapter: " + adapters);
        expandable_list.setAdapter(adapters);

    }

    public void OnClick() {
        ProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Feed.this, ProfileActivity.class);
                startActivity(i);
            }
        });

    }

    public void SliderInital() {

        mDemoSlider = findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        //  recyclerView = findViewById(R.id.idrecycler_view);
        ProfBtn = findViewById(R.id.profilebutton);
    }

    public void SliderValue() {

        Call<List<SliderResponse>> call = RetrofitClient
                .getInstance().getApi().Slider();
        call.enqueue(new Callback<List<SliderResponse>>() {
            @Override
            public void onResponse(Call<List<SliderResponse>> call, Response<List<SliderResponse>> response) {
                Log.i("TAG", "Succes: " + response);
                slres = new ArrayList<>(response.body());
                int k = slres.size();
                for (int index = 0; index < k; index++) {
                    String s = slres.get(index).getImageUrl();
                    urls.add(s);
                }
                Print();
            }

            @Override
            public void onFailure(Call<List<SliderResponse>> call, Throwable t) {
                cont.showToastMessage(Feed.this, t.getMessage());
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });


    }

    public void Print() {

        int k = urls.size();
        for (int index = 0; index < k; index++) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(urls.get(index))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    /*    public void TimeValue() {
            Call<Timing> call = RetrofitClient
                    .getInstance().getApi().timings();

            call.enqueue(new Callback<Timing>() {
                @Override
                public void onResponse(Call<Timing> call, Response<Timing> response) {

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {


                            Log.d(TAG, "onResponse: response.body() " + response.body());
                            Timing timingObj = response.body();

                            if (timingObj != null)
                                t.addAll(timingObj.getEvents());
                   *//* int o = t.getEvents().size();
                for (int i = 0; i < o; i++) {
                    String l = t.getEvents().get(i).getDay();

                    int w = t.getEvents().get(i).getPrograms().size();
                    for (int u = 0; u < w; u++) {
                        String j = t.getEvents().get(i).getPrograms().get(u).getEvent();
                        String m = t.getEvents().get(i).getPrograms().get(u).getTime();
                        Log.i(TAG, "Day " + l + "Event" + j + "Time " + m);
                    }
                }*//*

                        Log.d(TAG, "onResponse: t " + t);
                        adapter.setData(t);
                    }
                });
            }

            @Override
            public void onFailure(Call<Timing> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(Feed.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/


    public void RetrofitInitUsers() {
        Call<JsonObject> call = RetrofitClient
                .getInstance().getApi().timings();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                final String java = response.body().toString();
                Log.d(TAG, "Response: " + java);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONObject object = new JSONObject(java);

                            JSONArray array = object.getJSONArray("events");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);

                                GroupHeader header = new GroupHeader();
                                Log.d(TAG, "header1: " + header);
                                header.setTitle(object1.getString("day"));

                                JSONArray category = object1.getJSONArray("programs");

                                ch_list = new ArrayList<>();
                                Log.d(TAG, "child: " + ch_list);

                                for (int j = 0; j < category.length(); j++) {

                                    JSONObject channelsObject = category.getJSONObject(j);
                                    ChildItems childItems = new ChildItems();
                                    Log.d(TAG, "run: " + childItems);
                                    childItems.setTiming(channelsObject.getString("time"));
                                    childItems.setEvent(channelsObject.getString("event"));
                                    ch_list.add(childItems);
                                }
                                header.setItems(ch_list);
                                listDataHeader.add(header);
                            }

                            adapters.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                cont.showToastMessage(Feed.this, t.getMessage());
            }
        });


    }

/*    private void InitUsers() {

        Request builder = new Request.Builder()
                .url(cont.Timing)
                .build();

        Client.newCall(builder).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cont.showToastMessage(Feed.this, "Check Your Internet Connection.");
                        // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
                    }


                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                final String java = response.body().string();
                Log.d(TAG, "Response: " + java);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONObject object = new JSONObject(java);

                            JSONArray array = object.getJSONArray("events");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);

                                GroupHeader header = new GroupHeader();
                                Log.d(TAG, "header1: " + header);
                                header.setTitle(object1.getString("day"));

                                JSONArray category = object1.getJSONArray("programs");

                                ch_list = new ArrayList<>();
                                Log.d(TAG, "child: " + ch_list);

                                for (int j = 0; j < category.length(); j++) {

                                    JSONObject channelsObject = category.getJSONObject(j);
                                    ChildItems childItems = new ChildItems();
                                    Log.d(TAG, "run: " + childItems);
                                    childItems.setTiming(channelsObject.getString("time"));
                                    childItems.setEvent(channelsObject.getString("event"));
                                    ch_list.add(childItems);
                                }
                                header.setItems(ch_list);
                                listDataHeader.add(header);
                            }

                            adapters.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }*/
}





