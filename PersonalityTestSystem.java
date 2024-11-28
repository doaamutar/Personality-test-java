import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

class PersonalityTestSystem{
    private Queue<Question> questionQueue;
    private Stack<Question> answeredQuestions;
    private Set<String> users;

    public PersonalityTestSystem() {
        questionQueue = new LinkedList<>();
        answeredQuestions= new Stack<>();
        users = new Hashset<>();
        initializedQuestions();
    }

     /**
     * Initializes the question bank with present questiona
     */

        private void initializedQuestions(){

                    // Add sample questions for each dimension
                // Extroversion Questions
            questionQueue.add(new Question(1, "I am the life of the party.", PersonalityDimension.EXTROVERSION));
            questionQueue.add(new Question(6, "I don't talk a lot.", PersonalityDimension.EXTROVERSION));
            questionQueue.add(new Question(11, "I feel comfortable around people.", PersonalityDimension.EXTROVERSION));
            questionQueue.add(new Question(16, "I keep in the background.", PersonalityDimension.EXTROVERSION));
            questionQueue.add(new Question(21, "I start conversations.", PersonalityDimension.EXTROVERSION));

                // Agreeableness Questions
            questionQueue.add(new Question(2, "I feel little concern for others.", PersonalityDimension.AGREEABLENESS));
            questionQueue.add(new Question(7, "I am interested in people.", PersonalityDimension.AGREEABLENESS));
            questionQueue.add(new Question(12, "I insult people.", PersonalityDimension.AGREEABLENESS));
            questionQueue.add(new Question(17, "I sympathize with others' feelings.", PersonalityDimension.AGREEABLENESS));
            questionQueue.add(new Question(22, "I am not interested in other people's problems.", PersonalityDimension.AGREEABLENESS));

                // Conscientiousness Questions
            questionQueue.add(new Question(3, "I am always prepared.", PersonalityDimension.CONSCIENTIOUSNESS));
            questionQueue.add(new Question(8, "I leave my belongings around.", PersonalityDimension.CONSCIENTIOUSNESS));
            questionQueue.add(new Question(13, "I pay attention to details.", PersonalityDimension.CONSCIENTIOUSNESS));
            questionQueue.add(new Question(18, "I make a mess of things.", PersonalityDimension.CONSCIENTIOUSNESS));
            questionQueue.add(new Question(23, "I get chores done right away.", PersonalityDimension.CONSCIENTIOUSNESS));

                // Neuroticism Questions
            questionQueue.add(new Question(4, "I get stressed out easily.", PersonalityDimension.NEUROTICISM));
            questionQueue.add(new Question(9, "I am relaxed most of the time.", PersonalityDimension.NEUROTICISM));
            questionQueue.add(new Question(14, "I worry about things.", PersonalityDimension.NEUROTICISM));
            questionQueue.add(new Question(19, "I seldom feel blue.", PersonalityDimension.NEUROTICISM));
            questionQueue.add(new Question(24, "I am easily disturbed.", PersonalityDimension.NEUROTICISM));

                // Openness Questions
            questionQueue.add(new Question(5, "I have a rich vocabulary.", PersonalityDimension.OPENNESS));
            questionQueue.add(new Question(10, "I have difficulty understanding abstract ideas.", PersonalityDimension.OPENNESS));
            questionQueue.add(new Question(15, "I have a vivid imagination.", PersonalityDimension.OPENNESS));
            questionQueue.add(new Question(20, "I am not interested in abstract ideas.", PersonalityDimension.OPENNESS));
            questionQueue.add(new Question(25, "I have excellent ideas.", PersonalityDimension.OPENNESS));
                // Add more questions as needed...
        }

       public static boolean checkPassword(Scanner scanner) {
            System.out.print("Enter admin password to enable admin features: ");
            String password = scanner.nextLine();
            return password.equals("admin123"); // Replace with secure auth in production
        }

        public void addQuestion(Question question) {
            questionQueue.add(question);
        }

        // Remove a question by its ID
        public boolean removeQuestion(int questionId) {
            for (Question q : questionQueue) {
                if (q.getId() == questionId) {
                     questionQueue.remove(q);
                        return true;
                }
            }
            return false; // Return false if question with given ID wasn't found
        }
        
        /**
     * Conducts the personality test for a user
     */
    public void takeTest(String userId, Scanner scanner) {
        if (!users.contains(userId)) {
            System.out.println("Error: User not found. Please register first.");
            return;
        }
        
        TestResult result = new TestResult(userId);
        Queue<Question> tempQueue = new LinkedList<>(questionQueue);
        
        System.out.println("\nStarting personality test...");
        System.out.println("Rate each statement from 1 (Strongly Disagree) to 5 (Strongly Agree)\n");
        
        while (!tempQueue.isEmpty()) {
            Question q = tempQueue.poll();
            System.out.println(q.getText());
            System.out.print("Your answer (1-5): ");
            int answer = getValidAnswer(scanner);
            q.setAnswer(answer);
            answeredQuestions.push(q);
        }
        
        calculateResults(result);
        testHistory.get(userId).add(result);
        System.out.println("\nTest completed! Here are your results:\n");
        System.out.println(result);
    }

        /**
     * Validates user input for test answers
     */
    private int getValidAnswer(Scanner scanner) {
        while (true) {
            try {
                int answer = Integer.parseInt(scanner.nextLine());
                if (answer >= 1 && answer <= 5) return answer;
                System.out.print("Please enter a number between 1 and 5: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
            }
        }
    }

    /**
     * Calculates test results based on answers
     */
    private void calculateResults(TestResult result) {
        Map<PersonalityDimension, Integer> scores = new fed<>(PersonalityDimension.class);
        
        while (!answeredQuestions.isEmpty()) {
            Question q = answeredQuestions.pop();
            PersonalityDimension dim = q.getDimension();
            scores.put(dim, scores.getOrDefault(dim, 0) + q.getAnswer());
        }
        
        for (PersonalityDimension dim : PersonalityDimension.values()) {
            result.setScore(dim, scores.getOrDefault(dim, 0));
        }
    }
}
