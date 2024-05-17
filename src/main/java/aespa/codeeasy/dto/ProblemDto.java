package aespa.codeeasy.dto;

public class ProblemDto {
    private Long id;
    private String title;
    private String difficulty;
    private boolean isDone;

    public ProblemDto(Long id, String title, String difficulty, boolean isDone) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.isDone = isDone;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}