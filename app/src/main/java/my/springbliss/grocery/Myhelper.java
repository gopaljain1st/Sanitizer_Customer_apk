package my.springbliss.grocery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Myhelper extends SQLiteOpenHelper {
    private  static  final  String dbname="CartDb";
    private  static  final int version =1;
    public Myhelper(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //price real
        String sql="CREATE TABLE SPRINGBLISS(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRODUCT_PRICE TEXT,SELLING_PRICE TEXT,QTY INTEGER,SELEER_ID TEXT,ITEM_IMAGE TEXT,ITEMID TEXT,WEIGHT TEXT)";
        db.execSQL(sql);

     /*   String sql1="CREATE TABLE REGISTRATION(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Gmail TEXT,Phone TEXT,CITY TEXT,ADDRESS TEXT,Password TEXT)";
        db.execSQL(sql1);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
          //  db.execSQL("ALTER TABLE PRODUCT ADD COLUMN SELEER_ID TEXT");
            //db.execSQL("ALTER TABLE PRODUCT ADD COLUMN Item_image TEXT");
        }
    }
    /*public void delete(int position) {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = "PRODUCT";
        String whereClause = "id";
        String [] whereArgs = new String[] {String.valueOf(position)};
        db.delete (table, whereClause, whereArgs);

    }*/
}
