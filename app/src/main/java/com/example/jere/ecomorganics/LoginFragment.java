package com.example.jere.ecomorganics;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText username;
    private EditText password;
    private Button mbutton;

    String uname,pass;


    networkConnection nconn;
    AlertDialog.Builder builder;
    String ulogin;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout=(FrameLayout)inflater.inflate(R.layout.fragment_login, container, false);
        username=(EditText)frameLayout.findViewById(R.id.lusername);
        password=(EditText)frameLayout.findViewById(R.id.lpassword);

        mbutton=(Button)frameLayout.findViewById(R.id.blogin);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nconn = new networkConnection(getActivity());
                if(nconn.isConnected()){
                }else{
                    Toast.makeText(getContext(),"Connection could not be established",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"Turn on Wifi  to register",Toast.LENGTH_SHORT).show();
                }

                builder = new AlertDialog.Builder(getActivity());
                uname=username.getText().toString();
                pass=password.getText().toString();

                if(uname.equalsIgnoreCase("")||pass.equalsIgnoreCase("")){
                    builder.setTitle("Error");
                    builder.setMessage("Please enter all fields");
                    builder.setCancelable(false)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    // Register.this.finish();
                                }
                            });
                    builder.create();builder.show();
                }else {
                    ulogin="login";
                    DBTasks dbt = new DBTasks(getActivity());
                    dbt.execute(ulogin,uname, pass);
                }

            }
        });

        return frameLayout;
    }

}
