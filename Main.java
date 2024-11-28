/**
 * Personality Test System
 * This program implements a comprehensive personality test based on the Big Five Personality Traits.
 * It uses multiple data structures and implements sorting, searching, and object-oriented principles.
 * 
 * Data Structures Used:
 * 1. Queue - For managing test questions sequence
 * 2. Stack - For tracking answered questions and allowing undo functionality
 * 
 * Key Features:
 * 1. Take personality test
 * 2. View and sort test history
 * 3. Search previous test results
 * 4. Compare results with others
 * 5. Manage user profiles
 */

 import java.util.*;
 import java.text.SimpleDateFormat;
 
 /**
  * Represents a single test question
  */
 class Question {
     private int id;
     private String text;
     private int answer;
     private PersonalityDimension dimension;
     
     public Question(int id, String text, PersonalityDimension dimension) {
         this.id = id;
         this.text = text;
         this.dimension = dimension;
         this.answer = 0;
     }
     
     // Getters and setters
     public int getId() { return id; }
     public String getText() { return text; }
     public int getAnswer() { return answer; }
     public void setAnswer(int answer) { this.answer = answer; }
     public PersonalityDimension getDimension() { return dimension; }
 }
 
 /**
  * Enum representing the five personality dimensions
  */
 enum PersonalityDimension {
     EXTROVERSION("Extroversion"),
     AGREEABLENESS("Agreeableness"),
     CONSCIENTIOUSNESS("Conscientiousness"),
     NEUROTICISM("Neuroticism"),
     OPENNESS("Openness");
     
     private String name;
     PersonalityDimension(String name) { this.name = name; }
     public String getName() { return name; }
 }
 
 /**
  * Represents a completed test result
  */
 class TestResult implements Comparable<TestResult> {
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
             sb.append(dim.getName()).append(": ").append(scores.get(dim)).append("/40\n");
         }
         return sb.toString();
     }
 }
 
 /**
  * Main system class that manages the personality test
  */
 class PersonalityTestSystem {
     private Queue<Question> questionQueue;  // First data structure: Queue
     private Stack<Question> answeredQuestions;  // Second data structure: Stack
     private Map<String, List<TestResult>> testHistory;
     private Set<String> users;
     
     public PersonalityTestSystem() {
         questionQueue = new LinkedList<>();
         answeredQuestions = new Stack<>();
         testHistory = new HashMap<>();
         users = new HashSet<>();
         initializeQuestions();
     }
     
     /**
      * Initializes the question bank with preset questions
      */
     private void initializeQuestions() {
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
       
     // Method to check the password and return the admin status
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
      * Registers a new user in the system
      */
     public void registerUser(String userId) {
         users.add(userId);
         testHistory.put(userId, new ArrayList<>());
         System.out.println("User " + userId + " registered successfully!");
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
     * Returns the list of test results for a specific user.
     */
    public List<TestResult> getUserResults(String userId) {
        return testHistory.getOrDefault(userId, new ArrayList<>());
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
         Map<PersonalityDimension, Integer> scores = new EnumMap<>(PersonalityDimension.class);
         
         while (!answeredQuestions.isEmpty()) {
             Question q = answeredQuestions.pop();
             PersonalityDimension dim = q.getDimension();
             scores.put(dim, scores.getOrDefault(dim, 0) + q.getAnswer());
         }
         
         for (PersonalityDimension dim : PersonalityDimension.values()) {
             result.setScore(dim, scores.getOrDefault(dim, 0));
         }
     }
     
     /**
      * Recursive method to search for test results by date
      */
     public TestResult searchTestByDate(String userId, Date date, List<TestResult> results, int index) {
         if (index >= results.size()) return null;
         TestResult current = results.get(index);
         if (current.getTestDate().equals(date)) return current;
         return searchTestByDate(userId, date, results, index + 1);
     }
     
     /**
      * Sorts test history by date
      */
     public void sortTestHistory(String userId) {
         List<TestResult> userHistory = testHistory.get(userId);
         if (userHistory != null) {
             Collections.sort(userHistory);
         }
     }
     
     /**
      * Views test history for a user
      */
     public void viewTestHistory(String userId) {
         List<TestResult> history = testHistory.get(userId);
         if (history == null || history.isEmpty()) {
             System.out.println("No test history found for user: " + userId);
             return;
         }
         
         sortTestHistory(userId);
         System.out.println("\nTest History for user: " + userId);
         for (TestResult result : history) {
             System.out.println("\n" + result);
         }
     }
 }
 
 /**
  * Main class with program entry point and menu interface
  */
 public class Main {
     private static Scanner scanner = new Scanner(System.in);
     private static PersonalityTestSystem system = new PersonalityTestSystem();
     
     public static void main(String[] args) {
         System.out.println("Welcome to the Personality Test System!");
         
     boolean isAdmin = PersonalityTestSystem.checkPassword(scanner);

         while (true) {
             System.out.println("\nPlease choose an option:");
             System.out.println("1. Register new user");
             System.out.println("2. Take personality test");
             System.out.println("3. View test history");
             System.out.println("4. Sort test history by date");
             System.out.println("5. Search test results by date");
             if (isAdmin) { // Show admin options only if logged in as admin
                System.out.println("6. Add new question (Admin Only)");
                System.out.println("7. Remove a question (Admin Only)");
            }
            System.out.println(isAdmin ? "8. Exit" : "6. Exit");
             
             System.out.print("\nEnter your choice (1-8): ");
             
             try {
                 int choice = Integer.parseInt(scanner.nextLine());
                 
                 switch (choice) {
                    case 1:
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        system.registerUser(userId);
                        break;
        
                    case 2:
                        System.out.print("Enter user ID: ");
                        userId = scanner.nextLine();
                        system.takeTest(userId, scanner);
                        break;
        
                    case 3:
                        System.out.print("Enter user ID: ");
                        userId = scanner.nextLine();
                        system.viewTestHistory(userId);
                        break;
        
                    case 4:
                        System.out.print("Enter user ID: ");
                        userId = scanner.nextLine();
                        system.sortTestHistory(userId);
                        break;
        
                    case 5:
                        // Placeholder for searching test results by date
                        break;
        
                    case 6:
                        if (isAdmin) {
                            // Admin-only actions
                            System.out.print("Enter question ID to remove: ");
                            int questionId = Integer.parseInt(scanner.nextLine());
                            boolean removed = system.removeQuestion(questionId);
                            System.out.println(removed ? "Question removed successfully!" : "Question not found.");
                        } else {
                            System.out.println("Exiting... Thank you for using the system.");
                            System.exit(0); // Exit the program for non-admin users
                        }
                        break;
        
                    case 7:
                        if (isAdmin) {
                            // Admin option for adding questions
                            System.out.println("Admin options...");
                            // You can implement adding questions logic here
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                        break;
        
                    case 8:
                        System.out.println("Exiting... Thank you for using the system.");
                        System.exit(0); // Exit the program for admin or non-admin users
                        break;
        
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
            }
         }
     }
     
     private static void registerUser() {
         System.out.print("Enter user ID: ");
         String userId = scanner.nextLine();
         system.registerUser(userId);
     }
     
     private static void takeTest() {
         System.out.print("Enter your user ID: ");
         String userId = scanner.nextLine();
         system.takeTest(userId, scanner);
     }
     
     private static void viewHistory() {
         System.out.print("Enter user ID: ");
         String userId = scanner.nextLine();
         system.viewTestHistory(userId);
     }
     
     private static void sortHistory() {
         System.out.print("Enter user ID: ");
         String userId = scanner.nextLine();
         system.sortTestHistory(userId);
         system.viewTestHistory(userId);
     }
     
     private static void searchResults() {
         System.out.print("Enter user ID: ");
         String userId = scanner.nextLine();
         System.out.print("Enter date (yyyy-MM-dd): ");
         try {
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             Date searchDate = sdf.parse(scanner.nextLine());
             TestResult result = system.searchTestByDate(userId, searchDate, 
                 system.getUserResults(userId), 0);
             if (result != null) {
                 System.out.println("\nFound test result:\n" + result);
             } else {
                 System.out.println("No test results found for that date.");
             }
         } catch (Exception e) {
             System.out.println("Invalid date format. Please use yyyy-MM-dd");
         }
     }
 }
    
     