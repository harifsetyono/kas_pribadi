package com.harif.user.kaspribadiku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    private Button btnBack, btnHome;
    CardView btnsettingpemasukan, btnsettingpengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnsettingpemasukan = (CardView) findViewById(R.id.btnsettingpemasukan);
        btnsettingpengeluaran = (CardView) findViewById(R.id.btnsettingpengeluaran);


        home();
        kembali();
        setBtnsettingpemasukan();
        setBtnsettingpengeluaran();
    }

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setBtnsettingpemasukan(){
        btnsettingpemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingPemasukan.class);
                startActivity(intent);
            }
        });


    }

    private void setBtnsettingpengeluaran(){
        btnsettingpengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingPengeluaran.class);
                startActivity(intent);
            }
        });

    }
}
