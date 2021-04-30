package wtbu.xiaomai.musicgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import wtbu.xiaomai.musicgo.adapter.MusicAdapter;
import wtbu.xiaomai.musicgo.bean.MusicBean;
import wtbu.xiaomai.musicgo.getNetRequest.getRequest;

public class MusicInformationActivity extends AppCompatActivity {
    private getRequest getRequest = new getRequest();
    private MHandle mHandle = new MHandle();
    private List<MusicBean> list = new ArrayList<>();
    private RecyclerView rv_music;
    private MusicAdapter musicAdapter = new MusicAdapter(list);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_information);
        getRequest.sendGetNetRequest("http://sandyz.ink:3000/search/search?keywords= hello",mHandle);
        init();


    }

    private void init(){
        rv_music = findViewById(R.id.rv_music);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_music.setLayoutManager(layoutManager);
        rv_music.setAdapter(musicAdapter);

        /*musicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String songId = list.get(position).getSongId();
                String uriRequest = "http://sandyz.ink:3000/song/url?id=" + songId;

                Intent intent = new Intent(MusicInformationActivity.this,PlayMusicActivity.class);
                intent.putExtra("uriRequest",uriRequest);
                startActivity(intent);
            }
        });*/
    }

    public class MHandle extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String responseData = msg.obj.toString();
            String singerName = null;
            try{
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject jsonObjectresult = jsonObject.getJSONObject("result");
                JSONArray jsonArray = jsonObjectresult.getJSONArray("songs");
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObjecti = jsonArray.getJSONObject(i);
                    String songName = jsonObjecti.getString("name");
                    //String 和 int类型不一样，不知道得行不得行啊。
                    String songId = jsonObjecti.getString("id");
                    JSONArray artists = jsonObjecti.getJSONArray("artists");
                    for(int j = 0;j<artists.length();j++){
                        JSONObject jsonObjectj = artists.getJSONObject(j);
                        String name = jsonObjectj.getString("name");
                        singerName = name;
                    }
                    JSONObject jsonObjectalbum = jsonObjecti.getJSONObject("album");
                    String albumName = jsonObjectalbum.getString("name");
                    list.add(new MusicBean(songName,singerName,albumName,songId));

                }
                Log.e("singername",list.toString());
                musicAdapter.notifyDataSetChanged();

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}