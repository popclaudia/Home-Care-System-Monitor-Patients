package ds.producer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityEntry {

    Integer patientId;
    Date startTime;
    Date endTime;
    String activity;

    public ActivityEntry(String startTime, String endTime, String activity) {
        this.patientId= 4;
        this.startTime = this.stringToDate(startTime);
        this.endTime = this.stringToDate(endTime);
        this.activity = activity;
    }

    public Date stringToDate(String d){
        Date date = new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date=formatter.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "patient_id: " + patientId +
                "\nactivity: " + activity +
                "\nstart: " + startTime +
                "\nend: " + endTime +
                "\n";
    }




}
