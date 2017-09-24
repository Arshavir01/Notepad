package com.example.user.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.notepad.realm.GetterSetter;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleET;
    private EditText textET;
    private Realm realm;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        titleET = (EditText)findViewById(R.id.noteTitleId);
        textET = (EditText)findViewById(R.id.noteTextId);

        // get old value for editing
        String edit_title = getIntent().getStringExtra("titleKey");
        String edit_text = getIntent().getStringExtra("textKey");

        // if edit_text is null it means you push Add button else you want to edit old value
        if(edit_text != null){
            titleET.setText(edit_title);
            textET.setText(edit_text);

        }

    }

    //Save button
    public void saveClick(View view) {

        final String title_str = titleET.getText().toString();
        final String text_str = textET.getText().toString();

        String dt = new Date().toString();
        final String subDt = dt.substring(0, 16);
        final String subYear = dt.substring(30,34);


        // for editting old value
        int position = getIntent().getIntExtra("posKey", -1);
        if(position == -1){
            //do nothing
        }else { deleteOldValue(position); }

        //if fields not empty save it in Realm database
        if(text_str.isEmpty()){
            Toast.makeText(this, "Fill Text Field", Toast.LENGTH_SHORT).show();
        }else{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    GetterSetter getterSetter = realm.createObject(GetterSetter.class);
                    //if Title is empty give first row of text
                    if(title_str.isEmpty()){

                        getterSetter.setTitle("No_Title...");
                    }else{
                        getterSetter.setTitle(title_str);
                    }
                    getterSetter.setText(text_str);
                    getterSetter.setDateTime(subDt+" "+subYear);

                }
            });

            //retrieve list
            adapter = new CustomAdapter(this, realm);
            MainActivity.lv.setAdapter(adapter);
            finish();

        }

    }

    //Delete old value
    public void deleteOldValue(final int pos){
        final RealmResults<GetterSetter> results = realm.where(GetterSetter.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteFromRealm(pos);
            }
        });
    }

    //Share button
    public void shareClick(View view) {
        String subject = titleET.getText().toString();
        String text = textET.getText().toString();

        if(text.isEmpty()){
            Toast.makeText(this,"Fill Text Field", Toast.LENGTH_LONG).show();

        }else {
            Intent intent;
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "Share via"));
        }
    }
}
