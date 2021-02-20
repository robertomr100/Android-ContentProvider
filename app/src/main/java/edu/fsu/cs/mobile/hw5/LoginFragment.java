package edu.fsu.cs.mobile.hw5;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import javax.microedition.khronos.egl.EGLDisplay;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends android.support.v4.app.Fragment {

    private Cursor mCursor;
    private TextView mID;
    private  TextView mCode;
    private EditText mIDinput;
    private EditText mCodeinput;
    private Button  mButton;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        mID=(TextView) view.findViewById(R.id.ID);
        mCode=(TextView) view.findViewById(R.id.AccessCode);
        mIDinput=(EditText)view.findViewById(R.id.IDinput);
        mCodeinput=(EditText)view.findViewById(R.id.Accessinput);
        mButton=(Button) view.findViewById(R.id.button3);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButton=(Button) view.findViewById(R.id.button3);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fill = "fill ";
                boolean complete=true;
                String user =mIDinput.getText().toString();

                if(mIDinput.getText().toString().matches(""))
                {
                    fill= fill+ " ID,";
                    complete=false;
                }
                if(mCodeinput.getText().toString().matches(""))
                {
                    fill= fill+ " access code,";
                    complete=false;
                }


                if(complete==false)
                {
                    Toast.makeText(getActivity(), fill, Toast.LENGTH_SHORT).show();
                }



                String mSelectionClause= EmployeeContract.TransactionEntry._ID + "=? AND "+ EmployeeContract.TransactionEntry.PASSWORD + "=?";
                String[] mSelectionArgs = new String[]{mIDinput.getText().toString(),mCodeinput.getText().toString()};
                mCursor = getContext().getContentResolver().query(
                        EmployeeContract.CONTENT_URI, null, mSelectionClause, mSelectionArgs,
                        null);




                //check if it is in databases

                if(complete==true) {

                    if (user.matches("ADMIN") ) {
                        //open the view admin employee fragment
                        Intent intent = new Intent(getActivity(), EmployeeViewActivity.class);
                        intent.putExtra("ADMIN", 1);
                        startActivity(intent);

                    }
                    else {
                        if(mCursor.getCount()==0)
                        {
                            complete=false;
                            Toast.makeText(getActivity(),"Incorrect ID and password", Toast.LENGTH_LONG).show();
                        }
                        else {

                            mCursor.moveToNext();
                            String id = mCursor.getString(0);
                            String name = mCursor.getString(1);
                            String email = mCursor.getString(2);
                            String gender = mCursor.getString(3);
                            String department = mCursor.getString(5);
                            Intent intent = new Intent(getActivity(), EmployeeViewActivity.class);
                            intent.putExtra("ADMIN", 0);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("gender", gender);
                            intent.putExtra("department", department);
                            intent.putExtra("ID", id);

                            ContentValues values= new ContentValues();
                            values.put(EmployeeContract.TransactionEntry._ID,id);
                            values.put(EmployeeContract.TransactionEntry.DEPARTMENT,department);
                            values.put(EmployeeContract.TransactionEntry.EMAIL,email);
                            values.put(EmployeeContract.TransactionEntry.NAME,name);
                            values.put(EmployeeContract.TransactionEntry.PASSWORD,mCodeinput.getText().toString());
                            values.put(EmployeeContract.TransactionEntry.GENDER,gender);
                            Date currentTime = Calendar.getInstance().getTime();
                            values.put(EmployeeContract.TransactionEntry.LASTLOGIN,currentTime.toString());

                            mSelectionClause= EmployeeContract.TransactionEntry._ID + "=?";
                             mSelectionArgs = new String[]{id};

                            int mRowsSelected=getActivity().getContentResolver().update(EmployeeContract.CONTENT_URI,values,mSelectionClause,mSelectionArgs);


                            startActivity(intent);

                        }
                    }
                }

            }
        });
    }
}
