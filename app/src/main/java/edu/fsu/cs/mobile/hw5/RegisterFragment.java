package edu.fsu.cs.mobile.hw5;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends android.support.v4.app.Fragment {

    private EditText mID;
    private EditText mName;
    private EditText mEmail;
    private RadioGroup mRadio;
    private EditText mCode;
    private EditText mConfirmation;
    private Spinner mDepartment;
    private CheckBox mAgree;
    private Button mReset;
    private Button mRegister;
    Cursor mCursor;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_register, container, false);

        mID=(EditText)view.findViewById(R.id.id_input);
        mName=(EditText)view.findViewById(R.id.name_input);
        mEmail=(EditText)view.findViewById(R.id.email_input);
        mCode=(EditText)view.findViewById(R.id.code_input);
        mConfirmation=(EditText)view.findViewById(R.id.confirmation_input);
        mRadio=(RadioGroup) view.findViewById(R.id.gender_input);
        mDepartment=(Spinner) view.findViewById(R.id.spinner);
        mAgree=(CheckBox) view.findViewById(R.id.agree_input);
        mReset=(Button)view.findViewById(R.id.button_reset);
        mRegister=(Button)view.findViewById(R.id.submit_reset);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReset=(Button)view.findViewById(R.id.button_reset);
        mRegister=(Button)view.findViewById(R.id.submit_reset);
        mDepartment=(Spinner) view.findViewById(R.id.spinner);


        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mID.getText().clear();
                mName.getText().clear();
                mEmail.getText().clear();
                mCode.getText().clear();
                mConfirmation.getText().clear();
                mRadio.clearCheck();
                mAgree.setChecked(false);
                mDepartment.setSelection(0);
            }
        });

       mRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String fill="Fill";
               boolean complete=true;
               if(view==mRegister)
               {
                   if(mID.getText().toString().matches(""))
                   {
                       fill= fill+ " ID,";
                       complete=false;
                   }
                   if(mName.getText().toString().matches(""))
                   {
                       fill= fill+ " name,";
                       complete=false;
                   }
                   if(mEmail.getText().toString().matches(""))
                   {
                       fill= fill+ " email,";
                       complete=false;
                   }
                   if(mRadio.getCheckedRadioButtonId()==-1)
                   {
                       fill= fill+ " gender,";
                       complete=false;
                   }
                   if(mCode.getText().toString().matches(""))
                   {
                       fill= fill+ " access code,";
                       complete=false;
                   }
                   if(mConfirmation.getText().toString().matches(""))
                   {
                       fill= fill+ " confirmation to access code,";
                       complete=false;
                   }
                   if(mAgree.isChecked()==false)
                   {
                       fill= fill+ " agreement to terms";
                       complete=false;
                   }

                   if(complete==false)
                   {
                       Toast.makeText(getActivity(), fill, Toast.LENGTH_SHORT).show();
                   }


                   if(complete==true &&!mCode.getText().toString().equals( mConfirmation.getText().toString()) )
                   {
                       Toast.makeText(getActivity(),"Incorrect Confirmation code",Toast.LENGTH_SHORT).show();
                       complete=false;

                   }

                    //check for valid
                   String mSelectionClause= EmployeeContract.TransactionEntry._ID + "=?";
                   String[] mSelectionArgs = new String[]{mID.getText().toString()};
                   mCursor = getContext().getContentResolver().query(
                           EmployeeContract.CONTENT_URI, null, mSelectionClause, mSelectionArgs,
                           null);
                   if(complete==true&& mCursor.getCount()!=0)
                   {
                       Toast.makeText(getActivity(),"ID already exists",Toast.LENGTH_SHORT).show();
                       complete=false;
                   }

                   if(complete==true)
                   {

                       ContentValues values= new ContentValues();
                       values.put(EmployeeContract.TransactionEntry._ID,mID.getText().toString());
                       values.put(EmployeeContract.TransactionEntry.DEPARTMENT,mDepartment.getSelectedItem().toString());
                       values.put(EmployeeContract.TransactionEntry.EMAIL,mEmail.getText().toString());
                       values.put(EmployeeContract.TransactionEntry.NAME,mName.getText().toString());
                       values.put(EmployeeContract.TransactionEntry.PASSWORD,mCode.getText().toString());

                       if(mRadio.getCheckedRadioButtonId()==R.id.female)
                           values.put(EmployeeContract.TransactionEntry.GENDER,"female");

                       else
                           values.put(EmployeeContract.TransactionEntry.GENDER,"male");

                       Date currentTime = Calendar.getInstance().getTime();

                       values.put(EmployeeContract.TransactionEntry.LASTLOGIN,currentTime.toString());

                       final Uri newUri=getContext().getContentResolver().insert(EmployeeContract.CONTENT_URI,values);



                       Intent intent=new Intent(getActivity(),EmployeeViewActivity.class);
                       intent.putExtra("ID",mID.getText().toString());
                       intent.putExtra("name",mName.getText().toString());
                       intent.putExtra("email",mEmail.getText().toString());
                       intent.putExtra("code",mCode.getText().toString());
                       if(mRadio.getCheckedRadioButtonId()==R.id.female)
                           intent.putExtra("gender","Female");

                       else
                           intent.putExtra("gender","Male");
                       intent.putExtra("department",mDepartment.getSelectedItem().toString());
                       intent.putExtra("ADMIN", 0);

                       startActivity(intent);
                   }
               }




           }
       });

    }



}
