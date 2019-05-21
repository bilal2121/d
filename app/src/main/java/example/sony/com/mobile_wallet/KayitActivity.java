package example.sony.com.mobile_wallet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by SONY on 1/8/2017.
 */

public class KayitActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
     private EditText kimlik_no;
    private EditText ad;
    private EditText soyad;
    private Spinner cinsiyet;
    private EditText tel;
    private EditText email;
    private EditText sifre;
    private EditText sifretekrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);



         kimlik_no = (EditText) findViewById(R.id.editText_kimlikno);
         ad = (EditText) findViewById(R.id.editText_ad);
         soyad = (EditText) findViewById(R.id.editText_soyad);
         cinsiyet = (Spinner) findViewById(R.id.spinner_cinsiyet);
         tel = (EditText) findViewById(R.id.editText_tel);
         email = (EditText) findViewById(R.id.editText_email);
         sifre = (EditText) findViewById(R.id.editText_sifre);
         sifretekrar = (EditText) findViewById(R.id.editText_sifretekrar);


        final Button kayit_ol = (Button) findViewById(R.id.button_kayitol);



        kayit_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            if(hataKontrolEt()!=-1)
            {
                if(veriKayitEt()!=-1)
                {
                    Intent menuIntent = new Intent(KayitActivity.this,MenuActivity.class);
                    menuIntent.putExtra("kim_no",kimlik_no.getText().toString());

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);

                    startActivity(menuIntent);
                    finish();

                }

            }
  }
        });



    }

    private int veriKayitEt()
    {
         Database database = new Database(KayitActivity.this.getApplicationContext());
         SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("kimlik_no", Long.parseLong(kimlik_no.getText().toString()));
        values.put("ad", ad.getText().toString());
        values.put("soyad", soyad.getText().toString());
        values.put("cinsiyet",cinsiyet.getSelectedItem().toString() );
        values.put("tel", Long.parseLong(tel.getText().toString()));
        values.put("email", email.getText().toString());
        values.put("sifre", sifre.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long RowId = db.insert("kulanici_bilgiler", null, values);
        db.close();
        database.close();

        if (RowId !=-1)
        {
            Toast.makeText(KayitActivity.this.getApplicationContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();
            return 1;
        }
        else Toast.makeText(KayitActivity.this.getApplicationContext(), "Kayıt Başarılı olamadı", Toast.LENGTH_LONG).show();
        return -1;



    }

    private int hataKontrolEt()
    {
        if(kimlik_no.getText().equals("")||kimlik_no.getText().length()!=11)
    {

        Toast.makeText(KayitActivity.this.getApplicationContext(), "Tc Kimlik no boş bırakılamaz ve 11 basamaklı olması lazım", Toast.LENGTH_LONG).show();
        kimlik_no.requestFocus();
        return -1;
    }
    else if(ad.getText().toString().equals(""))
    {
        Toast.makeText(KayitActivity.this.getApplicationContext(), "Ad boş bırakılamaz ", Toast.LENGTH_LONG).show();
        ad.requestFocus();
        return -1;
    }
    else if(soyad.getText().toString().equals(""))
    {
        Toast.makeText(KayitActivity.this.getApplicationContext(), "Soyad boş bırakılamaz ", Toast.LENGTH_LONG).show();
        soyad.requestFocus();
        return -1;
    }
    else if(tel.getText().toString().equals(""))
    {
        Toast.makeText(KayitActivity.this.getApplicationContext(), "Telefon Numarası boş bırakılamaz ", Toast.LENGTH_LONG).show();
        tel.requestFocus();
        return -1;
    }
    else if(email.getText().toString().equals(""))
    {
        Toast.makeText(KayitActivity.this.getApplicationContext(), "Email  boş bırakılamaz ", Toast.LENGTH_LONG).show();
        email.requestFocus();
        return -1;
    }
    else if(!sifre.getText().toString().equals(sifretekrar.getText().toString()))
    {
        Toast.makeText(KayitActivity.this.getApplicationContext(), "Şifreler uyuşmuyor ", Toast.LENGTH_LONG).show();
        sifre.requestFocus();
        return -1;
    }
    else return 1;

    }

    private void kayitListele()
    {
        /*String[] projection = {
                        "kimlik_no","ad","soyad","cinsiyet","tel","email","sifre"
                };
                if (1 != -1) {
                    Toast.makeText(KayitActivity.this.getApplicationContext(), "Kayit tamam", Toast.LENGTH_LONG).show();

                    Cursor cursor = db.query(
                            "kulanici_bilgiler",                     // The table to query
                            projection,                               // The columns to return
                            null,                                // The columns for the WHERE clause
                            null,                            // The values for the WHERE clause
                            null,                                     // don't group the rows
                            null,                                     // don't filter by row groups
                            null                                 // The sort order
                    );

                    String   s="";
                    while(cursor.moveToNext()) {
                        s += cursor.getLong(0) +cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+ cursor.getString(4)+cursor.getLong(5)+cursor.getString(6);

                    }
                    cursor.close();

                    if (1 != -1) {
                        sifretekrar.setText(s);
                    }
                }*/
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

}
