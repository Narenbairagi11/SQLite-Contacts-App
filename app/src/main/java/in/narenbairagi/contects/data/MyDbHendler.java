package in.narenbairagi.contects.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDbHendler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contact_db";

    // Table Name
    public static final String TABLE_NAME = "table_contact";
    // ID , NAME AND PHONE_NUMBER
    public static final String KEY_ID = "id";
    public static final String KEY_NMAE = "name";
    public static final String KEY_PHONE = "phonenumber";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NMAE + " TEXT,"
            + KEY_PHONE + " TEXT" + ")";

    public MyDbHendler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

    @Override
    public void onCreate(SQLiteDatabase db) {


        Log.d("DbNaren", "Query being run is : " + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
        Log.d("DbNaren", "Create Table Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.d("DbNa", "onUpgrade: ");


        // Create tables again
        onCreate(db);
    }

    public void addContect(String names, String phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NMAE,names);
        values.put(KEY_PHONE, phone);

        db.insert(TABLE_NAME, null, values);
        Log.d("DbNaren", "addContect: Name " + names +" Phone Number "+ phone + " Successfully");
        // close db connection
        db.close();


    }

    public JSONArray getAllContects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = " SELECT * FROM  " + TABLE_NAME;
        List<String> listColumn = new ArrayList();

        List<String> liststring = new ArrayList();
        Cursor cursor = db.rawQuery(select, null);
        JSONArray data = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            int totaldata = cursor.getColumnCount();
            JSONObject rowOb = new JSONObject();
            for (int i = 0 ; i<totaldata;i++){
                if (cursor.getColumnName(i) != null){
                    try {
                        rowOb.put(cursor.getColumnName(i),cursor.getString(i));
                        listColumn.add(cursor.getColumnName(i));
                        liststring.add(cursor.getString(i));

                        Log.d("DbNaren", "getAllContects: "+ cursor.getColumnName(i) +" "+ cursor.getString(i));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            data.put(rowOb);
            cursor.moveToNext();
        }
        cursor.close();


//        if (cursor.moveToFirst()) {
//            Contect contect = new Contect();
//            contect.setId(Integer.parseInt(cursor.getString(0)));
//            contect.setName(cursor.getString(1));
//            contect.setPhonenumber(cursor.getString(2));
//            contectList.add(contect);
//        }
//        while (cursor.moveToNext()) ;
        return data;
    }
}
