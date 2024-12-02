import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PersonalityTestSystem system = new PersonalityTestSystem();
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Personality Test System!");
        
        while (true) {
            System.out.println("\nAre you a (1) User or (2) Admin? (3 to Exit)");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    userMenu();
                    break;
                case "2":
                    if (PersonalityTestSystem.verifyAdminPassword(scanner)) {
                        adminMenu();
                    } else {
                        System.out.println("Invalid password. Access denied.");
                    }
                    break;
                case "3":
                    System.out.println("Thank you for using the Personality Test System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Register new user");
            System.out.println("2. Take personality test");
            System.out.println("3. View test history");
            System.out.println("4. Exit");
            
            System.out.print("\nEnter your choice (1-4): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    system.registerUser(userId);
                    break;
                    
                case "2":
                    System.out.print("Enter user ID: ");
                    userId = scanner.nextLine();
                    system.takeTest(userId, scanner);
                    break;
                    
                case "3":
                    System.out.print("Enter user ID: ");
                    userId = scanner.nextLine();
                    system.viewTestHistory(userId);
                    break;
                    
                case "4":
                    System.out.println("Thanks for taking this test. I hope you learned more about yourself!");
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add new question");
            System.out.println("2. Remove question");
            System.out.println("3. Return to main menu");
            
            System.out.print("\nEnter your choice (1-3): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addNewQuestion();
                    break;
                    
                case "2":
                    removeQuestion();
                    break;
                    
                case "3":
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addNewQuestion() {
        System.out.print("Enter question ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter question text: ");
        String text = scanner.nextLine();
        
        System.out.println("Select personality dimension:");
        for (PersonalityDimension dim : PersonalityDimension.values()) {
            System.out.println(dim.ordinal() + 1 + ". " + dim.getName());
        }
        
        int dimChoice = Integer.parseInt(scanner.nextLine()) - 1;
        PersonalityDimension dimension = PersonalityDimension.values()[dimChoice];
        
        Question newQuestion = new Question(id, text, dimension);
        system.addQuestion(newQuestion);
    }
    
    private static void removeQuestion() {
        System.out.print("Enter question ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean removed = system.removeQuestion(id);
        System.out.println(removed ? "Question removed successfully!" : "Question not found.");
    }
}