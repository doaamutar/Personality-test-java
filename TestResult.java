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
        return "\nWhat Do your Results Mean?\n\n" +
               "Extroversion:\n" +
               "Extroversion (E) is the personality trait of seeking fulfillment from sources outside the self or in community. " +
               "High scorers tend to be very social while low scorers prefer to work on their projects alone.\n\n" +
               
               "Agreeableness:\n" +
               "Agreeableness (A) reflects how much individuals adjust their behavior to suit others. " +
               "High scorers are typically polite and like people. Low scorers tend to 'tell it like it is'.\n\n" +
               
               "Conscientiousness:\n" +
               "Conscientiousness (C) is the personality trait of being honest and hardworking. " +
               "High scorers tend to follow rules and prefer clean homes. Low scorers may be messy and cheat others.\n\n" +
               
               "Neuroticism:\n" +
               "Neuroticism (N) is the personality trait of being emotional. High scorers tend to have high emotional reactions to stress. " +
               "They may perceive situations as threatening and be more likely to feel moody, depressed, angry, anxious, and experience mood swing. " +
               "Low scorers tend to be more emotionally stable and less reactive to stress.\n\n" +
               
               "Openness to Experience:\n" +
               "Openness to Experience (O) is the personality trait of seeking new experiences and intellectual pursuits. " +
               "High scores may day dream a lot (enjoy thinking about new and different things). Low scorers tend to be very down to earth " +
               "(more of a 'hear and now' thinker). Consequently, it is thought that people with higher scores might be more creative, flexible, " +
               "curious, and adventurous, whereas people with lower score might tend to enjoy routines, predictability, and structure.";
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
        sb.append(getResultsExplanation());
        return sb.toString();
    }
}