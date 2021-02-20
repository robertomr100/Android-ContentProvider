package edu.fsu.cs.mobile.hw5;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EmployeeViewActivity extends AppCompatActivity {
    private ViewAdminEmployeeFragment AdminFragment;
    private ViewEmployeeFragment EmployeeFragment;
    private FrameLayout Fadmin;
    private FrameLayout Femployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);

        AdminFragment = new ViewAdminEmployeeFragment();
        EmployeeFragment= new ViewEmployeeFragment();


        Fadmin=(FrameLayout) findViewById(R.id.Fadmin);
        Femployee=(FrameLayout) findViewById(R.id.Femployee);


        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction trans =manager.beginTransaction();
        trans.add(R.id.Fadmin,AdminFragment).commit();

        FragmentManager manager1= getSupportFragmentManager();
        FragmentTransaction trans1 =manager1.beginTransaction();
        trans1.add(R.id.Femployee,EmployeeFragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("ID", getIntent().getExtras().getString("ID"));
        bundle.putString("name", getIntent().getExtras().getString("name"));
        bundle.putString("email", getIntent().getExtras().getString("email"));
        bundle.putString("department", getIntent().getExtras().getString("department"));
        bundle.putString("gender", getIntent().getExtras().getString("gender"));

        EmployeeFragment.setArguments(bundle);



        int value = getIntent().getExtras().getInt("ADMIN");
        if(value==0)
        {
            Fadmin.setVisibility(View.INVISIBLE);
        }
        else
        {
            Femployee.setVisibility(View.INVISIBLE);
        }


    }
}
