package android.oneindia.in.chatroom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by greynium on 16/2/18.
 */

class UserAdaptor extends  RecyclerView.Adapter<UserAdaptor.ViewHolder>
{
    ArrayList<String> userDetails1;
Context context;

    public UserAdaptor(Context context, ArrayList<String> userDetails1) {
        this.userDetails1=userDetails1;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_users, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

       holder.textuser.setText(userDetails1.get(position));
        holder.linear_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails.chatWith = userDetails1.get(position);
                Intent chatIntent=new Intent(context,ChatActivity.class);


                context.startActivity(chatIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDetails1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textuser;
        LinearLayout linear_chat;
        public ViewHolder(View itemView) {
            super(itemView);
            textuser=(TextView)itemView.findViewById(R.id.text_name);
            linear_chat=(LinearLayout)itemView.findViewById(R.id.linear_next);
        }
    }
}