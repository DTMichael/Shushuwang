package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.administrator.shushu1.First.flag1;

public class Women extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);

        Button D =(Button) findViewById(R.id.button5);
        D.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Women.this, Third.class);
                flag1=0;
                startActivity(intent);
            }
        });

    }
}
