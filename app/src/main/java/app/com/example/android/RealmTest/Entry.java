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
    private double averagePain;
    private String sleepTime;
    private double sleepLength;
    private int energyLvl;
    private int stressLvl;

    public Entry() {}


    //getters and setters
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

    public double getAveragePain() { return averagePain; }

    public void setAveragePain(double averagePain){ this.averagePain = averagePain; }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime){ this.sleepTime = sleepTime; }

    public double getSleepLength() {
        return sleepLength;
    }

    public void setSleepLength(double sleepLength){
        this.sleepLength = sleepLength;
    }

    public int getEnergyLvl() { return energyLvl; }

    public void setEnergyLvl(int energyLvl){ this.energyLvl = energyLvl; }

    public int getStressLvl() { return stressLvl; }

    public void setStressLvl(int stressLvl){ this.stressLvl = stressLvl; }


}

