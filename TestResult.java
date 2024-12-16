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
    
    public Date getTestDate() {
        return testDate;
    }
    
    public String getUserId() {
        return userId;
    }
    
    @Override
    public int compareTo(TestResult other) {
        return this.testDate.compareTo(other.testDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        TestResult other = (TestResult) obj;
        return userId.equals(other.userId) && 
               testDate.equals(other.testDate) &&
               scores.equals(other.scores);
    }
    
    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + testDate.hashCode();
        result = 31 * result + scores.hashCode();
        return result;
    }
    
    private String getResultsExplanation() {
        return "\nUnderstanding Your Results:\n\n" +
               "Extroversion:\n" +
               "Tendency to seek fulfillment externally.\n" +
               "    •    High scorers: Social and outgoing.\n" +
               "    •    Low scorers: More reserved and prefer solitude.\n\n" +
               
               "Agreeableness:\n" +
               "Willingness to adjust behavior to suit others.\n" +
               "    •    High scorers: Polite, empathetic, and cooperative.\n" +
               "    •    Low scorers: Direct, blunt, and less concerned with others' opinions.\n\n" +
               
               "Conscientiousness:\n" +
               "Tendency to be organized, responsible, and hardworking.\n" +
               "    •    High scorers: Follow rules, prefer structure, and value reliability.\n" +
               "    •    Low scorers: May be messy, spontaneous, or disorganized.\n\n" +
               
               "Neuroticism:\n" +
               "Tendency to experience negative emotions.\n" +
               "    •    High scorers: Sensitive to stress, prone to anxiety, mood swings, and irritability.\n" +
               "    •    Low scorers: Emotionally stable and less reactive to stress.\n\n" +
               
               "Openness to Experience:\n" +
               "Tendency to seek new experiences and ideas.\n" +
               "    •    High scorers: Creative, curious, and open-minded.\n" +
               "    •    Low scorers: Prefer routine, practicality, and predictability.\n";
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Test Date: ").append(sdf.format(testDate)).append("\n");
        sb.append("User ID: ").append(userId).append("\n\n");
        sb.append("Your Scores:\n");
        for (PersonalityDimension dim : PersonalityDimension.values()) {
            sb.append(dim.getName()).append(": ").append(scores.get(dim)).append("/25\n");
        }
        sb.append(getResultsExplanation());
        return sb.toString();
    }
}