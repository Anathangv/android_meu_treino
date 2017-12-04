package gv.anathan.meutreino.Adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gv.anathan.meutreino.Classes.WorkoutLog;
import gv.anathan.meutreino.R;

public class WorkoutLogsAdapter extends ArrayAdapter{

    private Context context;
    private List<WorkoutLog> workoutLogList = new ArrayList<WorkoutLog>();
    private Button btEditWorkoutLog;
    private Calendar calendarBeginDate = Calendar.getInstance();

    public WorkoutLogsAdapter(@NonNull Context context, @LayoutRes int resource, List<WorkoutLog> workoutLogList) {

        super(context, resource, workoutLogList);

        this.context = context;
        this.workoutLogList = workoutLogList;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final View layout;

        //creates each item of the list with my layout
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.workout_log_listview_item, null);
        }else{
            layout = convertView;
        }

        //shows the workout log number
        final TextView tvWorkoutLogNumber = (TextView) layout.findViewById(R.id.tvWorkoutLogNumber);
        tvWorkoutLogNumber.setText(String.valueOf(workoutLogList.size() - position));

        //shows the begin date
        final TextView tvBeginDate = (TextView) layout.findViewById(R.id.tvBeginDate);
        tvBeginDate.setText(workoutLogList.get(position).getdateBegin());

        //set event of return to the button
        btEditWorkoutLog = (Button) layout.findViewById(R.id.btEditWorkoutLog);
        btEditWorkoutLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_workout_log, null);

                builder.setView(view);
                dialog = builder.create();

                //reference dialog components
                final TextView tvWorkoutLogNumberDialog = (TextView) view.findViewById(R.id.tvWorkoutLogNumberDialog);
                final EditText etNote = (EditText) view.findViewById(R.id.etNote);
                final EditText etDateBegin = (EditText) view.findViewById(R.id.etBegin);
                Button btSaveWorkoutLog = (Button) view.findViewById(R.id.btSaveWorkoutLog);

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


                tvWorkoutLogNumberDialog.setText( tvWorkoutLogNumber.getText().toString());
                etDateBegin.setText(tvBeginDate.getText().toString());

                //save changes
                btSaveWorkoutLog.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("WorkoutLogs");
                        query.getInBackground(workoutLogList.get(position).getId(), new GetCallback<ParseObject>() {

                            @Override
                            public void done(ParseObject object, ParseException e) {

                                if (e == null) {

                                    object.put("beginDate", etDateBegin.getText().toString());
                                    object.put("note", etNote.getText().toString());
                                    object.saveInBackground();

                                    workoutLogList.get(position).setdateBegin(etDateBegin.getText().toString());

                                    Toast.makeText(context, "Treino atualizado", Toast.LENGTH_LONG).show();

                                    //closes dialog after the updating workout log
                                    dialog.dismiss();

                                    //refresh the workout logs list
                                    notifyDataSetChanged();

                                } else {

                                    dialog.dismiss();

                                    Toast.makeText(context, "Não foi possível atualizar", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        return layout;
    }

}
