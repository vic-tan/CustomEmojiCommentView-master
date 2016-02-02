package com.chaowen.commentlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaowen.commentlibrary.emoji.EmojiViewPagerAdapter;
import com.chaowen.commentlibrary.emoji.Emojicon;
import com.chaowen.commentlibrary.emoji.EmojiconEditText;
import com.chaowen.commentlibrary.emoji.EmojiconHandler;
import com.chaowen.commentlibrary.emoji.People;
import com.chaowen.commentlibrary.emoji.SoftKeyboardStateHelper;
import com.chaowen.commentlibrary.util.SystemUtil;
import com.chaowen.commentlibrary.viewpage.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaowen
 */
public class CommentView extends LinearLayout implements View.OnClickListener, SoftKeyboardStateHelper.SoftKeyboardStateListener, EmojiViewPagerAdapter.OnClickEmojiListener {

    private static String tmp = "";
    // 是否重置了EditText的内容
    private static boolean resetText;
    private ImageView mIvEmoji,mIvDel;
    private Animation mShowAnim, mDismissAnim, mShowMoreAnim, mDismissMoreAnim;
    private Button mBtnSend;
    private EmojiconEditText mEtText;
    private OnComposeOperationDelegate mDelegate;
    private SoftKeyboardStateHelper mKeyboardHelper;
    private ViewPager mViewPager;
    private EmojiViewPagerAdapter mPagerAdapter;
    private int mCurrentKeyboardHeigh;
    private View mLyEmoji;
    private boolean mIsKeyboardVisible;
    private boolean mNeedShowEmojiOnKeyboardClosed, mNeedShowOptOnKeyboardClosed;
    private boolean showVoice = true;
    private boolean showMore = true;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!resetText)
                tmp = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!resetText) {
                if ((count == 2) && (!containsEmoji(s.toString().substring(start, start + 2)))) {
                    resetText = true;
                    mEtText.setText(tmp);
                    mEtText.invalidate();
                    if (mEtText.getText().length() > 1)
                        Selection.setSelection(mEtText.getText(), mEtText.getText().length());
                    Toast.makeText(getContext(), "不支持输入表情!", Toast.LENGTH_SHORT).show();
                }
            } else {
                resetText = false;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public CommentView(Context context) {
        this(context, null);
    }


    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return FALSE，包含图片
     */
    public static boolean containsEmoji(String source) {
        if (source.equals("")) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static void input(EditText editText, Emojicon emojicon) {
        if (editText == null || emojicon == null) {
            return;
        }
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start < 0) {
            editText.append(emojicon.getEmoji());
        } else {
            /*editText.getText().replace(Math.min(start, end), Math.max(start, end),
                    emojicon.getEmoji(), 0, emojicon.getEmoji().length());*/
            SpannableStringBuilder builder = new SpannableStringBuilder(emojicon.getEmoji());
            editText.setText(editText.getText() + EmojiconHandler.addEmojisToStr(builder, 0, emojicon.getEmoji().length(), false));
        }
        Selection.setSelection(editText.getText(), editText.getText().length());
    }

    public static void backspace(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }

    private void showSendButton() {
        mShowAnim.reset();
        mBtnSend.clearAnimation();
        mBtnSend.startAnimation(mShowAnim);
    }

    private void dismissSendButton() {
        mBtnSend.clearAnimation();
        mBtnSend.startAnimation(mDismissAnim);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ComposeView);
        showVoice = a.getBoolean(R.styleable.ComposeView_showVoice, true);
        showMore = a.getBoolean(R.styleable.ComposeView_showMore, true);
        inflate(context, R.layout.view_compose, this);


        mShowAnim = AnimationUtils.loadAnimation(context, R.anim.chat_show_send_button);
        mDismissAnim = AnimationUtils.loadAnimation(context, R.anim.chat_dismiss_send_button);
        mIvEmoji = (ImageView) findViewById(R.id.iv_emoji);
        mIvDel = (ImageView) findViewById(R.id.del);
        mIvEmoji.setOnClickListener(this);
        mIvDel.setOnClickListener(this);
        mEtText = (EmojiconEditText) findViewById(R.id.et_text);
        mEtText.addTextChangedListener(mTextWatcher);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);


        mLyEmoji = findViewById(R.id.ly_emoji);


        mKeyboardHelper = new SoftKeyboardStateHelper(((Activity) getContext()).getWindow()
                .getDecorView());
        mKeyboardHelper.addSoftKeyboardStateListener(this);


        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        int emojiHeight = caculateEmojiPanelHeight();

        Emojicon[] emojis = People.DATA;
        List<List<Emojicon>> pagers = new ArrayList<List<Emojicon>>();
        List<Emojicon> es = null;
        int size = 0;
        boolean justAdd = false;
        for (Emojicon ej : emojis) {
            if (size == 0) {
                es = new ArrayList<>();
            }
            //if (size == 27) {
            //   es.add(new Emojicon(""));
            //} else {
                es.add(ej);
           // }
            size++;
            if (size == 28) {
                pagers.add(es);
                size = 0;
                justAdd = true;
            } else {
                justAdd = false;
            }
        }
        if (!justAdd && es != null) {
            int exSize = 28 - es.size();
            for (int i = 0; i < exSize; i++) {
                es.add(new Emojicon(""));
            }
            pagers.add(es);
        }

        mPagerAdapter = new EmojiViewPagerAdapter(getContext(), pagers,
                emojiHeight, this);
        mViewPager.setAdapter(mPagerAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.btn_send) {
            if (mDelegate != null) {
                mDelegate.onSendText(mEtText.getText().toString());
            }
        } else if (id == R.id.iv_emoji) {
            if (mLyEmoji.getVisibility() == View.GONE) {
                mNeedShowEmojiOnKeyboardClosed = true;
                tryShowEmojiPanel();
            } else {
                tryHideEmojiPanel();
            }
        }else if(id == R.id.del){
            this.onDelete();
        }
    }


    private void changeToInput() {
        mEtText.setVisibility(View.VISIBLE);
        mIvEmoji.setVisibility(View.VISIBLE);
    }

    private int caculateEmojiPanelHeight() {
        mCurrentKeyboardHeigh = BaseContext.getInstance().getSoftKeyboardHeight();
        if (mCurrentKeyboardHeigh == 0) {
            mCurrentKeyboardHeigh = (int) SystemUtil.dpToPixel(180);
        }


        int emojiPanelHeight = (int) (mCurrentKeyboardHeigh - SystemUtil
                .dpToPixel(20));
        int emojiHeight = (int) (emojiPanelHeight / 4);

        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, emojiPanelHeight);
        mViewPager.setLayoutParams(lp);
        if (mPagerAdapter != null) {
            mPagerAdapter.setEmojiHeight(emojiHeight);
        }
        return emojiHeight;
    }

    private void tryShowEmojiPanel() {
        if (mIsKeyboardVisible) {
            hideKeyboard();
        } else {
            showEmojiPanel();
        }
    }

    private void tryHideEmojiPanel() {
        if (!mIsKeyboardVisible) {
            showKeyboard();
        } else {
            hideEmojiPanel();
        }
    }

    private void showEmojiPanel() {
        mNeedShowEmojiOnKeyboardClosed = false;
        mLyEmoji.setVisibility(View.VISIBLE);
        //mDelegate.onEmojiPanelVisiable(true, mLyEmoji.getHeight());
        mIvEmoji.setImageResource(R.drawable.btn_emoji_pressed);
    }

    private void hideEmojiPanel() {
        if (mLyEmoji.getVisibility() == View.VISIBLE) {
            mLyEmoji.setVisibility(View.GONE);
            //mDelegate.onEmojiPanelVisiable(true,0);
            mIvEmoji.setImageResource(R.drawable.btn_emoji_selector);
        }
    }

    public void hideEmojiOptAndKeyboard() {
        hideEmojiPanel();
        SystemUtil.hideSoftKeyboard(mEtText);
    }

    private void tryHideOptPanel() {
        if (!mIsKeyboardVisible) {
            showKeyboard();
        }
    }

    private void tryShowOptPanel() {
        if (mIsKeyboardVisible) {
            hideKeyboard();
        } else {
            showOptPanel();
        }
    }

    private void showOptPanel() {
        mNeedShowOptOnKeyboardClosed = false;
    }



    private void hideKeyboard() {
        SystemUtil.hideSoftKeyboard(mEtText);
    }

    public void showKeyboard() {
        mEtText.requestFocus();
        SystemUtil.showSoftKeyboard(mEtText);
    }

    public EmojiconEditText getmEtText() {
        return mEtText;
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        int realKeyboardHeight = keyboardHeightInPx
                - SystemUtil.getStatusBarHeight();

        if (mCurrentKeyboardHeigh != realKeyboardHeight) {
            caculateEmojiPanelHeight();
        }

        mIsKeyboardVisible = true;
        hideEmojiPanel();
        if (mDelegate != null) {
            //mDelegate.onSoftKeyboardOpened();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        mIsKeyboardVisible = false;
        if (mNeedShowEmojiOnKeyboardClosed) {
            showEmojiPanel();
        }
        if (mNeedShowOptOnKeyboardClosed) {
            showOptPanel();
        }
    }

    @Override
    public void onEmojiClick(Emojicon emoji) {
        input(mEtText, emoji);
    }

    @Override
    public void onDelete() {
        backspace(mEtText);
    }


    public void setOperationDelegate(OnComposeOperationDelegate delegate) {
        mDelegate = delegate;
    }

    public void clearText() {
        if (mEtText != null) {
            mEtText.setText("");
            mEtText.setHint("");
        }
    }

    public void setTextHint(String text) {
        if (mEtText != null) {
            mEtText.setHint(text);
        }
    }

    public interface OnComposeOperationDelegate {
        void onSendText(String emojionText);
    }


}
