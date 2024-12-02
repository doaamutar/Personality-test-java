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
        questionQueue.add(new Question(1, "I am the life of the party.", PersonalityDimension.EXTROVERSION));
        // Add all other questions...
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
        if (currentQuestion < 0) currentQuestion = 0; // Prevent negative index
        if (currentQuestion >= questionList.size()) {
            System.out.println("\nYou have completed the test!");
            break;
        }

        Question q = questionList.get(currentQuestion);
        System.out.println("Question " + (currentQuestion + 1) + "/" + questionList.size());
        System.out.println(q.getText());
        if (q.getAnswer() > 0) {
            System.out.println("Current answer: " + q.getAnswer());
        }
        System.out.print("Your answer (1-5, n/p/q): ");

        String input = scanner.nextLine().trim().toLowerCase();

        switch (input) {
            case "n":
                currentQuestion++;
                break;
            case "p":
                currentQuestion--;
                break;
            case "q":
                System.out.println("Test cancelled.");
                return;
            default:
                try {
                    int answer = Integer.parseInt(input);
                    if (answer >= 1 && answer <= 5) {
                        q.setAnswer(answer);
                        currentQuestion++;
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