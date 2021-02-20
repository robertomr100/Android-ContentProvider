package edu.fsu.cs.mobile.hw5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ViewEmployeeFragment extends android.support.v4.app.Fragment {

    private TextView mID;
    private TextView mName;
    private TextView mEmail;
    private TextView mGender;
    private TextView mDepartment;
    private Button Logout;
    private Button Delete;

    public ViewEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_view_employee, container, false);
        Logout=(Button)view.findViewById(R.id.button);
        Delete=(Button)view.findViewById(R.id.button2);
        mID=(TextView)view.findViewById(R.id.ID_display);
        mName=(TextView)view.findViewById(R.id.Name_display);
        mEmail=(TextView)view.findViewById(R.id.Email_display);
        mGender=(TextView)view.findViewById(R.id.Gender_display);
        mDepartment=(TextView)view.findViewById(R.id.Department_display);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mrowsdeleted=0;
                String mSelectionClause= EmployeeContract.TransactionEntry._ID + "=?";
                String[] mSelectionArgs = new String[]{mID.getText().toString()};
                mrowsdeleted=getActivity().getContentResolver().delete( EmployeeContract.CONTENT_URI,mSelectionClause,mSelectionArgs);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String id = bundle.getString("ID");
            String name = bundle.getString("name");
            String department = bundle.getString("department");
            String gender = bundle.getString("gender");
            String email = bundle.getString("email");

            mID.setText(id);
            mName.setText(name);
            mDepartment.setText(department);
            mGender.setText(gender);
            mEmail.setText(email);
        }

    }
}
