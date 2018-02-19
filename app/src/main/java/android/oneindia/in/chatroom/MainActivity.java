package android.oneindia.in.chatroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
EditText edit_user,edit_password;
Button btn_login;
TextView text_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login=(Button)findViewById(R.id.button_login);
        text_register=(TextView)findViewById(R.id.text_register);
        edit_user=(EditText)findViewById(R.id.edit_user);
         edit_password=(EditText)findViewById(R.id.edit_password);
        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_user.getText().toString().equals("")){
                    edit_user.setError("can't be blank");
                }
                else if(edit_password.getText().toString().equals("")){
                    edit_password.setError("can't be blank");
                }
                else{
                    String url = "https://chatroom-9a447.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("null")){
                                Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(response);

                                    if(!obj.has(edit_user.getText().toString())){
                                        Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(edit_user.getText().toString()).getString("password").equals(edit_password.getText().toString())){
                                        UserDetails.username = edit_user.getText().toString();
                                        UserDetails.password = edit_password.getText().toString();
                                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);
                }

            }
        });
    }

    }


