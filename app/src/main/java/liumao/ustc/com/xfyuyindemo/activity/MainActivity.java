package liumao.ustc.com.xfyuyindemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.List;

import liumao.ustc.com.xfyuyindemo.CommonValue;
import liumao.ustc.com.xfyuyindemo.R;
import liumao.ustc.com.xfyuyindemo.model.DictationResult;
import liumao.ustc.com.xfyuyindemo.utils.TTSUtils;

public class MainActivity extends AppCompatActivity {

    private Button speck_btn;
    private EditText speck_content_edt;
    private RecognizerDialog iatDialog;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 语音配置对象初始化，APPID是自己在平台上生成的ID
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "="+ CommonValue.XFYY_APPID);
        speck_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                process_speck();
            }
        });
    }

    private void process_speck() {
        // 初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(MainActivity.this, mInitListener);
        //设置监听，实现听写结果的回调
        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                Log.d(TAG,"onResult...");
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    speck_content_edt.setText(result);
                    //获取焦点
                    speck_content_edt.requestFocus();
                    //将光标定位到文字最后，以便修改
                    speck_content_edt.setSelection(result.length());
                    TTSUtils.getInstance().speak(result+"!");
                }
            }
            @Override
            public void onError(SpeechError speechError) {
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下
        iatDialog.show();
    }

    private void initView() {
        speck_btn=findViewById(R.id.speak_btn);
        speck_content_edt=findViewById(R.id.speck_content_edt);
    }
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(MainActivity.this, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
