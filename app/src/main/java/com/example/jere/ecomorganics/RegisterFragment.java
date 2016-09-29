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
public class RegisterFragment extends Fragment {
    private EditText name;
    private EditText location;
    private EditText phone;
    private EditText username;
    private EditText password;
    private EditText confirm;
    private Button register;

    networkConnection nconn;
    AlertDialog.Builder builder;
    String pname,plocation,pphone,pusername,ppassword,pconfirm,function;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_login, container, false);
        FrameLayout frameLayout= (FrameLayout) inflater.inflate(R.layout.fragment_register, container, false);

        name=(EditText)frameLayout.findViewById(R.id.name);
        location=(EditText) frameLayout.findViewById(R.id.location);
        phone=(EditText)frameLayout.findViewById(R.id.pnumber);
        username=(EditText)frameLayout.findViewById(R.id.username);
        password=(EditText)frameLayout.findViewById(R.id.password);
        confirm=(EditText)frameLayout.findViewById(R.id.cpassword);
        register=(Button) frameLayout.findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nconn = new networkConnection(getActivity());
                if(nconn.isConnected()){
                }else{
                    Toast.makeText(getContext(),"Connection could not be established",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"Turn on Wifi  to register",Toast.LENGTH_SHORT).show();
                }
                builder = new AlertDialog.Builder(getActivity());

                pname=name.getText().toString();
                plocation=location.getText().toString();
                pphone=phone.getText().toString();
                pusername=username.getText().toString();
                ppassword=password.getText().toString();
                pconfirm=confirm.getText().toString();

                if(pname.equalsIgnoreCase("")||plocation.equalsIgnoreCase("")||pphone.equalsIgnoreCase("")
                        ||pusername.equalsIgnoreCase("")||ppassword.equalsIgnoreCase("")||pconfirm.equalsIgnoreCase("")){
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
                    if(ppassword.equals(pconfirm)){
                        function = "register";
                        DBTasks dbt = new DBTasks(getActivity());
                        dbt.execute(function,pname,plocation,pphone,pusername,ppassword);
                        getActivity().finish();
                    }else{
                        builder.setTitle("Error");
                        builder.setMessage("Passwords do not match...");
                        builder.setCancelable(false)
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        getActivity().finish();
                                    }
                                });
                        builder.create();
                        builder.show();
                        password.setText("");
                        confirm.setText("");

                    }
                }


            }
        });

        return frameLayout;
    }

}


