package com.lixd.emojidemo.emoji;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.jianshu.com/p/a56130635589
 */
public class SystemMethodEmojiFilter implements InputFilter {
    private static final String TAG = SystemMethodEmojiFilter.class.getSimpleName();

    private static Set<Integer> OTHER_EMOJI_CODE;

    /**
     * type == Character.SURROGATE || type == Character.OTHER_SYMBOL
     * 这两个类型能过滤大概95%的emoji表情
     * 剩下的emoji表情需要手动添加表情码过滤，这是因为emoji表情码不定时会更新版本
     */
    static {
        OTHER_EMOJI_CODE = new HashSet<>();
        // 8252   双感叹号表情
        OTHER_EMOJI_CODE.add(8252);
        // 8265   感叹号+问号表情
        OTHER_EMOJI_CODE.add(8265);
        // 12336  类似于波浪表情
        OTHER_EMOJI_CODE.add(12336);
        // 12349  类似于皮卡丘尾巴表情
        OTHER_EMOJI_CODE.add(12349);
    }


    private Context context;

    public SystemMethodEmojiFilter(Context context) {
        this.context = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        boolean hasEmoji = false;
        for (int i = start; i < end; i++) {
            int type = Character.getType(source.charAt(i));
            LogUtils.getInstance().log(TAG, "type=" + type);
            if (type == Character.SURROGATE) {
                hasEmoji = true;
            }

            if (type == Character.OTHER_SYMBOL) {
                hasEmoji = true;
            }

            if (isCustomEmoji(source.charAt(i))) {
                hasEmoji = true;
            }
        }
        if (hasEmoji) {
            LogUtils.getInstance().log(TAG, "检测到表情输入");
            Toast.makeText(context, "检测到表情输入", Toast.LENGTH_SHORT).show();
            return "";
        } else {
            return null;
        }
    }

    /**
     * 自定义emoji表情码范围，防止有的表情码检测不到
     *
     * @param ch
     */
    private boolean isCustomEmoji(int ch) {
        LogUtils.getInstance().log(TAG, "emojiCode=" + ch);
        // 8252   双感叹号表情
        // 12349  类似于皮卡丘尾巴表情
        if (OTHER_EMOJI_CODE.contains(ch)) {
            return true;
        }
        return false;
    }

}
