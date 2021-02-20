package edu.fsu.cs.mobile.hw5;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.security.PrivateKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAdminEmployeeFragment extends android.support.v4.app.Fragment {

    private Button button;
    private Cursor mCursor;
    private ListView mListview;


    public ViewAdminEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_admin_employee, container, false);
        button=(Button)view.findViewById(R.id.logout_admin);
        mListview=(ListView)view.findViewById(R.id.List);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(Button)view.findViewById(R.id.logout_admin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        String[] mProjection = new String[]{
                EmployeeContract.TransactionEntry._ID,
                EmployeeContract.TransactionEntry.LASTLOGIN, };

        mCursor=getActivity().getContentResolver().query(EmployeeContract.CONTENT_URI,mProjection,null,null,null);
        Toast.makeText(getActivity(),Integer.toString(mCursor.getCount()),Toast.LENGTH_LONG).show();

        String[] mListColumns= new String[]
                {
                   EmployeeContract.TransactionEntry._ID,
                    EmployeeContract.TransactionEntry.LASTLOGIN
                };
        int[] mListItems= new int[]
                {
                    R.id.ID_row,
                    R.id.Time_row
                };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.display_user_row, mCursor,mListColumns,mListItems,0 );
        mListview=(ListView)view.findViewById(R.id.List);
        mListview.setAdapter(adapter);

    }
}
