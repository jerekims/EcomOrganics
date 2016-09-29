package com.example.jere.ecomorganics;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class categoryFragment extends Fragment {
    private TextView catname;
    private ListView plistView;
    Long id;

    private static String allprods = "http://www.tusome.co.ke/livestock/alllivestockprod.php";

    ArrayList<HashMap<String, String>> catproducts;

    public categoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String value = getArguments().getString("cname");
        id=getArguments().getLong("catid");
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.fragment_category, container, false);
        catname=(TextView)relativeLayout.findViewById(R.id.products);
        catname.setText("Category Name : "+value);
        catproducts =new ArrayList<>();
        plistView=(ListView)relativeLayout.findViewById(R.id.plistView);
        new getproductcat().execute();
        return  relativeLayout;

    }

    private class getproductcat extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            Long catid = id;
            String cid=String.valueOf(catid);

            try {
                URL url = new URL(allprods);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data = URLEncoder.encode("cid","UTF-8") +"="+ URLEncoder.encode(cid,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }

                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                JSONObject resultJSON = null;
                try {
                    resultJSON = new JSONObject(result);
                    JSONArray breeds = resultJSON.getJSONArray("catprods");


                    for (int i = 0; i < breeds.length(); i++) {
                        JSONObject obj = breeds.getJSONObject(i);

                        int btno = i+1;
                        String prodid = obj.getString("livestock_prod_id");
                        String prodname = obj.getString("livestock_prod_name");
                        String prodcatid = obj.getString("livestock_cat_id");
                        String price = obj.getString("live_price");
                        String desc = obj.getString("live_desc");
                        String date = obj.getString("date_added");


                        HashMap<String, String> prods = new HashMap<>();


                        prods.put("btno", String.valueOf(btno));
                        prods.put("prodid", prodid);
                        prods.put("prodname", prodname);
                        prods.put("prodcatid", prodcatid);
                        prods.put("price", price);
                        prods.put("desc", desc);
                        prods.put("date", date);


                        catproducts.add(prods);
                        //Log.e(TAG, "Response from url: " + breedlist);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter(getActivity(), catproducts,
                    R.layout.list_products, new String[]{"prodname"},
                    new int[]{ R.id.prodname});

            plistView.setAdapter(adapter);
            plistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent lives = new Intent(categoryFragment.this, BreedCriteria.class);
//
//                String ltid = ((TextView) view.findViewById(R.id.tvid)).getText().toString();
//                String ltname = ((TextView) view.findViewById(R.id.tvname)).getText().toString();
//
//                lives.putExtra("ltid", ltid);
//                lives.putExtra("ltname", ltname);
//
//                lives.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(lives);


                }
            });
        }
    }



}
