package com.rx7ru.onesmartkeyboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CustomKeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomKeyboardView keyboardView = new CustomKeyboardView(this);
        setContentView(keyboardView);
    }
}
