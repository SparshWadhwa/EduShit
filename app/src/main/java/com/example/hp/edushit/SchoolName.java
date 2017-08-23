package com.example.hp.edushit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SchoolName extends AppCompatActivity {


    private Button schoolSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_name);

        schoolSubmitBtn = (Button) findViewById(R.id.schoolSubmitBtn);

        schoolSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i2 = new Intent(SchoolName.this, StudentInfo.class);
                startActivity(i2);

            }
        });

    }
}
