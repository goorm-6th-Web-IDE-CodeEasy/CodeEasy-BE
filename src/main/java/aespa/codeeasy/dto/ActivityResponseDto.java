package aespa.codeeasy.dto;

public class ActivityResponseDto {
    private String date;
    private boolean activityPresent;

    public ActivityResponseDto(String date, boolean activityPresent) {
        this.date = date;
        this.activityPresent = activityPresent;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public boolean isActivityPresent() {
        return activityPresent;
    }
}
