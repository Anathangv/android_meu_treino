package gv.anathan.meutreino;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import gv.anathan.meutreino.Adapters.WorkoutsAdapter;
import gv.anathan.meutreino.Classes.Utility;
import gv.anathan.meutreino.Classes.Workout;

public class Workouts extends AppCompatActivity {

    private String workoutlogId;
    private String timeInterval;
    private AlertDialog dialog;
    private ArrayList<Workout> workoutList;
    private ListView lvWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        workoutList = new ArrayList<Workout>();
        lvWorkout = (ListView) findViewById(R.id.lvWorkout);

        Intent intentMain = getIntent();
        workoutlogId = intentMain.getStringExtra("idParameter");
        timeInterval = intentMain.getStringExtra("beginDateParameter");
        setTitle(timeInterval + " - ");

        if (workoutlogId != null){

            loadWorkouts();
        }
    }


    //loads all the workouts of a workout log
    public void loadWorkouts(){

        workoutList = new ArrayList<Workout>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Workouts");
        query.whereEqualTo("workoutLogId",workoutlogId);
        query.orderByAscending("workoutLetter");

        Utility.showLoading(this);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    if(objects.size() > 0){

                        for(ParseObject parseWorkouts : objects){

                            Workout workout = new Workout();
                            workout.setWorkoutLogId(parseWorkouts.getString("workoutLogId"));
                            workout.setWorkoutLetter(parseWorkouts.getString("workoutLetter"));

                            workoutList.add(workout);
                        }

                        //mount list when all the workouts have been listed
                        if(workoutList.size() == objects.size()){

                            lvWorkout.setAdapter(new WorkoutsAdapter(Workouts.this,R.layout.dialog_workout,workoutList));
                        }
                    }
                }else{

                    Toast.makeText(getApplicationContext(),"Error in retrieving training sheets modules",Toast.LENGTH_LONG).show();
                }
            }
        });
        Utility.dismissLoading();
    }


    //creates or updates a workout
    public void createUpdateWorkout(final View v){

        final AlertDialog.Builder builder = new AlertDialog.Builder(Workouts.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_workout, null);

        Button btSaveWorkout = (Button) view.findViewById(R.id.btSaveWorkout);

        btSaveWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Utility.showLoading(v.getContext());

                //save workout
                ParseObject object = new ParseObject("Workouts");
                object.put("workoutLogId", workoutlogId);
                object.put("workoutLetter", getNextWorkoutLetter());
                object.put("neck", false);
                object.put("trapeze", false);
                object.put("shoulders", false);
                object.put("back", false);
                object.put("chest", false);
                object.put("biceps", false);
                object.put("forearm", false);
                object.put("abs", false);
                object.put("glutes", false);
                object.put("thighs", false);
                object.put("calves", false);
                object.put("cardio", false);

                object.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {

                        if (e == null) {
                            Utility.dismissLoading();
                            Toast.makeText(getApplicationContext(), "Treino Criado", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            loadWorkouts();

                        } else {
                            Utility.dismissLoading();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error in saving data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }


    //gets the next letter that represents the workout
    public String getNextWorkoutLetter(){

        String currentLetter;
        int charValue;
        String nextLetter;

        if (workoutList.size() > 0){

            currentLetter = workoutList.get(workoutList.size() - 1).getWorkoutLetter();
            charValue = currentLetter.charAt(0);
            nextLetter = String.valueOf((char) (charValue + 1));
        }else{

            nextLetter = "A";
        }

        return nextLetter;
    }

}
