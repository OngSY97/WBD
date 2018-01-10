package my.edu.tarc.wbd.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S3113 on 9/1/2018.
 */

public class PersonalDataSource {
    private SQLiteDatabase myDatabase;
    private PersonalDatabaseHelper myDbHelper;
    public PersonalDataSource(Context context) {
        myDbHelper = new PersonalDatabaseHelper(context);
    }

    public void open() throws SQLException {
        myDatabase = myDbHelper.getWritableDatabase();
    }
    public void close(){
        myDbHelper.close();
    }

    public boolean insertData(Account details){
        myDatabase = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( PersonalDatabaseContract.COLUMN_EMAIL, details.getEmail());
        values.put( PersonalDatabaseContract.COLUMN_NAME, details.getName());
        values.put( PersonalDatabaseContract.COLUMN_PASSWORD, details.getPassword());

       long result= myDatabase.insert(PersonalDatabaseContract.TABLE_NAME, null, values);
        myDatabase.close();
        if(result ==-1)
            return false;
        else
            return true;


    }

    public List<Account> getAllDetails(){
        List<Account> records = new ArrayList<>();

        myDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM " + PersonalDatabaseContract.TABLE_NAME, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Account record= new Account();
            record.setEmail(cursor.getString(0));
            record.setName(cursor.getString(1));
            record.setPassword(cursor.getString(2));

            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return records;
    }
}
