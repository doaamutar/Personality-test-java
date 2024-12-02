import java.text.SimpleDateFormat;
import java.util.*;

    public class Main {
        public static Scanner scanner = new Scanner(System.in);
        private static PersonalityTestSystem system = new PersonaliyTestSystem();

        public static void main(String[] args) {
            System.out.println("Welcome to the Personality Test System");

            System.out.print("Are you an Admin or a User?");
            String answer = scanner.nextLine();

            if (answer.equals("Admin")){
                boolean isAdmin = PersonalityTestSystem.checkPassword(scanner);
                    while (true){
                        System.out.println("")
                    }

            }

            boolean isAdmin = PersonalityTestSystem.checkPassword(scanner);
        }
    
    }