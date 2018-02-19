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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {
    EditText edit_username,edit_password;
    Button btn_register;

    private String mUser,mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(RegisterActivity.this);
        btn_register=(Button)findViewById(R.id.btn_register);

        edit_username=(EditText)findViewById(R.id.edit_username);
        edit_password=(EditText)findViewById(R.id.password);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = edit_username.getText().toString();
                mPassword = edit_password.getText().toString();

                if(mUser.equals("")){
                    edit_username.setError("can't be blank");
                }
                else if(mPassword.equals("")){
                    edit_password.setError("can't be blank");
                }

                else {
                    final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://chatroom-9a447.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String mResponse) {
                           Firebase reference = new Firebase("https://chatroom-9a447.firebaseio.com/users");

                            if(mResponse.equals("null")) {
                                reference.child(mUser).child("password").setValue(mPassword);
                                Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(mResponse);

                                    if (!obj.has(mUser)) {
                                        reference.child(mUser).child("password").setValue(mPassword);
                                        Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });


                    RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
                    rQueue.add(request);
                }

            }
        });
    }
}

