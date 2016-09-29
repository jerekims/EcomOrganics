package com.example.jere.ecomorganics;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static String url = "http://www.tusome.co.ke/livestock/livestockcattype.php";
    ArrayList<HashMap<String, String>> livestockcatype;

    private ListView lv;
    networkConnection nconn;
    ProgressDialog pDialog;
    FragmentTransaction fragmentTransaction;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nconn = new networkConnection(getActivity());
        if(nconn.isConnected()){
        }else{
            Toast.makeText(getContext(),"Connection could not be established",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Turn on Wifi  to view categories",Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        FrameLayout frameLayout=(FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);
        livestockcatype = new ArrayList<>();
        lv = (ListView) frameLayout.findViewById(R.id.listView);
        new GetAllLivestockcattype().execute();
        return frameLayout;
    }
    private class GetAllLivestockcattype extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Categories Loading...");
            pDialog.setCancelable(true);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray livestock = jsonObj.getJSONArray("livestockcattype");

                    // looping through All Contacts
                    for (int i = 0; i < livestock.length(); i++) {
                        JSONObject obj = livestock.getJSONObject(i);

                        int catno = i+1;
                        String catid = obj.getString("catid");
                        String catname = obj.getString("catname");


                        HashMap<String, String> live = new HashMap<>();


                        live.put("catno", String.valueOf(catno));
                        live.put("catid", catid);
                        live.put("catname", catname);

                        // adding contact to adverts list
                        livestockcatype.add(live);
                    }
                } catch (final JSONException e) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(new HomeFragment().getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check your internet connectivity",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(getActivity(), livestockcatype,
                    R.layout.list_categories, new String[]{"catname"},
                    new int[]{ R.id.catnames});

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   // Toast.makeText(getActivity(),"You have clicked me" +id ,Toast.LENGTH_SHORT).show();
                    String catname = ((TextView) view.findViewById(R.id.catnames)).getText().toString();
                    //Put the value
                    categoryFragment ldf = new categoryFragment ();
                    Bundle args = new Bundle();
                    args.putString("cname", catname);
                    args.putLong("catid",id);
                    ldf.setArguments(args);

                    //Inflate the fragment
                    fragmentTransaction= getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, ldf).commit();

                    //Toast.makeText(HomeFragment.this, "You have clc", Toast.LENGTH_SHORT).show();
                    //Intent lives = new Intent(HomeFragment.this, new categoryFragment().getActivity());

                   //String ltid = ((TextView) view.findViewById(R.id.tvid)).getText().toString();
                  // String catname = ((TextView) view.findViewById(R.id.catnames)).getText().toString();

                    //lives.putExtra("ltid", ltid);
                  // lives.putExtra("catname", catname);

                   // lives.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // startActivity(lives);


                }
            });
        }


    }



}
