package com.example.todo_firebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.todo_firebase.R.*;
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<User> users;
    Context context;
    private int mViewResourceId;
    private int row_index=-1;
    DataBaseHelper myDB;


    public ListAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.mViewResourceId = textViewResourceId;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        myDB=new DataBaseHelper(context);
        return viewHolder;


    }


    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        final User myListData = users.get(position);
        int current_position=position;
        User infoData=users.get(position);
        viewholder.simpleTextView.setText(users.get(position).getDescription());

        viewholder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               row_index =position;
                notifyDataSetChanged();
            }
        });

        viewholder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newpos= viewholder.getAdapterPosition();
                Log.d("hello","on click bindviewholder");
                User desc= users.get(newpos);
                users.remove(newpos);
                notifyItemRemoved(newpos);
                notifyItemRangeChanged(newpos, users.size());

                myDB.deleteuserdata(desc);


            }
        });

        viewholder.updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View alertLayout = inflater.inflate(layout.update_layout, null);
                final EditText etTodo = alertLayout.findViewById(id.descrip);

                final Button done=alertLayout.findViewById(id.update);
                final Button close=alertLayout.findViewById(id.close);

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(alertLayout);
                alert.setCancelable(false);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;



                AlertDialog dialog = alert.create();

                dialog.getWindow().setAttributes(lp);


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newtodo = etTodo.getText().toString();
                        Toast.makeText(context, "Todo Updated to: " + newtodo, Toast.LENGTH_LONG).show();
                        viewholder.simpleTextView.setText(newtodo);
                        myDB.updateuserdata(newtodo);
                        dialog.dismiss();


                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });




        if(row_index==position){
            viewholder.row_linearlayout.setBackgroundColor(Color.parseColor("#c7c73e"));
            viewholder.simpleTextView.setTextColor(Color.parseColor("#ffffff"));
            viewholder.checked.setVisibility(View.VISIBLE);
            viewholder.updated.setVisibility(View.VISIBLE);
            viewholder.delete.setVisibility(View.VISIBLE);

        }



        else {
            viewholder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            viewholder.simpleTextView.setTextColor(Color.parseColor("#000000"));
            viewholder.checked.setVisibility(View.INVISIBLE);
            viewholder.updated.setVisibility(View.INVISIBLE);
            viewholder.delete.setVisibility(View.INVISIBLE);

        }
    }





    @Override
    public int getItemCount() {
        return users.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mview;
        private Boolean value=false;

        public TextView simpleTextView;
        public ImageView checked;
        public ImageView updated;
        public ImageView delete;

        LinearLayout row_linearlayout;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
            simpleTextView = (TextView) itemView.findViewById(id.textView);
            checked=(ImageView) itemView.findViewById(id.checked);
            updated=(ImageView) itemView.findViewById(id.update);
            delete=(ImageView) itemView.findViewById(id.delete);

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!value) {

                        simpleTextView.setPaintFlags(simpleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        value=true;

                    }
                    else{

                        simpleTextView.setPaintFlags(simpleTextView.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        value=false;

                    }
                }
            });

            updated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            row_linearlayout=(LinearLayout) itemView.findViewById(id.linearLayout);
        }
    }
}