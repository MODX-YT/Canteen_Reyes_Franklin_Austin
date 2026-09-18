import java.util.Scanner;

public class Canteen {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //menu items
        String[] menuItems = {"Burger", "Pizza", "Pasta", "Sandwich", "Milk Tea"};
        double[] menuPrices = {80.00, 120.00, 100.00, 70.00, 90.00};

        //tracking variables
        int totalQuantityPurchased = 0;
        double totalBeforeDeductions = 0.0;
        double totalDeductions = 0.0;

        //display menu
        System.out.println("===== M E N U =====");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.printf("%d. %-10s - $%.2f%n", (i + 1), menuItems[i], menuPrices[i]);
        }
        System.out.println();

        boolean ordering = true;

        while (ordering) {
            System.out.print("Enter item number: ");
            if (!scanner.hasNextInt()) {
                scanner.next(); //consume invalid token
                System.out.println("\nInvalid order! Please enter a valid item and quantity.\n");
                ordering = askToOrderAgain(scanner);
                continue;
            }
            int itemNum = scanner.nextInt();

            System.out.print("Enter quantity: ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("\nInvalid order! Please enter a valid item and quantity.\n");
                ordering = askToOrderAgain(scanner);
                continue;
            }
            int quantity = scanner.nextInt();

            //validate item number 1-5 and quantity 1-10
            if (itemNum < 1 || itemNum > menuItems.length || quantity < 1 || quantity > 10) {
                System.out.println("\nInvalid order! Please enter a valid item and quantity.\n");
                ordering = askToOrderAgain(scanner);
                continue;
            }

            System.out.print("Are you a student? (Y/N): ");
            char studentStatus = scanner.next().toUpperCase().charAt(0);
            boolean isStudent = (studentStatus == 'Y');

            //Calculation for current order
            double itemPrice = menuPrices[itemNum - 1];
            double subtotal = itemPrice * quantity;

            //Determine discount percentage
            double discountRate = 0.0;
            if (isStudent && subtotal >= 500.0) {
                discountRate = 0.15; //Student and >= $500
            } else if (isStudent) {
                discountRate = 0.10; //Student only
            } else if (subtotal >= 500.0) {
                discountRate = 0.05; // >= $500 only
            }

            double discountAmount = subtotal * discountRate;
            double orderTotal = subtotal - discountAmount;

            //update cumulative totals
            totalQuantityPurchased += quantity;
            totalBeforeDeductions += subtotal;
            totalDeductions += discountAmount;

            //order receipt display
            System.out.println();
            System.out.printf("Subtotal: $%.2f%n", subtotal);
            System.out.printf("Discount: $%.2f%n", discountAmount);
            System.out.printf("Order total: $%.2f%n%n", orderTotal);

            //check if user wants to continue
            ordering = askToOrderAgain(scanner);
        }

        //final summary output
        double finalAmountToPay = totalBeforeDeductions - totalDeductions;
        System.out.println();
        System.out.println("===== ORDER SUMMARY =====");
        System.out.printf("Total items: %d%n", totalQuantityPurchased);
        System.out.printf("Total before discount: $%.2f%n", totalBeforeDeductions);
        System.out.printf("Total discount: $%.2f%n", totalDeductions);
        System.out.printf("Final amount: $%.2f%n", finalAmountToPay);
        System.out.println("Thank you for ordering!");

        scanner.close();
    }

    private static boolean askToOrderAgain(Scanner scanner) {
        System.out.print("Do you want to order again? (Y/N): ");
        char choice = scanner.next().toUpperCase().charAt(0);
        return choice == 'Y';
    }
}
