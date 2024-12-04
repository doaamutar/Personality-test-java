import java.util.*;

public class PersonalityTestSystem {
    private Queue<Question> questionQueue;
    private Stack<Question> answeredQuestions;
    private Map<String, List<TestResult>> testHistory;
    private Set<String> users;
    
    public PersonalityTestSystem() {
        questionQueue = new LinkedList<>();
        answeredQuestions = new Stack<>();
        testHistory = new HashMap<>();
        users = new HashSet<>();
        initializeQuestions();
    }
    
    private void initializeQuestions() {
        // Sample questions (same as before)
        
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
        }
        
    
    public void addQuestion(Question question) {
        questionQueue.add(question);
        System.out.println("Question added successfully!");
    }
    
    public boolean removeQuestion(int questionId) {
        for (Question q : questionQueue) {
            if (q.getId() == questionId) {
                questionQueue.remove(q);
                return true;
            }
        }
        return false;
    }
    
    public void registerUser(String userId) {
        users.add(userId);
        testHistory.put(userId, new ArrayList<>());
        System.out.println("User " + userId + " registered successfully!");
    }
    
    public void takeTest(String userId, Scanner scanner) {
        if (!users.contains(userId)) {
            System.out.println("Error: User not found. Please register first.");
            return;
        }
    
        TestResult result = new TestResult(userId);
        List<Question> questionList = new ArrayList<>(questionQueue); // Shallow copy of questions
        int currentQuestion = 0;
    
        System.out.println("\nStarting personality test...");
        System.out.println("Rate each statement from 1 (Strongly Disagree) to 5 (Strongly Agree)");
        System.out.println("Use 'n' for next question, 'p' for previous question, or 'q' to quit\n");
    
        while (true) {
            // Prevent invalid indices
            if (currentQuestion < 0) currentQuestion = 0;
            if (currentQuestion >= questionList.size()) {
                System.out.println("\nYou have completed the test!");
                break;
            }
        
            // Retrieve current question
            Question q = questionList.get(currentQuestion);
    
            // Add line spacing and separator for better readability
            System.out.println("\n----------------------------------------");
            System.out.println("Question " + (currentQuestion + 1) + "/" + questionList.size());
            System.out.println(q.getText());
            if (q.getAnswer() > 0) { // Show answer if it has been previously set
                System.out.println("Current answer: " + q.getAnswer());
            }
            System.out.println("----------------------------------------");
    
            // Prompt for user input
            System.out.print("Your answer (1-5, n/p/q): ");
            String input = scanner.nextLine().trim().toLowerCase();
        
            switch (input) {
                case "n": // Move to the next question
                    if (currentQuestion < questionList.size() - 1) {
                        currentQuestion++;
                    } else {
                        System.out.println("You are on the last question.");
                    }
                    break;
                case "p": // Move to the previous question
                    if (currentQuestion > 0) {
                        currentQuestion--;
                    } else {
                        System.out.println("You are on the first question.");
                    }
                    break;
                case "q": // Quit the test
                    System.out.println("Test cancelled.");
                    return;
                default: // Handle answers
                    try {
                        int answer = Integer.parseInt(input);
                        if (answer >= 1 && answer <= 5) {
                            q.setAnswer(answer); // Save the answer in the current question
                            System.out.println("\nAnswer saved.");
                            currentQuestion++; // Move to the next question automatically
                        } else {
                            System.out.println("Please enter a number between 1 and 5.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please try again.");
                    }
            }
        }
    
        // Calculate and save results
        calculateResults(result, questionList);
        testHistory.get(userId).add(result);
        System.out.println("\nTest completed! Here are your results:\n");
        System.out.println(result);
    }

    
    private void calculateResults(TestResult result, List<Question> questions) {
        Map<PersonalityDimension, Integer> scores = new EnumMap<>(PersonalityDimension.class);
        
        for (Question q : questions) {
            PersonalityDimension dim = q.getDimension();
            scores.put(dim, scores.getOrDefault(dim, 0) + q.getAnswer());
        }
        
        for (PersonalityDimension dim : PersonalityDimension.values()) {
            result.setScore(dim, scores.getOrDefault(dim, 0));
        }
    }
    
    public void viewTestHistory(String userId) {
        List<TestResult> history = testHistory.get(userId);
        if (history == null || history.isEmpty()) {
            System.out.println("No test history found for user: " + userId);
            return;
        }
        
        Collections.sort(history);
        System.out.println("\nTest History for user: " + userId);
        for (TestResult result : history) {
            System.out.println("\n" + result);
        }
    }
    
    public static boolean verifyAdminPassword(Scanner scanner) {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return password.equals("admin123"); // In a real system, use secure authentication
    }
}