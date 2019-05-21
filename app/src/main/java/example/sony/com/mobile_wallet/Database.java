package example.sony.com.mobile_wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY on 1/12/2017.
 */

public class Database extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MobileWallet.db";

    private String table_kulanici_bilgiler="CREATE TABLE kulanici_bilgiler ( kimlik_no INTEGER PRIMARY KEY," +
            "ad TXT,soyad TXT,cinsiyet TXT, email TXT, tel INTEGER ,sifre TXT )";
    private String table_kart_bilgiler="CREATE TABLE kart_bilgiler ( kart_no INTEGER PRIMARY KEY,cvv INTEGER,kart_turu TXT,kimlik_no INTEGER,kart_bakiye Integer )";

    private String table_para_islemleri = "CREATE TABLE para_islemleri ( a_kart_no INTEGER ,g_kart_no INTEGER,miktar Integer)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_kulanici_bilgiler);
        db.execSQL(table_kart_bilgiler);
        db.execSQL(table_para_islemleri);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS kulanici_bilgiler");
        db.execSQL("DROP TABLE IF EXISTS kart_bilgiler");
        db.execSQL("DROP TABLE IF EXISTS para_islemleri");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
