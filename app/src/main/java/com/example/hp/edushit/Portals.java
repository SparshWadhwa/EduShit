package com.example.hp.edushit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Portals extends AppCompatActivity {

    private Button student_portal;
    private Button teacher_portal;
    private Button parent_portal;

    static boolean isStudent=false,isTeacher=false,isParent=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portals);

        student_portal = (Button) findViewById(R.id.student_portal);
        teacher_portal = (Button) findViewById(R.id.teacher_portal);
        parent_portal = (Button) findViewById(R.id.parent_portal);

        student_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isStudent=true;
                Intent i = new Intent(Portals.this, SchoolName.class);
                startActivity(i);



            }
        });

    }
}
