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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class KartEkleActivity extends AppCompatActivity {

    private String kim_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kart_ekle);

        kim_no=getIntent().getStringExtra("kimNo") ;
        dialogKartekle();

    }

     void dialogKartekle()
    {

        final String[] karttur={""};



        final EditText kartno =(EditText) findViewById(R.id.editText_kartno);
        final EditText cvv = (EditText) findViewById(R.id.EditTextcvv);
        final Button kayitet = (Button) findViewById(R.id.buttkaydet);
        final RadioGroup radioGroup =(RadioGroup) findViewById(R.id.radioGroup);




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton radiobtnp =(RadioButton) findViewById(checkedId);
                karttur[0]=radiobtnp.getText().toString();
             //  adp.notifyDataSetChanged();

                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });



        kayitet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Database database = new Database(KartEkleActivity.this.getApplicationContext());
                final SQLiteDatabase db = database.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("kart_no", Long.parseLong(kartno.getText().toString()));
                values.put("cvv", Integer.parseInt(cvv.getText().toString()));
                values.put("kart_turu", karttur[0]);
                values.put("kimlik_no", Long.parseLong(kim_no));
                values.put("kart_bakiye", 1000);

                long RowId = db.insert("kart_bilgiler", null, values);
                if (RowId!=-1)
                {
                    Toast.makeText(KartEkleActivity.this,"kart kayıt edildi",Toast.LENGTH_LONG).show();

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);

                    finish();

                }
                else
                    Toast.makeText(KartEkleActivity.this,"Kayıt edilemedi",Toast.LENGTH_LONG).show();


                db.close();
                database.close();


            }
        });






    }


}
