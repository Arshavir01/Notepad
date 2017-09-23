package com.example.user.notepad.realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by user on 23.09.2017.
 */

public class RealmHelper {
    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //show Title
    public ArrayList<String> retrieveTitle(){

        ArrayList<String> titleList = new ArrayList<>();
        RealmResults<GetterSetter> realmResults = realm.where(GetterSetter.class).findAll();
        for(int i = 0; i < realmResults.size(); i++){
            titleList.add(realmResults.get(i).getTitle());
        }
        return titleList;
    }

    //show Text
    public ArrayList<String> retrieveText(){
        ArrayList<String> textList = new ArrayList<>();

        RealmResults<GetterSetter> realmResults = realm.where(GetterSetter.class).findAll();
        for(int i = 0; i < realmResults.size(); i++){
            textList.add(realmResults.get(i).getText());
        }
        return textList;
    }

    //show Date and Time
    public ArrayList<String> retrieveDateTime(){

        ArrayList<String> dateList = new ArrayList<>();
        RealmResults<GetterSetter> realmResults = realm.where(GetterSetter.class).findAll();
        for(int i = 0; i < realmResults.size(); i++){
            dateList.add(realmResults.get(i).getDateTime());
        }

        return dateList;

    }
}
