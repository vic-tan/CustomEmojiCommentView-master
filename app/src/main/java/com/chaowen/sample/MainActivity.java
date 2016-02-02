package com.chaowen.sample;

import android.app.Activity;
import android.os.Bundle;

import com.chaowen.commentlibrary.CommentView;
import com.chaowen.commentlibrary.emoji.EmojiconTextView;

public class MainActivity extends Activity implements CommentView.OnComposeOperationDelegate {
    EmojiconTextView textView;

    @Override
    public void onSendText(String emojionText) {
        textView.setCustomText(emojionText);
        commentView.clearText();
    }

    CommentView commentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (EmojiconTextView)findViewById(R.id.txt);
        commentView = (CommentView)findViewById(R.id.compose);
        commentView.setOperationDelegate(this);
        
    }



}
