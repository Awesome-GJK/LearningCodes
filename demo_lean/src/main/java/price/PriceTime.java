package main.java.price;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2025-01-24 15:50
 **/

public class PriceTime {
    private int startTimeCode;
    private int endTimeCode;
    private String startTime;
    private String endTime;
    private boolean isTimeRemaining;
    private int type;


    public PriceTime(int startTimeCode, int endTimeCode, String startTime, String endTime, boolean isTimeRemaining, int type) {
        this.startTimeCode = startTimeCode;
        this.endTimeCode = endTimeCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isTimeRemaining = isTimeRemaining;
        this.type = type;
    }

    public int getStartTimeCode() {
        return startTimeCode;
    }

    public void setStartTimeCode(int startTimeCode) {
        this.startTimeCode = startTimeCode;
    }

    public int getEndTimeCode() {
        return endTimeCode;
    }

    public void setEndTimeCode(int endTimeCode) {
        this.endTimeCode = endTimeCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isTimeRemaining() {
        return isTimeRemaining;
    }

    public void setTimeRemaining(boolean timeRemaining) {
        isTimeRemaining = timeRemaining;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
