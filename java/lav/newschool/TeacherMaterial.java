package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ashwin on 5/16/2018.
 */

public class TeacherMaterial extends AppCompatActivity {
    TextView one_tv,two_tv,three_tv,four_tv,five_tv,six_tv,seven_tv,eight_tv,nine_tv,ten_tv,eleven_tv,twelve_tv;
    String id, classname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachermaterial);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        one_tv=(TextView)findViewById(R.id.tv_one);
        one_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "1";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        two_tv=(TextView)findViewById(R.id.tv_two);
        two_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "2";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        three_tv=(TextView)findViewById(R.id.tv_three);
        three_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "3";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        four_tv=(TextView)findViewById(R.id.tv_four);
        four_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "4";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        five_tv=(TextView)findViewById(R.id.tv_five);
        five_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "5";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        six_tv=(TextView)findViewById(R.id.tv_six);
        six_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "6";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        seven_tv=(TextView)findViewById(R.id.tv_seven);
        seven_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "7";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        eight_tv=(TextView)findViewById(R.id.tv_eight);
        eight_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "8";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        nine_tv=(TextView)findViewById(R.id.tv_nine);
        nine_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "9";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        ten_tv=(TextView)findViewById(R.id.tv_ten);
        ten_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "10";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        eleven_tv=(TextView)findViewById(R.id.tv_eleven);
        eleven_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "11";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
        twelve_tv=(TextView)findViewById(R.id.tv_twelve);
        twelve_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classname = "12";
                Intent intent = new Intent(TeacherMaterial.this,TeacherMaterialSubject.class);
                intent.putExtra("id",id);
                intent.putExtra("classname",classname);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int vid = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (vid == R.id.action_view) {
            Intent intent=new Intent(TeacherMaterial.this,TeacherViewMaterial.class);
            intent.putExtra("id",id);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
