package com.pratyay.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private  String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            mSnapshotList.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public  ChatListAdapter(Activity activity, DatabaseReference ref, String name){
        mActivity=activity;
        mDisplayName=name;
        mDatabaseReference=ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();


    }

    static class ViewHolder{
        TextView authorName;
        TextView body;
        ViewGroup.LayoutParams params;
    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public InstanceMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(InstanceMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater=(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);
            final ViewHolder holder=new ViewHolder();
            holder.authorName=(TextView) convertView.findViewById(R.id.author);
            holder.body=(TextView) convertView.findViewById(R.id.message);
            holder.params=(LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }
        final InstanceMessage message=getItem(position);
        final  ViewHolder holder=(ViewHolder) convertView.getTag();

        boolean isMe =message.getAuthor().equals(mDisplayName);
        setChatRowApperance(isMe,holder);

        String author = message.getAuthor();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);

        return convertView;
    }

    private void setChatRowApperance(boolean isItMe, ViewHolder holder){
        if(isItMe){
//            holder.params.gravity=Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);
        } else {
//            holder.params.gravity=Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }

    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }
}
