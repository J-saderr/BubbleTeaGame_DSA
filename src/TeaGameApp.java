import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class TeaGameApp {
    private static JTextArea bubbleTeaTextArea = new JTextArea(10, 30);
    private static String[] customerNames = {"Foam", "Tea", "Topping"};
    private static String[] toppings = {"Tapioca pearls", "Lychee jelly", "Grass jelly", "Popping boba", "Custard pudding"};
    private static boolean[] isOrderVisible = {false, false, false};
    private static Timer orderTimer;
    private static Random random = new Random();
    private static List<String> menu = new ArrayList<>();

    public static void main(String[] args) {
        menu.addAll(Arrays.asList("Tapioca Bubble Tea", "Lychee Bubble Tea", "Grass Jelly Bubble Tea", "Popping Boba Bubble Tea"));
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Bubble Tea Order App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton[] buttons = new JButton[customerNames.length];
        for (int i = 0; i < customerNames.length; i++) {
            buttons[i] = new JButton("Khách hàng " + (i + 1));
            buttons[i].addActionListener(new CustomerButtonListener(i));
            panel.add(buttons[i]);
        }

        panel.add(bubbleTeaTextArea);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static class CustomerButtonListener implements ActionListener {
        private int customerIndex;

        public CustomerButtonListener(int index) {
            this.customerIndex = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide all other orders
            for (int i = 0; i < isOrderVisible.length; i++) {
                if (i != customerIndex) {
                    isOrderVisible[i] = false;
                }
            }

            if (isOrderVisible[customerIndex]) {
                bubbleTeaTextArea.setText("");
                isOrderVisible[customerIndex] = false;
            } else {
                // Clear previous order before displaying the new one
                bubbleTeaTextArea.setText("");
                addCustomer(customerNames[customerIndex]);
                isOrderVisible[customerIndex] = true;

                // Set order display time
                if (orderTimer != null) {
                    orderTimer.stop();
                }
                orderTimer = new Timer(300000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bubbleTeaTextArea.setText("");
                        isOrderVisible[customerIndex] = false;
                    }
                });
                orderTimer.setRepeats(false);
                orderTimer.start();
            }
        }
    }

    private static void addCustomer(String customerName) {
        // Simulate adding a customer and displaying their order
        String message = "Tên khách hàng: " + customerName + "\n"
                + "Toppings: " + getRandomTopping() + "\n"
                + "Kem cheese: " + getRandomCheeseStatus() + "\n\n";

        bubbleTeaTextArea.append(message);
    }

    private static String getRandomTopping() {
        int randomIndex = random.nextInt(toppings.length);
        return toppings[randomIndex];
    }

    private static String getRandomCheeseStatus() {
        return random.nextBoolean() ? "Có" : "Không";
    }
}

