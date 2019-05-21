package example.sony.com.mobile_wallet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SONY on 1/9/2017.
 */


public class KartlarFragment extends Fragment {

    private Button kartekle;
    private Button kartsil;
    private ImageView foto;
    private TextView textAdSoyad;
    private TextView textViewKartno;
    private ListView kartlist;
    String kartlar[] = {"Bonus", "Gold", "Maximum"};
    ArrayList<String> kar = new ArrayList<String>();
    ArrayList<Kartbilgiler> kartbilgilerArrayList = new ArrayList<Kartbilgiler>();
    private OzelAdapter adp;
    Context context;
    AlertDialog.Builder dialog;
    private Intent intent;
    private String kim_no;
    private int imageId;
    private String adSoyad;
    private String bakiye;
    private TextView textBakiye;
    private int listItem;
    private int listItemeski;
    private String kartNo;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kim_no = getArguments().getString("kim_no");
        //Log.i("Bilgi", "Kartlar Oncreate");


        //Bundle bundle =getArguments();
        //kim_no= bundle.getString("kim_no");


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                kartbilgilerArrayList.clear();
                kartListDoldur();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.fragment_kartlar, container, false);

        kartekle = (Button) lay.findViewById(R.id.btnKartEkle);
        kartsil = (Button) lay.findViewById(R.id.btnKartSil);
        foto = (ImageView) lay.findViewById(R.id.foto);
        textAdSoyad = (TextView) lay.findViewById(R.id.textView5);
        textBakiye = (TextView) lay.findViewById(R.id.textView7);
        textViewKartno = (TextView) lay.findViewById(R.id.textView8) ;


        kartlist = (ListView) lay.findViewById(R.id.lstView);

        adp = new OzelAdapter(kartbilgilerArrayList, context);
        kartlist.setAdapter(adp);

        kartbilgilerArrayList.clear();
        kartListDoldur();
        adSoyadAl();
        if (kartbilgilerArrayList.isEmpty() != true && listItem == 0) {
            kartSec();
        }
        else if( kartbilgilerArrayList.isEmpty() != true) kartSec();


        kartekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialogKartekle();
                Intent kartEkleIntent = new Intent(context, KartEkleActivity.class);
                kartEkleIntent.putExtra("kimNo", kim_no);
                startActivityForResult(kartEkleIntent, 1);

                kartbilgilerArrayList.clear();
                kartListDoldur();


            }
        });

        kartsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kartbilgilerArrayList.isEmpty()==true)
                    Toast.makeText(context,"Kart listeniz Boş",Toast.LENGTH_SHORT).show();
                  else  kartSilDialog();



            }
        });
        kartlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                listItem = i;

                kartSec();

            }
        });


        return lay;
    }


    private void kartSec() {
        if (kartbilgilerArrayList.get(listItem).getKart_turu().equals("Maximum"))
            imageId = R.drawable.maximum;
        else if (kartbilgilerArrayList.get(listItem).getKart_turu().equals("Gold"))
            imageId = R.drawable.gold;
        else if (kartbilgilerArrayList.get(listItem).getKart_turu().equals("Bonus"))
            imageId = R.drawable.bonus;

        foto.setImageResource(imageId);
        getArguments().putLong("kartNo",kartbilgilerArrayList.get(listItem).getKart_no());
        if (listItemeski != listItem)
        getArguments().putBoolean("update",true);
        bakiye = "Bakiye : " + String.valueOf(kartbilgilerArrayList.get(listItem).getBakiye());
        textBakiye.setText(bakiye);

        kartNo = " Kart no : " + String.valueOf(kartbilgilerArrayList.get(listItem).getKart_no());
        textViewKartno.setText(kartNo);

        listItemeski=listItem;
    }

    private void kartSilDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Uyarı");
        builder.setMessage("Bu kartı Silmek istiyormusun ?");
        builder.setPositiveButton("evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                kartSil();
            }
        });

        builder.setNegativeButton("hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void kartSil()
    {


        final Database database = new Database(context.getApplicationContext());
        final SQLiteDatabase db = database.getWritableDatabase();

        String selection = "kart_no" + " = ?";

        String[] selectionArgs = { String.valueOf(kartbilgilerArrayList.get(listItem).getKart_no())  };

       if( db.delete("kart_bilgiler", selection, selectionArgs)>0)
       {
           Toast.makeText(context,"kart silindi",Toast.LENGTH_SHORT).show();
           kartbilgilerArrayList.clear();
           kartListDoldur();

           if(kartbilgilerArrayList.isEmpty()==true)
           {
               textViewKartno.setText("");
               foto.setImageResource(0);
               textBakiye.setText("");
               getArguments().putLong("kartNo",0);
               getArguments().putBoolean("update",true);


           }

       }

        else
           Toast.makeText(context,"kart silinmedi",Toast.LENGTH_SHORT).show();

        db.close();
        database.close();
    }
    private void  adSoyadAl()
    {
        final Database database = new Database(context.getApplicationContext());
        final SQLiteDatabase db = database.getWritableDatabase();

        String[] projection = {
                "ad","soyad"
        };


        String selection ="kimlik_no = ? ";
        String[] selectionArgs = {kim_no};


        Cursor cursor = db.query(
                "kulanici_bilgiler",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if (cursor.getCount()>=1)
        {
            while(cursor.moveToNext()) {
                adSoyad = "Ad Soyad : " + cursor.getString(0) +" "+cursor.getString(1);
                textAdSoyad.setText(adSoyad);

            }
        }



        cursor.close();
        db.close();
        database.close();

    }
   private void kartListDoldur()
   {
       final Database database = new Database(context.getApplicationContext());
       final SQLiteDatabase db = database.getWritableDatabase();

       String[] projection = {
               "kart_no","cvv","kart_turu","kimlik_no","kart_bakiye"
       };


       String selection ="kimlik_no = ? ";
       String[] selectionArgs = {kim_no};


       Cursor cursor = db.query(
               "kart_bilgiler",                     // The table to query
               projection,                               // The columns to return
               selection,                                // The columns for the WHERE clause
               selectionArgs,                            // The values for the WHERE clause
               null,                                     // don't group the rows
               null,                                     // don't filter by row groups
               null                                 // The sort order
       );


       if (cursor.getCount()>=1)
       {
           while(cursor.moveToNext()) {
               kartbilgilerArrayList.add(new Kartbilgiler(cursor.getLong(0),cursor.getInt(1),cursor.getString(2),cursor.getLong(3),cursor.getInt(4)));


           }
       }

       adp.notifyDataSetChanged();
       cursor.close();
       db.close();
       database.close();
   }
}

class OzelAdapter extends BaseAdapter
{
    private ArrayList<Kartbilgiler> okartbilgiler;
    private LayoutInflater olayoutInflate;

    @Override
    public int getCount() {
        return okartbilgiler.size();
    }

    @Override
    public Kartbilgiler getItem(int i) {
        return okartbilgiler.get(i);
    }

    public OzelAdapter(ArrayList<Kartbilgiler> okartbilgiler, Context context) {
        this.okartbilgiler = okartbilgiler;
        this.olayoutInflate = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public long getItemId(int i) {
        return okartbilgiler.get(i).getKart_no();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View satırview;

        satırview =olayoutInflate.inflate(R.layout.listview_kartbilgiler,null);
        TextView kartno=(TextView)satırview.findViewById(R.id.textViewkartno);
        TextView karttur=(TextView)satırview.findViewById(R.id.textViewKarttur);
        TextView kartCvv=(TextView)satırview.findViewById(R.id.textViewCvv);
        kartno.setText(String.valueOf(okartbilgiler.get(i).getKart_no()));
        karttur.setText(okartbilgiler.get(i).getKart_turu());
        kartCvv.setText(String.valueOf(okartbilgiler.get(i).getCvv()));


        return satırview;
    }
}

class Kartbilgiler
{
    private  long kart_no;
    private int cvv;
    private int bakiye;
    private  long kimlikno;



    private String kart_turu;

    public String getKart_turu() {
        return kart_turu;
    }

    public void setKart_turu(String kart_turu) {
        this.kart_turu = kart_turu;
    }

    public Kartbilgiler(long kart_no, int cvv, String kart_turu, long kimlikno, int bakiye) {
        this.kart_no = kart_no;
        this.cvv = cvv;
        this.bakiye = bakiye;
        this.kimlikno = kimlikno;
        this.kart_turu=kart_turu;

    }

    public long getKart_no() {
        return kart_no;
    }

    public void setKart_no(long kart_no) {
        this.kart_no = kart_no;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }

    public long getKimlikno() {
        return kimlikno;
    }

    public void setKimlikno(long kimlikno) {
        this.kimlikno = kimlikno;
    }


}