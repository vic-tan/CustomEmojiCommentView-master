/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chaowen.commentlibrary.emoji;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;

import com.chaowen.commentlibrary.R;

import java.util.HashMap;
import java.util.LinkedHashMap;


public final class EmojiconHandler {
    public static final LinkedHashMap<String, Emojicon> sEmojisMapToUnicode = new LinkedHashMap<String, Emojicon>(100);
    public static final String PREFIX = "[";//前缀
    public static final String SUFFIX = "]";//后缀
    public static final String REPLACE_PREFIX = "\\[";//替换前缀
    public static final String REPLACE_SUFFIX = "\\]";//替换后缀
    private static final SparseIntArray sEmojisMap = new SparseIntArray(100);
    private static final HashMap<Integer, String> sEmojisMapToStr = new HashMap<Integer, String>(100);
    private static final String title[] = new String[]
            {
                    "微笑", "撇嘴", "色", "发呆", "得意", "流泪", "害羞", "闭嘴", "睡", "大哭",
                    "尴尬", "发怒", "调皮", "呲牙", "惊讶", "难过", "酷", "冷汗", "抓狂", "吐",
                    "偷笑", "愉快", "白眼", "傲慢", "饥饿", "困", "惊恐", "流汗", "憨笑", "悠闲",
                    "奋斗", "咒骂", "疑问", "嘘", "晕", "疯了", "衰", "骷髅", "敲打", "再见",
                    "擦汗", "抠鼻", "鼓掌", "糗大了", "坏笑", "左哼哼", "右哼哼", "哈欠", "鄙视", "委屈",
                    "快哭了", "阴险", "亲亲", "吓", "可怜", "菜刀", "西瓜", "啤酒", "篮球", "乒乓",
                    "咖啡", "饭", "猪头", "玫瑰", "凋谢", "嘴唇", "爱心", "心碎", "蛋糕", "闪电",
                    "炸弹", "刀", "足球", "瓢虫", "便便", "月亮", "太阳", "礼物", "拥抱", "强",
                    "弱", "握手", "胜利", "抱拳", "勾引", "拳头", "差劲", "爱你", "NO", "OK",
                    "爱情", "飞吻", "跳跳", "发抖", "怄火", "转圈", "磕头", "回头", "跳绳", "投降"
            };
    private static int uincode[] = new int[]

            {
                    0x1f604, 0x1f603, 0x1f600, 0x1f60a, 0x1f609, 0x1f60d, 0x1f618, 0x1f61a, 0x1f617, 0x1f619,
                    0x1f61c, 0x1f61d, 0x1f61b, 0x1f633, 0x1f601, 0x1f614, 0x1f60c, 0x1f612, 0x1f61e, 0x1f623,
                    0x1f622, 0x1f602, 0x1f62d, 0x1f62a, 0x1f625, 0x1f630, 0x1f605, 0x1f613, 0x1f629, 0x1f62b,
                    0x1f628, 0x1f631, 0x1f620, 0x1f621, 0x1f624, 0x1f616, 0x1f606, 0x1f60b, 0x1f637, 0x1f60e,
                    0x1f634, 0x1f635, 0x1f632, 0x1f61f, 0x1f626, 0x1f627, 0x1f608, 0x1f62e, 0x1f62c, 0x1f610,
                    0x1f615, 0x1f62f, 0x1f636, 0x1f607, 0x1f60f, 0x1f611, 0x1f485, 0x1f470, 0x1f64e, 0x1f64d,
                    0x1f647, 0x1f3a9, 0x1f451, 0x1f452, 0x1f45f, 0x1f45e, 0x1f461, 0x1f460, 0x1f462, 0x1f455,
                    0x1f454, 0x1f45a, 0x1f457, 0x1f3bd, 0x1f456, 0x1f458, 0x1f459, 0x1f4bc, 0x1f45c, 0x1f45d,
                    0x1f45b, 0x1f453, 0x1f380, 0x1f302, 0x1f484, 0x1f49b, 0x1f499, 0x1f49c, 0x1f49a, 0x1f494,
                    0x1f497, 0x1f493, 0x1f495, 0x1f496, 0x1f49e, 0x1f498, 0x1f48c, 0x1f48b, 0x1f48d, 0x1f48e,
            };

    private static int resId[] = new int[]
            {
                    R.drawable.expression_1, R.drawable.expression_2, R.drawable.expression_3, R.drawable.expression_4, R.drawable.expression_5,
                    R.drawable.expression_6, R.drawable.expression_7, R.drawable.expression_8, R.drawable.expression_9, R.drawable.expression_10,
                    R.drawable.expression_11, R.drawable.expression_12, R.drawable.expression_13, R.drawable.expression_14, R.drawable.expression_15,
                    R.drawable.expression_16, R.drawable.expression_17, R.drawable.expression_18, R.drawable.expression_19, R.drawable.expression_20,
                    R.drawable.expression_21, R.drawable.expression_22, R.drawable.expression_23, R.drawable.expression_24, R.drawable.expression_25,
                    R.drawable.expression_26, R.drawable.expression_27, R.drawable.expression_28, R.drawable.expression_29, R.drawable.expression_30,
                    R.drawable.expression_31, R.drawable.expression_32, R.drawable.expression_33, R.drawable.expression_34, R.drawable.expression_35,
                    R.drawable.expression_36, R.drawable.expression_37, R.drawable.expression_38, R.drawable.expression_39, R.drawable.expression_40,
                    R.drawable.expression_41, R.drawable.expression_42, R.drawable.expression_43, R.drawable.expression_44, R.drawable.expression_45,
                    R.drawable.expression_46, R.drawable.expression_47, R.drawable.expression_48, R.drawable.expression_49, R.drawable.expression_50,
                    R.drawable.expression_51, R.drawable.expression_52, R.drawable.expression_53, R.drawable.expression_54, R.drawable.expression_55,
                    R.drawable.expression_56, R.drawable.expression_57, R.drawable.expression_58, R.drawable.expression_59, R.drawable.expression_60,
                    R.drawable.expression_61, R.drawable.expression_62, R.drawable.expression_63, R.drawable.expression_64, R.drawable.expression_65,
                    R.drawable.expression_66, R.drawable.expression_67, R.drawable.expression_68, R.drawable.expression_69, R.drawable.expression_70,
                    R.drawable.expression_71, R.drawable.expression_72, R.drawable.expression_73, R.drawable.expression_74, R.drawable.expression_75,
                    R.drawable.expression_76, R.drawable.expression_77, R.drawable.expression_78, R.drawable.expression_79, R.drawable.expression_80,
                    R.drawable.expression_81, R.drawable.expression_82, R.drawable.expression_83, R.drawable.expression_84, R.drawable.expression_85,
                    R.drawable.expression_86, R.drawable.expression_87, R.drawable.expression_88, R.drawable.expression_89, R.drawable.expression_90,
                    R.drawable.expression_91, R.drawable.expression_92, R.drawable.expression_93, R.drawable.expression_94, R.drawable.expression_95,
                    R.drawable.expression_96, R.drawable.expression_97, R.drawable.expression_98, R.drawable.expression_99, R.drawable.expression_100,
            };

    static {
        for (int i = 0; i < 100; i++) {
            sEmojisMapToUnicode.put(title[i], Emojicon.fromCodePoint(uincode[i]));
            sEmojisMapToStr.put(uincode[i], title[i]);
            sEmojisMap.put(uincode[i], resId[i]);
        }

    }

    private EmojiconHandler() {
    }

    private static boolean isSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    private static int getEmojiResource(Context context, int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    private static String getEmojiResourceToStr(int codePoint) {
        return sEmojisMapToStr.get(codePoint);
    }

    private static Emojicon getEmojiResourceToUnicode(String value) {
        return sEmojisMapToUnicode.get(value);
    }


    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize) {
        addEmojis(context, text, emojiSize, 0, -1, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int index, int length) {
        addEmojis(context, text, emojiSize, index, length, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, boolean useSystemDefault) {
        addEmojis(context, text, emojiSize, 0, -1, useSystemDefault);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param text
     */
    public static String addEmojisToUincode(String text) {

        if (null == text || text.equals("")) {
            return text;
        }
        if (isExistStr(text)) {
            //优化替换方法，提高速率
            for (String key : sEmojisMapToUnicode.keySet()) {
                text = text.replaceAll(REPLACE_PREFIX + key + REPLACE_SUFFIX, sEmojisMapToUnicode.get(key).getEmoji());
                if (!isExistStr(text)) {
                    return text;
                }
            }
        }
        return text;
    }

    /**
     * 是否存在某字符串
     *
     * @param text
     * @return
     */
    public static boolean isExistStr(String text) {
        return text.contains(PREFIX) && text.contains(SUFFIX);
    }


    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     * @param useSystemDefault
     */

    public static void addEmojis(Context context, Spannable text, int emojiSize, int index, int length, boolean useSystemDefault) {
        if (useSystemDefault) {
            return;
        }

        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            text.removeSpan(oldSpans[i]);
        }

        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            int icon = 0;
            if (icon == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);

                if (unicode > 0xff) {
                    icon = getEmojiResource(context, unicode);
                }

                if (icon == 0 && i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    if (followUnicode == 0x20e3) {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    } else {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    }
                }
            }

            if (icon > 0) {
                text.setSpan(new EmojiconSpan(context, icon, emojiSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }


    public static String addEmojisToStr(Spannable text, int index, int length, boolean useSystemDefault) {
        if (useSystemDefault) {
            return "";
        }

        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            text.removeSpan(oldSpans[i]);
        }

        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            String icon = "";
            if ("".equals(icon)) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);

                if (unicode > 0xff) {
                    icon = getEmojiResourceToStr(unicode);
                }
                if ("".equals(icon) && i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    if (followUnicode == 0x20e3) {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    } else {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    }
                }
            }

            if (!"".equals(icon)) {
                return PREFIX + icon + SUFFIX;
            }
        }
        return "";
    }
}
