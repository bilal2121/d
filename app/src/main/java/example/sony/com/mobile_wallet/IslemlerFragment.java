package example.sony.com.mobile_wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SONY on 1/9/2017.
 */

public class IslemlerFragment extends Fragment {

    private long kartNo=0;
    private boolean update;
    private ListView listView;
    private Context context;
    private ParalistAdapter adapter;
    private ArrayList<Paraislemleri> islemlerArray = new ArrayList<Paraislemleri>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.fragment_islemler,container,false);
        kartNo= getArguments().getLong("kartNo");

        listView = (ListView) lay.findViewById(R.id.listVislem);
        adapter =new ParalistAdapter(islemlerArray,context);

        listView.setAdapter(adapter);
        //Toast.makeText(context,String.valueOf(kartNo),Toast.LENGTH_SHORT).show();
        Log.i("Bilgi", "islemler OnCrateView");




        return lay;
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i("Bilgi", "islemler Onresume");
        kartNo= getArguments().getLong("kartNo");
        update= getArguments().getBoolean("update");
//        Toast.makeText(context,String.valueOf(kartNo),Toast.LENGTH_SHORT).show();
        if( update == true)
        {
            /*listeEkle();
            listeEkle();
            listeEkle();*/
            islemlerArray.clear();
            adapter.notifyDataSetChanged();
            listeDoldur();
            update=false;
            getArguments().putBoolean("update",false);
        }
        //listeDoldur();

    }

    private void listeDoldur()
    {

            final Database database = new Database(context.getApplicationContext());
            final SQLiteDatabase db = database.getWritableDatabase();

            String[] projection = {
                    "a_kart_no","g_kart_no","miktar",
            };


            String selection ="a_kart_no = ? ";
            String[] selectionArgs = {String.valueOf(kartNo)};


            Cursor cursor = db.query(
                    "para_islemleri",                     // The table to query
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
                    islemlerArray.add(new Paraislemleri(cursor.getLong(0),cursor.getLong(1),cursor.getInt(2)));


                }
              //  Toast.makeText(context,"okundu",Toast.LENGTH_SHORT).show();
            }
        //else Toast.makeText(context,"okunmuyor",Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();
            cursor.close();
            db.close();
            database.close();
        }

    private void listeEkle()
    {
        final Database database = new Database(context.getApplicationContext());
        final SQLiteDatabase db = database.getWritableDatabase();
         String gonderen_no ="5478965235874125";

        ContentValues values = new ContentValues();
        values.put("a_kart_no", kartNo);

        values.put("g_kart_no", Long.parseLong(gonderen_no));
        values.put("miktar", 50);


        long RowId = db.insert("para_islemleri", null, values);
        if (RowId!=-1)
        {
            Toast.makeText(context,"insert edildi",Toast.LENGTH_SHORT).show();


        }
        else
            Toast.makeText(context,"insert edilemedi",Toast.LENGTH_SHORT).show();


        db.close();
        database.close();
    }

}



class ParalistAdapter extends BaseAdapter
{
    private ArrayList<Paraislemleri> paraislemleri;
    private LayoutInflater olayoutInflate;

    @Override
    public int getCount() {
        return paraislemleri.size();
    }

    @Override
    public Paraislemleri getItem(int i) {
        return paraislemleri.get(i);
    }

    public ParalistAdapter(ArrayList<Paraislemleri> paraislemleri, Context context) {
        this.paraislemleri = paraislemleri;
        this.olayoutInflate = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public long getItemId(int i) {
        return paraislemleri.get(i).getA_kart_no();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View satırview;

        satırview =olayoutInflate.inflate(R.layout.listview_para,null);
        TextView a_kartno=(TextView)satırview.findViewById(R.id.tViewParaAlan);
        TextView g_kartno=(TextView)satırview.findViewById(R.id.tViewParagon);
        TextView miktar=(TextView)satırview.findViewById(R.id.tViewMiktar);
        a_kartno.setText(String.valueOf(paraislemleri.get(i).getA_kart_no()));
        g_kartno.setText(String.valueOf(paraislemleri.get(i).getG_kart_no()));
        miktar.setText(String.valueOf(paraislemleri.get(i).getMiktar()));


        return satırview;
    }
}

class Paraislemleri
{
    private  long a_kart_no;
    private long g_kart_no;
    private int miktar;

    public Paraislemleri(long a_kart_no, long g_kart_no, int miktar) {
        this.a_kart_no = a_kart_no;
        this.g_kart_no = g_kart_no;
        this.miktar = miktar;
    }

    public long getA_kart_no() {
        return a_kart_no;
    }

    public void setA_kart_no(long a_kart_no) {
        this.a_kart_no = a_kart_no;
    }

    public long getG_kart_no() {
        return g_kart_no;
    }

    public void setG_kart_no(long g_kart_no) {
        this.g_kart_no = g_kart_no;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
}