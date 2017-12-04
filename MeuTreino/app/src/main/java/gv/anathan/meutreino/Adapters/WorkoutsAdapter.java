package gv.anathan.meutreino.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gv.anathan.meutreino.Classes.Workout;
import gv.anathan.meutreino.R;

public class WorkoutsAdapter extends ArrayAdapter {

    private Context context;
    private List<Workout> workoutList = new ArrayList<Workout>();
    private Button btEditWorkout;

    public WorkoutsAdapter(Context context, int resource, List<Workout> workoutList) {
        super(context, resource, workoutList);

        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View layout;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.workout_list_iten, null);
        }else{
            layout = convertView;
        }

        final TextView tvWorkoutLetter = (TextView) layout.findViewById(R.id.tvWorkoutLetter);
        tvWorkoutLetter.setText(workoutList.get(position).getWorkoutLetter());

        final TextView tvWorkoutDescription = (TextView) layout.findViewById(R.id.textViewModuleDescription);
        tvWorkoutDescription.setText("teste");

        btEditWorkout = (Button) layout.findViewById(R.id.buttonEditModule);
        btEditWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_workout, null);

                /*
                final CheckBox checkBoxChest = (CheckBox) view.findViewById(R.id.checkBoxChest);
                final CheckBox checkBoxTriceps = (CheckBox) view.findViewById(R.id.checkBoxTriceps);
                final CheckBox checkBoxBack = (CheckBox) view.findViewById(R.id.checkBoxBack);
                final CheckBox checkBoxBicpse = (CheckBox) view.findViewById(R.id.checkBoxBicpse);
                final CheckBox checkBoxLeg = (CheckBox) view.findViewById(R.id.checkBoxLeg);
                */

                builder.setView(view);
                dialog = builder.create();
                dialog.show();

            }
        });

        return layout;
    }
}
