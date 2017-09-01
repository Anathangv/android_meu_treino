package gv.anathan.meutreino.Classes;

public class WorkoutLog {

    private String id;
    private String dateBegin;
    private String dateEnd;
    private String note;


    public WorkoutLog(String id, String dateBegin, String dateEnd, String note){
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.note = note;
    }

    public String getdateBegin() {
        return dateBegin;
    }

    public void setdateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getdateEnd() {
        return dateEnd;
    }

    public void setdateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
