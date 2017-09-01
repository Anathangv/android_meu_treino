package gv.anathan.meutreino;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gv.anathan.meutreino.Adapters.WorkoutLogsAdapter;
import gv.anathan.meutreino.Classes.Utility;
import gv.anathan.meutreino.Classes.WorkoutLog;

public class Main extends AppCompatActivity {

    private List<WorkoutLog> workoutLogList;
    private ListView lvWorkoutLog;
    private Calendar calendarBeginDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createWorkoutLog();

            }
        });

        workoutLogList = new ArrayList<WorkoutLog>();
        lvWorkoutLog = (ListView) findViewById(R.id.lvWorkoutLog);

        loadWorkoutLogList();
    }


    //displays all user's the workout logs
    public void loadWorkoutLogList(){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("WorkoutLogs");
        query.whereEqualTo("userId",ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending("createdAt");

        Utility.showLoading(this);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                int workoutLogListCount = 0;

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject parseWorkouLog : objects) {

                            workoutLogListCount += 1;

                            WorkoutLog workoutLog = new WorkoutLog(parseWorkouLog.getObjectId(),
                                    parseWorkouLog.getString("beginDate"),
                                    parseWorkouLog.getString("endDate"),
                                    parseWorkouLog.getString("note"));
                            workoutLogList.add(workoutLog);

                            //mount the listView only when all the workout logs are in the list
                            if (workoutLogListCount == objects.size()) {

                                WorkoutLogsAdapter workoutLogsAdapter = new WorkoutLogsAdapter(Main.this, R.layout.workout_log_listview_item, workoutLogList);
                                lvWorkoutLog.setAdapter(workoutLogsAdapter);

                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Erro ao carregar fichas",Toast.LENGTH_LONG).show();
                }

                Utility.dismissLoading();
            }
        });
    }



    public void createWorkoutLog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_workout_log, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        Button btSaveWorkoutLog = (Button) view.findViewById(R.id.btSaveWorkoutLog);
        final EditText etNote = (EditText) view.findViewById(R.id.etNote);
        final EditText etDateBegin = (EditText) view.findViewById(R.id.etBegin);

        //creates datepicker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendarBeginDate.set(Calendar.YEAR, year);
                calendarBeginDate.set(Calendar.MONTH, month);
                calendarBeginDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateformat  =  "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat, Locale.getDefault());
                etDateBegin.setText(simpleDateFormat.format(calendarBeginDate.getTime()));
            }
        };

        //set datepicker in the editText
        etDateBegin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(v.getContext(), date, calendarBeginDate.get(Calendar.YEAR),
                                                 calendarBeginDate.get(Calendar.MONTH),
                                                 calendarBeginDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btSaveWorkoutLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //save workout log
                ParseObject object = new ParseObject("WorkoutLogs");
                object.put("userId", ParseUser.getCurrentUser().getObjectId());
                object.put("beginDate", etDateBegin.getText().toString());
                object.put("endDate","");
                object.put("note", etNote.getText().toString());

                object.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            //move to the next intent
                            //Intent i = new Intent(getApplicationContext(),TrainingSheetModules.class);
                            //i.putExtra("beginDateParameter", etDateBegin.getText().toString());
                            //i.putExtra("noteParameter", etNote.getText().toString());
                            //startActivity(i);
                            //Toast.makeText(getApplicationContext(), "Created",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error in saving data",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}
