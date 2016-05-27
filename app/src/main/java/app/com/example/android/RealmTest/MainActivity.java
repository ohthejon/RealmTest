package app.com.example.android.RealmTest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private int mHour, mMinute, mYear, mMonth, mDay;
    EditText pain1, pain2, pain3, sleepL, sleepT, entryDate;
    private Realm realm;
    private RealmChangeListener entryListener;
    private RealmConfiguration realmConfig;
    private Entry entry;
    private RecyclerView realmRecyclerView;

    final static int REQ_CODE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton addEntryBtn = (FloatingActionButton) findViewById(R.id.addEntryBtn);
        addEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAndShowInputDialog();
            }
        });

        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        resetRealm();
        realm = Realm.getInstance(realmConfig);
        realmRecyclerView = (RecyclerView) findViewById(R.id.realm_recycler_view);
        //OrderedRealmCollection<Entry> entries = realm.where(Entry.class).findAllAsync();
        //final EntryAdapter entryAdapter = new EntryAdapter(this, entries);
        realmRecyclerView.setAdapter(new EntryAdapter(this, realm.where(Entry.class).findAllAsync()));
        realmRecyclerView.setHasFixedSize(true);
        //realmRecyclerView.setAdapter(entryAdapter);

    }

    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Create an Entry");

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.entry_form, null);

        entryDate = (EditText) dialogView.findViewById(R.id.in_date);
        pain1 = (EditText) dialogView.findViewById(R.id.etPainMorn);
        pain2 = (EditText) dialogView.findViewById(R.id.etPainMid);
        pain3 = (EditText) dialogView.findViewById(R.id.etPainNight);
        sleepL = (EditText) dialogView.findViewById(R.id.etSleepLength);
        sleepT = (EditText) dialogView.findViewById(R.id.in_time);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addEntry(
                        entryDate.getText().toString(),
                        Integer.parseInt(pain1.getText().toString()),
                        Integer.parseInt(pain2.getText().toString()),
                        Integer.parseInt(pain3.getText().toString()),
                        Double.parseDouble(sleepL.getText().toString()),
                        sleepT.getText().toString()
                );
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        entryDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){
                if(event.getAction() == event.ACTION_UP) {
                    int inType = entryDate.getInputType();
                    entryDate.setInputType(InputType.TYPE_NULL);
                    entryDate.onTouchEvent(event);
                    entryDate.setInputType(inType);
                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog;

                    datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int year, int monthOfYear, int dayOfMonth) {
                            entryDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.show();
                }
                return true;
            }
        });
        sleepT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event){
                if(event.getAction() == event.ACTION_UP) {
                    int inType = sleepT.getInputType();
                    sleepT.setInputType(InputType.TYPE_NULL);
                    sleepT.onTouchEvent(event);
                    sleepT.setInputType(inType);
                    Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog;
                    timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            int hour = hourOfDay % 12;
                            sleepT.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    minute, hourOfDay < 12 ? "am" : "pm"));
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();
                }
                return true;
            }
        });

        builder.show();

    }


    public void addEntry(String entryDate, int pain1, int pain2, int pain3, Double sleepL, String sleepT) {

        int nonNullPainCount = 3;
        if(pain1 < 1){
            nonNullPainCount--;
        }
        if(pain2 < 1){
            nonNullPainCount--;
        }
        if(pain3 < 1){
            nonNullPainCount--;
        }

        Entry entry = new Entry();
        entry.setEntryDate(entryDate.toString());
        entry.setPainMorn(pain1);
        entry.setPainMid(pain2);
        entry.setPainNight(pain3);
        entry.setAveragePain((pain1+pain2+pain3)/nonNullPainCount);
        entry.setSleepLength(Double.parseDouble(sleepL.toString()));
        entry.setSleepTime(sleepT.toString());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(entry);
        realm.commitTransaction();

        OrderedRealmCollection<Entry> results = realm.where(Entry.class).findAll();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void resetRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(realmConfig);
    }


}
