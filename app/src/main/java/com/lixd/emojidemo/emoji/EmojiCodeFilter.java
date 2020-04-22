package com.lixd.emojidemo.emoji;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class EmojiCodeFilter implements InputFilter {
    private static final String TAG = SystemMethodEmojiFilter.class.getSimpleName();

    private Context context;

    public EmojiCodeFilter(Context context) {
        this.context = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String src = source.toString();
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        boolean hasEmoji = false;
        /**
         * 1.得到代码点数量，也即是实际字符数，注意和length()的区别
         * 举例：
         * 一个emoji表情是一个字符，codePointCount()是1，length()是2。
         * 但是遇到过一个emoji表情居然带着空格符号，结果它的codePointCount()是2，length()也是2，实际上单独拎emoji来说，它的length()应该说是1才对了。
         * 推测这就是它们是否属于增补字符范围内，length=2的是增补字符，length=1的不是。
         */
        int codePointCount = src.codePointCount(0, src.length());
        /**
         * 2.得到字符串的第一个代码点index，和最后一个代码点index
         * 举例：比如3个emoji表情，那么它的cpCount=3；firCodeIndex=0；lstCodeIndex=4
         * 因为每个emoji表情length()是2，所以第一个是0-1，第二个是2-3，第三个是4-5
         */
        int firstPointIndex = src.offsetByCodePoints(0, 0);
        int lastPointIndex = src.offsetByCodePoints(0, codePointCount - 1);
        int index = firstPointIndex;

        while (index <= lastPointIndex) {
            //3.获得代码点，判断是否是emoji表情
            //注意，codePointAt(int) 这个int对应的是codeIndex
            //举例:3个emoji表情，取第3个emoji表情，index应该是4
            int codePoint = src.codePointAt(index);
            if (isEmojiCharacter(codePoint)) {
                hasEmoji = true;
                break;
            }
            //4.确定指定字符（Unicode代码点）是否在增补字符范围内。
            //因为除了表情，还有些特殊字符也是在增补字符方位内的。
            index += Character.isSupplementaryCodePoint(codePoint) ? 2 : 1;
        }

        if (hasEmoji) {
            Log.e(TAG, "检测到表情输入");
            Toast.makeText(context, "检测到表情输入", Toast.LENGTH_SHORT).show();
            return "";
        } else {
            return null;
        }
    }

    private boolean isEmojiCharacter(int ch) {
        Log.e(TAG, "ch=" + ch);
        if (emojiEmoticons(ch)) {
            Log.e(TAG, "emojiEmoticons");
            return true;
        }
        if (emojiDingbats(ch)) {
            Log.e(TAG, "emojiDingbats");
            return true;
        }
        if (emojiTransportAndMapSymbols(ch)) {
            Log.e(TAG, "emojiTransportAndMapSymbols");
            return true;
        }
        if (emojiEnclosedCharacters(ch)) {
            Log.e(TAG, "emojiEnclosedCharacters");
            return true;
        }
        if (emoji6A(ch)) {
            Log.e(TAG, "emoji6A");
            return true;
        }
        if (emoji6B(ch)) {
            Log.e(TAG, "emoji6B");
            return true;
        }
        if (emoji6C(ch)) {
            Log.e(TAG, "emoji6C");
            return true;
        }
        if (emojiUncategorized(ch)) {
            Log.e(TAG, "emojiUncategorized");
            return true;
        }
        return false;
    }


    /**
     * 1.Emoticons ( 1F601 - 1F64F )
     */
    private boolean emojiEmoticons(int ch) {
        return ch >= 0x1F601 && ch <= 0x1F64F;
    }

    /**
     * 2.Dingbats ( 2700 - 27BF )
     */
    private boolean emojiDingbats(int ch) {
        return ch >= 0x2700 && ch <= 0x27BF;
    }

    /**
     * 3. Transport and map symbols ( 1F680 - 1F6C0 )
     */
    private boolean emojiTransportAndMapSymbols(int ch) {
        return ch >= 0x1F680 && ch <= 0x1F6C0;
    }

    /**
     * 4.Enclosed characters ( 24C2 - 1F251 )
     */
    private boolean emojiEnclosedCharacters(int ch) {
        return ch >= 0x24C2 && ch <= 0x1F251;
    }

    /**
     * 5. Uncategorized
     */
    private boolean emojiUncategorized(int ch) {
        return false;
    }

    /**
     * 6a. Additional emoticons ( 1F600 - 1F636 )
     */
    private boolean emoji6A(int ch) {
        return ch >= 0x1F600 && ch <= 0x1F636;
    }

    /**
     * 6b. Additional transport and map symbols ( 1F681 - 1F6C5 )
     */
    private boolean emoji6B(int ch) {
        return ch >= 0x1F681 && ch <= 0x1F6C5;
    }

    /**
     * 6c. Other additional symbols ( 1F30D - 1F567 )
     */
    private boolean emoji6C(int ch) {
        return ch >= 0x1F30D && ch <= 0x1F567;
    }
}
