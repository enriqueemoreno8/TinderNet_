package com.example.tindernet;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Switch switchButton = findViewById(R.id.switch2);
        TextView switchText = findViewById(R.id.textView3);

        switchButton.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
        switchButton.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_grey)));

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchButton.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    switchText.setText("Empresa");
                } else {
                    switchButton.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
                    switchText.setText("Cliente");
                }
            }
        });

    }
}
