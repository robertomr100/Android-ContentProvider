package edu.fsu.cs.mobile.hw5;



import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickListener {

    private MainFragment mMainFragment;
    private LoginFragment mLoginFragment;
    private  RegisterFragment mRegisterFragment;
    private FrameLayout Fmain;
    private FrameLayout Flogin;
    private FrameLayout Fregister;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyEmployeeProvider provider= new MyEmployeeProvider();

        mMainFragment=new MainFragment();
        mLoginFragment = new LoginFragment();
        mRegisterFragment= new RegisterFragment();


        Fmain=(FrameLayout) findViewById(R.id.Fmain);
        Flogin=(FrameLayout) findViewById(R.id.Flogin);
        Fregister=(FrameLayout) findViewById(R.id.Fregister);



        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction trans =manager.beginTransaction();
        trans.add(R.id.Fmain,mMainFragment).commit();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.Flogin, mLoginFragment)
                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.Fregister, mRegisterFragment)
                .commit();



        Flogin.setVisibility(View.INVISIBLE);
        Fregister.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onButtonClick(Bundle bundle){
        String x=bundle.get("Action").toString();

        if(x=="Login")
        {
            Flogin.setVisibility(View.VISIBLE);
            Fmain.setVisibility(View.INVISIBLE);

        }

        if(x=="Register")
        {
            Fregister.setVisibility(View.VISIBLE);
            Fmain.setVisibility(View.INVISIBLE);
        }
    }
}
