package gv.anathan.meutreino.Classes;

public class Workout {

    private String id;
    private String workoutLogId;
    private String workoutLetter;
    private boolean neck;
    private boolean trapeze;
    private boolean shoulders;
    private boolean back;
    private boolean chest;
    private boolean biceps;
    private boolean forearm;
    private boolean abs;
    private boolean glutes;
    private boolean thighs;
    private boolean calves;
    private boolean cardio;

    public String getWorkoutLetter() {
        return workoutLetter;
    }

    public void setWorkoutLetter(String workoutLetter) {
        this.workoutLetter = workoutLetter;
    }

    public String getWorkoutLogId() {
        return workoutLogId;
    }

    public void setWorkoutLogId(String workoutLog) {
        this.workoutLogId = workoutLog;
    }

    public boolean isNeck() {
        return neck;
    }

    public void setNeck(boolean neck) {
        this.neck = neck;
    }

    public boolean isTrapeze() {
        return trapeze;
    }

    public void setTrapeze(boolean trapeze) {
        this.trapeze = trapeze;
    }

    public boolean isShoulders() {
        return shoulders;
    }

    public void setShoulders(boolean shoulders) {
        this.shoulders = shoulders;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isChest() {
        return chest;
    }

    public void setChest(boolean chest) {
        this.chest = chest;
    }

    public boolean isBiceps() {
        return biceps;
    }

    public void setBiceps(boolean biceps) {
        this.biceps = biceps;
    }

    public boolean isForearm() {
        return forearm;
    }

    public void setForearm(boolean forearm) {
        this.forearm = forearm;
    }

    public boolean isAbs() {
        return abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
    }

    public boolean isGlutes() {
        return glutes;
    }

    public void setGlutes(boolean glutes) {
        this.glutes = glutes;
    }

    public boolean isThighs() {
        return thighs;
    }

    public void setThighs(boolean thighs) {
        this.thighs = thighs;
    }

    public boolean isCalves() {
        return calves;
    }

    public void setCalves(boolean calves) {
        this.calves = calves;
    }

    public boolean isCardio() {
        return cardio;
    }

    public void setCardio(boolean cardio) {
        this.cardio = cardio;
    }
}
