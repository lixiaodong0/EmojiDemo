package com.lixd.emojidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lixd.emojidemo.emoji.LogUtils;
import com.lixd.emojidemo.emoji.SystemMethodEmojiFilter;

public class MainActivity extends AppCompatActivity {

    private TextView tvLog;
    private Button btnClear;
    private EditText etEmoji;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLog = findViewById(R.id.tv_log);
        LogUtils.getInstance().bindLogView(tvLog);
        etEmoji = findViewById(R.id.et_emoji);
        btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        etEmoji = findViewById(R.id.et_emoji);
        etEmoji.setFilters(new InputFilter[]{new SystemMethodEmojiFilter(this)});
    }

    private void clear() {
        etEmoji.setText("");
        tvLog.setText("");
    }

    private InputFilter emojiPointCountFilter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String s = source.toString();
                if (end <= start) {
                    return null;
                }
                int codePointCount = s.codePointCount(0, s.length());
                int firstPointIndex = s.offsetByCodePoints(0, 0);
                int lastPointIndex = s.offsetByCodePoints(0, codePointCount - 1);
                int index = firstPointIndex;
                Log.e("emoji", "codePointCount=" + codePointCount);
                while (index <= lastPointIndex) {
                    int codePoint = s.codePointAt(index);
                    Log.e("emoji", "codePoint=" + codePoint);
                    index += Character.isSupplementaryCodePoint(codePoint) ? 2 : 1;
                    Log.e("emoji", "index=" + index);
                }
                return null;
            }

        };
        return filter;
    }


    /**
     * 系统过滤emoji表情方法
     *
     * @return
     */
    private InputFilter emojiFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type = Character.getType(source.charAt(i));
                    Log.e("emoji", "type=" + type);
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                        Toast.makeText(MainActivity.this, "禁止表情输入", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };
    }
}
