package example.sony.com.mobile_wallet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GirisActivity extends AppCompatActivity {

    private Button giris;
    private Button kayit;
    private EditText edtkimlik;
    private EditText edtsifre;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        giris =(Button) findViewById(R.id.button);
        kayit =(Button) findViewById(R.id.button2);
        edtkimlik =(EditText)findViewById(R.id.editText_gir_kimno) ;
        edtsifre = (EditText) findViewById(R.id.editText_gir_sifre);


        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hataKontrol()!=-1)
                {
                    if(girisKontrol()!=-1)
                    {
                        Intent gir = new Intent(GirisActivity.this,MenuActivity.class);
                        gir.putExtra("kim_no",edtkimlik.getText().toString());



                        startActivity(gir);
                        finish();
                    }
                    else Toast.makeText(GirisActivity.this.getApplicationContext(),"Lütfen Kimlik numarası ve şifreyi Kontrol edin",Toast.LENGTH_SHORT).show();
                }

            }
        });

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent kayıtIntent = new Intent(GirisActivity.this,KayitActivity.class);

                startActivityForResult(kayıtIntent,1);




            }
        });


    }

    private int girisKontrol()
    {

        final Database database = new Database(GirisActivity.this.getApplicationContext());
        final SQLiteDatabase db = database.getWritableDatabase();

        String[] projection = {
                "kimlik_no"
        };
        String selection ="kimlik_no = ? and sifre= ?";
        String[] selectionArgs = {edtkimlik.getText().toString(),edtsifre.getText().toString()};







        Cursor cursor = db.query(
                "kulanici_bilgiler",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

                    /*String   s="";
                    while(cursor.moveToNext()) {
                        s += cursor.getLong(0) +cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+ cursor.getString(4)+cursor.getLong(5)+cursor.getString(6);

                    }*/



        if (cursor.getCount()!=0) {
            Toast.makeText(GirisActivity.this.getApplicationContext(),"Giriş başarılı",Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
            database.close();
            return 1;


        }
        cursor.close();
        db.close();
        database.close();
        return -1;


        //finish();
    }

    private int hataKontrol()
    {
        if(edtkimlik.getText().equals("")||edtkimlik.getText().length()!=11)
        {

            Toast.makeText(GirisActivity.this.getApplicationContext(), "Tc Kimlik no boş bırakılamaz ve 11 basamaklı olması lazım", Toast.LENGTH_SHORT).show();

            return -1;
        }

        else if(edtsifre.getText().toString().equals(""))
        {
            Toast.makeText(GirisActivity.this.getApplicationContext(), "Şifre Boş bırakılamaz ", Toast.LENGTH_SHORT).show();

            return -1;
        }
        else return 1;
    }
}
