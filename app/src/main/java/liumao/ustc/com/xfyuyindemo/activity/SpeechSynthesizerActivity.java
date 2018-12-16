package liumao.ustc.com.xfyuyindemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import liumao.ustc.com.xfyuyindemo.R;
import liumao.ustc.com.xfyuyindemo.utils.TTSUtils;

public class SpeechSynthesizerActivity extends AppCompatActivity {

    private final String TAG="SpeechSynthesizerActivity";
    private Button word2voice_btn;
    private EditText words_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_synthesizer);
        initView();
        word2voice_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                process_task();
            }
        });
    }

    private void process_task() {
        String words_str=words_edt.getText().toString();
        if(words_str!=null && !words_str.isEmpty()){
            TTSUtils.getInstance().speak(words_str);
        }
    }

    private void initView() {
        word2voice_btn=findViewById(R.id.word2voice_btn);
        words_edt=findViewById(R.id.words_edt);
    }
}
