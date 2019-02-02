package com.example.a20111.mytraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String>itemsList;
    ArrayAdapter itemsAdapter;
    ListView lvItems;
    EditText editTx;
    Button okBtn;
    // numarical code to know the edit item activity
    public final static int EditItemCode=20;
    // know the string which sent the item text
    public final static String itemText= "item text ";
    // know the string which pass the postion of item
    public final static String itemPostion = "item postion ";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        //itemsList=new ArrayList<>();
        itemsAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemsList);
        lvItems=(ListView)findViewById(R.id. Lv_items);
        lvItems.setAdapter(itemsAdapter);
        //add data
       // itemsList.add("The First item");
       // itemsList.add("The Secand item");

        setUpListViewListener();



    }
    // add new item
    public void addNewItem(View view){
        // know the edit text
        editTx= (EditText)findViewById(R.id.edit_tx);
        String itemText= editTx.getText().toString();
        itemsAdapter.add(itemText);
        editTx.setText("");

        writeItem();
        // toast
        Toast.makeText(getApplicationContext(),"the item is added",Toast.LENGTH_SHORT).show();


    }
    // Make click to item
    private void setUpListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                writeItem();
                itemsList.remove(position);
                itemsAdapter.notifyDataSetChanged();

                return true;
            }
        });
        // when press one press it will intent to edit activity
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MainActivity.this,EditItemActivity.class);
                intent.putExtra(itemText,itemsList.get(position))
                        .putExtra(itemPostion,position);
                // start activity to display
                startActivityForResult(intent,EditItemCode);
            }
        });

    }
    //resive the update data from edit activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditItemCode &&resultCode==RESULT_OK)
        {
            String updateItem = data.getExtras().getString(itemText);
            int position= data.getExtras().getInt(itemPostion);
            // update item
            itemsList.set( position,updateItem);
            itemsAdapter.notifyDataSetChanged();
            writeItem();
            Toast.makeText(getApplicationContext(),"the Item is updated",Toast.LENGTH_SHORT).show();
        }
    }

    // read file add lib commons-io: commons-io 2.5 appache
    // create method to read file return file
    private File getDataFile(){
       return new File(getFilesDir(),"todo.Text");
    }
    // create method to read list view from the file
    private void readItems(){
        try {
            itemsList=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
           Log.e("MainActivity","cannot read a File",e);
            itemsList=new ArrayList<>();
        }

    }
    // create method to write item in list view
    private  void  writeItem(){
        try {
            FileUtils.writeLines(getDataFile(),itemsList);
           Toast.makeText(getApplicationContext(),"the item is added",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
           Log.e("MainActivity","fail to add item",e);
        }

    }}
