package wtbu.xiaomai.musicgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlayMusicActivity extends AppCompatActivity {
    private TextView show_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();
    }

    private void init(){
        Intent inf = getIntent();
        show_result = findViewById(R.id.show_result);

        show_result.setText(inf.getStringExtra("uriRequest"));
    }
}