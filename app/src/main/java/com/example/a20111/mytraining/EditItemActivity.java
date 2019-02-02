package com.example.a20111.mytraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.a20111.mytraining.MainActivity.itemPostion;
import static com.example.a20111.mytraining.MainActivity.itemText;

public class EditItemActivity extends AppCompatActivity {
    // know the edit text
    EditText etItemText;
    // know position
    int position ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // recivie the position and item form the main acticity
        etItemText= (EditText)findViewById(R.id.editItemText);
        etItemText.setText(getIntent().getStringExtra(itemText));
        position= getIntent().getIntExtra(itemPostion,0);
        // named the toolbar by name activity
        getSupportActionBar().setTitle("Edit Activity");
    }
    public void saveEditedItem (View view){
        Intent i = new Intent();
        i.putExtra(itemText,etItemText.getText().toString())
                .putExtra(itemPostion,position);
        setResult(RESULT_OK,i);
        finish();

    }
}
