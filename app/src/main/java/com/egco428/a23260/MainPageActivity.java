package com.egco428.a23260;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private MapsDataSource datasource;
    ArrayAdapter<Map> UserArrayAdapter;

    ListView listView;

    TextView userNameListText;

    Double passLatitude, passLongtitude;

    String passUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        listView = (ListView) findViewById(R.id.userList_listView);

        datasource = new MapsDataSource(this);
        datasource.read();
        final List<Map> values = datasource.getAllMap();
        UserArrayAdapter = new UserArrayAdapter(this, 0, values);
        listView.setAdapter(UserArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long l) {
                if(UserArrayAdapter.getCount() > 0){
                    final Map map = values.get(position);
                    passLatitude = Double.parseDouble(map.getLatitude());
                    passLongtitude = Double.parseDouble(map.getLongtitude());
                    passUsername = map.getUsername();

                    final Intent passIntent = new Intent(MainPageActivity.this, LocationActivity.class);
                    Bundle pass = new Bundle();

                    pass.putDouble("LATITUDE", passLatitude);
                    pass.putDouble("LONGTITUDE", passLongtitude);
                    pass.putString("USERNAME", passUsername);

                    passIntent.putExtras(pass);

                    view.animate().setDuration(150).alpha(0).withEndAction(new Runnable() {
                        public void run() {
                            startActivity(passIntent);
                            view.setAlpha(1);
                        }
                    });
                }
            }
        });

    }

    class UserArrayAdapter extends ArrayAdapter<Map>{
        Context context;
        List<Map> objects;
        public UserArrayAdapter(Context context, int resource, List<Map> objects){
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }
        @Override public View getView(int position, View convertView, ViewGroup parent){
            Map map = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_userlist_layout, null);

            userNameListText = (TextView) view.findViewById(R.id.username_list);
            userNameListText.setText(map.getUsername());

            return view;
        }
    }

    public void backDialogToLogin(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Do you want to Log out");
        builder.setIcon(R.drawable.ic_error_outline_black_24dp);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent clearBackMain = new Intent(MainPageActivity.this, LoginActivity.class);
                clearBackMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(clearBackMain);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }


    @Override
    protected void onResume(){
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.userList_listView);

        datasource.open();
        List<Map> values = datasource.getAllMap();
        listView.setAdapter(UserArrayAdapter);
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
