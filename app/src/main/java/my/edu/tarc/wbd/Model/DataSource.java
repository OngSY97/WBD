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

public class DataSource {
    private SQLiteDatabase myDatabase;
    private DatabaseHelper myDbHelper;
    public DataSource(Context context) {
        myDbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        myDatabase = myDbHelper.getWritableDatabase();
    }
    public void close(){
        myDbHelper.close();
    }

    public boolean insertData(personalAccount details){
        ContentValues values = new ContentValues();
        values.put( DatabaseContract.COLUMN_EMAIL, details.getEmail());
        values.put( DatabaseContract.COLUMN_NAME, details.getName());
        values.put( DatabaseContract.COLUMN_PASSWORD, details.getPassword());
        myDatabase = myDbHelper.getWritableDatabase();
       long result= myDatabase.insert(DatabaseContract.TABLE_NAME, null, values);
        myDatabase.close();
        if(result ==-1)
            return false;
        else
            return true;


    }

    public List<personalAccount> getAllDetails(){
        List<personalAccount> records = new ArrayList<>();

        myDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM " + DatabaseContract.TABLE_NAME, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            personalAccount record= new personalAccount();
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
