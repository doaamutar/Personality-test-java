
 import java.util.*;
 import java.text.SimpleDateFormat;
 // question class removed 
 /**
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
    
     