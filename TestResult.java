import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.text.SimpleDateFormat;

public class TestResult implements Comparable<TestResult> {
    private Date testDate;
    private Map<PersonalityDimension, Integer> scores;
    private String userId;
    
    public TestResult(String userId) {
        this.userId = userId;
        this.testDate = new Date();
        this.scores = new EnumMap<>(PersonalityDimension.class);
    }
    
    public void setScore(PersonalityDimension dimension, int score) {
        scores.put(dimension, score);
    }
    
    public int getScore(PersonalityDimension dimension) {
        return scores.getOrDefault(dimension, 0);
    }
    
    public Date getTestDate() { return testDate; }
    public String getUserId() { return userId; }
    
    @Override
    public int compareTo(TestResult other) {
        return this.testDate.compareTo(other.testDate);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Test Date: ").append(sdf.format(testDate)).append("\n");
        sb.append("User ID: ").append(userId).append("\n");
        for (PersonalityDimension dim : PersonalityDimension.values()) {
            sb.append(dim.getName()).append(": ").append(scores.get(dim)).append("/25\n");
        }
        return sb.toString();
    }
}