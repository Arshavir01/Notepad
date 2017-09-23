package com.example.user.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.notepad.realm.GetterSetter;
import com.example.user.notepad.realm.RealmHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    static ListView lv;
    private CustomAdapter adapter;

    private ArrayList<String> titleArray;
    private ArrayList<String> textArray;
    private RealmHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        adapter = new CustomAdapter(this, realm);
        lv.setAdapter(adapter);

        //Click on Item for Editting
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helper = new RealmHelper(realm);
                titleArray = helper.retrieveTitle();
                textArray = helper.retrieveText();

                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("titleKey", titleArray.get(position));
                intent.putExtra("textKey", textArray.get(position));
                intent.putExtra("posKey", position);
                startActivity(intent);

            }
        });

        //Long Click for deletting item
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alterDialog(position);
                return true;
            }
        });

    }

    public void init(){
        lv = (ListView)findViewById(R.id.listId);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    //Go to AddNoteActivity
    public void addClick(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }

    //Set Alter Dialog for deleting a note
    public void alterDialog(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this Note ?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Item Deleted", Toast.LENGTH_SHORT).show();
                final RealmResults<GetterSetter> results = realm.where(GetterSetter.class).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results.deleteFromRealm(pos);
                        adapter = new CustomAdapter(MainActivity.this, realm);
                        lv.setAdapter(adapter);
                    }
                });

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert =  builder.create();
        alert.show();

    }

}
