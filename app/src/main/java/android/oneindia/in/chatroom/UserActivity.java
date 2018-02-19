package android.oneindia.in.chatroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by greynium on 16/2/18.
 */

public class UserActivity extends AppCompatActivity{
    ProgressDialog pd;

    ArrayList<String> UserArray = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdaptor userAdaptor;
    TextView text_totaluser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.usersList);
        text_totaluser=(TextView)findViewById(R.id.noUsersText);
        pd = new ProgressDialog(UserActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://chatroom-9a447.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {


                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Iterator i = obj.keys();
                String key = "";

                while(i.hasNext()){
                    key = i.next().toString();

                    if(!key.equals(UserDetails.username)) {
                        UserArray.add(key);
                    }


                }
                pd.dismiss();
                if(UserArray.size() <=1){
                    text_totaluser.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else{
                    text_totaluser.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    userAdaptor = new UserAdaptor (UserActivity.this,UserArray);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(UserActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(userAdaptor);

                    userAdaptor.notifyDataSetChanged();
                }




            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue Queue = Volley.newRequestQueue(UserActivity.this);
        Queue.add(request);
    }
}
