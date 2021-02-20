package edu.fsu.cs.mobile.hw5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainFragment extends android.support.v4.app.Fragment {

    private Button LButton;
    private Button RButton;

    public MainFragment() {
        // Required empty public constructor
    }

    public interface OnButtonClickListener
    {
        void onButtonClick(Bundle bundle);
    }

    private OnButtonClickListener mClickListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_main, container, false);
        LButton =(Button) view.findViewById(R.id.Login);
        RButton =(Button) view.findViewById(R.id.Register);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        LButton =(Button) view.findViewById(R.id.Login);
        RButton =(Button) view.findViewById(R.id.Register);

        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putString("Action","Login");
                mClickListener.onButtonClick(bundle);
            }
        });

        RButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putString("Action","Register");
                mClickListener.onButtonClick(bundle);
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            mClickListener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }
}
