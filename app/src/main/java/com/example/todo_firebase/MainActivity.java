package com.example.todo_firebase;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    DataBaseHelper myDB;
    EditText  Desc;
    ImageView updated;
    ArrayList<User> userList;
    User user;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataBaseHelper(this);
        userList = new ArrayList<>();

        Desc = (EditText) findViewById(R.id.desc);
        add = (Button) findViewById(R.id.add);
        updated=(ImageView)findViewById(R.id.update);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc = Desc.getText().toString();

                if (desc.length() != 0) {
                    AddData(desc);

                    Desc.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "You must put something in the text field", Toast.LENGTH_SHORT).show();
                }
            }
        });




        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(this, "there is nothing in the database", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                user = new User(data.getString(1));
                userList.add(user);
            }
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recView);
        registerForContextMenu(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ListAdapter adapter = new ListAdapter(this, R.layout.list_item, userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.recView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id. check:
                // add stuff here
                return true;
            case R.id.update:
                // edit stuff here
                return true;
            case R.id.delete:
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }*/



    public void AddData(String desc){
        boolean insertdata=myDB.addData(desc);

        if(insertdata==true){
            Toast.makeText(MainActivity.this,"Todo Added",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

}