package app.com.example.android.RealmTest;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ohthe on 4/26/2016.
 */
public class Entry extends RealmObject {

    @PrimaryKey
    private String entryDate;

    private int painMorn;
    private int painMid;
    private int painNight;
    private String sleepTime;
    private double sleepLength;

    public Entry() {}

    public String getEntryDate() { return entryDate; }

    public void setEntryDate(String entryDate){ this.entryDate = entryDate; }

    public int getPainMorn() { return painMorn; }

    public void setPainMorn(int painMorn){
        this.painMorn = painMorn;
    }

    public int getPainMid() {
        return painMid;
    }

    public void setPainMid(int painMid){ this.painMid = painMid; }

    public int getPainNight() {
        return painNight;
    }

    public void setPainNight(int painNight){
        this.painNight = painNight;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime){
        this.sleepTime = sleepTime;
    }

    public double getSleepLength() {
        return sleepLength;
    }

    public void setSleepLength(double sleepLength){
        this.sleepLength = sleepLength;
    }

}
