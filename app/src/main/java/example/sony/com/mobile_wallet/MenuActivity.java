package example.sony.com.mobile_wallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private KartlarFragment kartlar;
    private IslemlerFragment islemler;
    private ParaislemleriFragment paraislemleri ;
    private Bundle bundle;

    private void dialogcik()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Uyarı");
        builder.setMessage("Çıkmak istiyormusun ?");
        builder.setPositiveButton("evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
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

    @Override
    public void onBackPressed() {

        dialogcik();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        bundle = new Bundle();
        bundle.putString("kim_no",getIntent().getStringExtra("kim_no"));
        bundle.putLong("kartNo",0);
        bundle.putBoolean("update",true);



        //Intent intent=getIntent();

        /*kartlar=new KartlarFragment();
        kartlar.setArguments(bundle);
        islemler= new IslemlerFragment();*/
        paraislemleri =new ParaislemleriFragment();
       // Bundle bundle =new Bundle();
       // bundle.putString("kim_no",intent.getStringExtra("kim_no"));
       // kartlar.setArguments(bundle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                    islemler.onResume();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }







    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0)
            {
                kartlar=new KartlarFragment();
                kartlar.setArguments(bundle);
                return kartlar;
            }
            if(position==1)
            {
                islemler= new IslemlerFragment();
                islemler.setArguments(bundle);
                return  islemler;
            }


            if(position==2)
                return  paraislemleri;
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Kart İşlemleri";
                case 1:
                    return "İşlemler";
                case 2:
                    return "Para işlemleri";
            }
            return null;
        }
    }
}
