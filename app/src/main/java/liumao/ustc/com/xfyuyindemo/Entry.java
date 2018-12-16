package liumao.ustc.com.xfyuyindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import liumao.ustc.com.xfyuyindemo.activity.MainActivity;
import liumao.ustc.com.xfyuyindemo.activity.SpeechSynthesizerActivity;

public class Entry extends AppCompatActivity implements OnClickListener{

    private Button YYHC_btn;
    private Button YYSB_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        initView();
    }

    private void initView() {
        YYHC_btn=findViewById(R.id.YYHC_btn);
        YYSB_btn=findViewById(R.id.YYSB_btn);
        YYSB_btn.setOnClickListener(this);
        YYHC_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.YYHC_btn:
                Intent intent1 = new Intent(Entry.this, SpeechSynthesizerActivity.class);
                startActivity(intent1);
                break;
            case R.id.YYSB_btn:
                Intent intent2 = new Intent(Entry.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
