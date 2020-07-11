package my.springbliss.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements  NavigationView
        .OnNavigationItemSelectedListener{
    GridView gridView;
    ViewPager viewPager;
NotificationBadge badge;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    List<Model> list;
    CustomAdapter customAdapter;
    String[] fruitNames = {"SANITIZER","HANDWASH"};
    SharedPreferences shared;
    int[] fruitImages = {R.drawable.inventive,R.drawable.inventive};
   // int[] fruitImages = {R.drawable.sanitizer,R.drawable.handwash1};
    //int[] fruitImages = {R.drawable.geo11,R.drawable.geo9,R.drawable.geo9,R.drawable.geo9,R.drawable.geo9,R.drawable.geo9,R.drawable.geo9,R.drawable.geo9,R.drawable.colgate,R.drawable.shabun,R.drawable.pooja,R.drawable.vim,R.drawable.garnier,R.drawable.mortine};
Myhelper myhelper;
int count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);
        shared = this.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        String user_name=shared.getString("name","");
        String user_mobile=shared.getString("mobile","");
        updateToken();

        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");

        mDrawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername =  headerView.findViewById(R.id.headername);
        TextView navUsermobile = headerView.findViewById(R.id.headernumber);
        ImageView userImage=headerView.findViewById(R.id.UserImageProfile);
        userImage.setImageResource(R.drawable.springblisslogo);
        navUsername.setText(user_name);
        navUsermobile.setText(user_mobile);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        viewPager=findViewById(R.id.view_pager);
        PackagesAdapter packagesAdapter=new PackagesAdapter(this);
        viewPager.setAdapter(packagesAdapter);

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),3000,3000);


        myhelper=new Myhelper(this);
        SQLiteDatabase database1 = myhelper.getReadableDatabase();
        String sql1 = "select * from SPRINGBLISS";
        Cursor c1 = database1.rawQuery(sql1,null);
        while(c1.moveToNext()){
            count1++;
        }


        list=new ArrayList<>();
        for(int i=0;i<fruitNames.length;i++){
            list.add(new Model(fruitNames[i],fruitImages[i]));

        }
        gridView = findViewById(R.id.gridview);

        customAdapter = new CustomAdapter(this,list);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {
                        Intent intent = new Intent(getApplicationContext(), Sanitizer.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 1: {
                        Intent intent1 = new Intent(getApplicationContext(), HandWash.class);
                        startActivity(intent1);
                        finish();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_cart,menu);

        MenuItem menuItem1=menu.findItem(R.id.cart1);
        menuItem1.setIcon(Converter.convertLayoutToImage(MainActivity.this,count1,R.drawable.ic_shopping_cart));

        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
                finish();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    public long getProfilesCount() {
        Myhelper myhelper=new Myhelper(this);
        SQLiteDatabase db = myhelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "SPRINGBLISS");
        db.close();
        return count;
    }
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            //super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                finish();
                break;
            case R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.nav_website:
            {
                Uri webpage=Uri.parse("https://springbliss.in/");
                Intent intent=new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(intent);
                break;
            }
            case R.id.nav_howto:
            {
                Intent intent=new Intent(MainActivity.this,HowToUse.class);
                startActivity(intent);
                break;
            }

            case R.id.nav_aboutus:
                Intent intent1=new Intent(MainActivity.this,AboutUs.class);
                startActivity(intent1);
                break;
            case R.id.myorder: {
                SharedPreferences prefs = this.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
                String loginID = prefs.getString("email", "");
                String loginPWD = prefs.getString("password", "");

                if (loginID.length() > 0 && loginPWD.length() > 0) {
                    Intent intent = new Intent(MainActivity.this, MyorderActivity.class);
                    startActivity(intent);
                }
                else {
                    //SHOW PROMPT FOR LOGIN DETAILS
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Please Login To Continue");
                    alertDialogBuilder.setPositiveButton("Login",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            });

                    alertDialogBuilder.setNegativeButton("Sign-Up",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
            }
            case R.id.nav_logout:
            {
                SharedPreferences prefs = this.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", "");     //RESET TO DEFAULT VALUE
                editor.putString("password", "");     //RESET TO DEFAULT VALUE
                editor.commit();
                Toast.makeText(this, "Succefully Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updateToken()
    {
        if(shared.getString("token","0").equals("0") || !shared.getString("token","0").equals(FirebaseInstanceId.getInstance().getToken()))
        {
            final String seller_id=shared.getString("id","");
            final ProgressDialog pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Customer Choice");
            pd.setMessage("Please Wait...");
            pd.show();
            String referral;
            String url = "https://digitalcafe.us/springbliss/updateToken.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    pd.dismiss();
                    if(response.trim().equals("token updated"))
                    {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("token",FirebaseInstanceId.getInstance().getToken());
                        editor.commit();
                        Toast.makeText(MainActivity.this, "Token Has Been Updated", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("id",seller_id);
                    map.put("token",FirebaseInstanceId.getInstance().getToken());
                    map.put("table","UserRegistration");
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this, new HurlStack());
            requestQueue.add(stringRequest);
        }
    }
    private class CustomAdapter extends BaseAdapter implements Filterable {

        Context context;
        List<Model> modelList;
        List<Model> modelListFilter;

        public CustomAdapter(Context context, List<Model> modelList) {
            this.context = context;
            this.modelList = modelList;
            this.modelListFilter = modelList;
        }

        @Override
        public int getCount() {
            return modelListFilter.size();
        }

        @Override
        public Object getItem(int i) {
            return modelListFilter.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1 = getLayoutInflater().inflate(R.layout.custome_item,null);
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(modelListFilter.get(i).getName());
            image.setImageResource(modelListFilter.get(i).getImage());
            return view1;
        }

        @Override
        public Filter getFilter() {
            Filter filter=new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults=new FilterResults();
                    if(charSequence==null || charSequence.length()==0){
                        filterResults.count=modelList.size();
                        filterResults.values=modelList;
                    }
                    else{
                        String serachStr=charSequence.toString().toUpperCase();
                        List<Model> resultData=new ArrayList<>();
                        for(Model model:modelList){
                            if(model.getName().toUpperCase().contains(serachStr)){
                                resultData.add(model);
                            }
                            filterResults.count=resultData.size();
                            filterResults.values=resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {
                    modelListFilter=(List<Model>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }


    }
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }
                    else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }
                    else if(viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    } else if(viewPager.getCurrentItem()==3){
                        viewPager.setCurrentItem(4);
                    }
                    else if(viewPager.getCurrentItem()==4){
                        viewPager.setCurrentItem(5);
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }

    }

}