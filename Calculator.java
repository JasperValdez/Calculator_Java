import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame frame;
    private JTextField historyField; 
    private JTextField resultField; 
    private double result = 0;
    private char operator = '\0';
    private boolean isOperatorPressed = false;

    public Calculator() {
        // Create frame
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

      
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1)); 

        historyField = new JTextField();
        historyField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        historyField.setHorizontalAlignment(JTextField.RIGHT);
        historyField.setEditable(false);
        historyField.setBackground(Color.WHITE);
        historyField.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); 

     
        resultField = new JTextField("0");
        resultField.setFont(new Font("Arial", Font.BOLD, 48)); 
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setEditable(false);
        resultField.setBackground(Color.WHITE);
        resultField.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 

        displayPanel.add(historyField);
        displayPanel.add(resultField);
        frame.add(displayPanel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); 
        buttonPanel.setBackground(new Color(240, 240, 240)); 
        // Button labels
        String[] buttonLabels = {
            "DEL", "C", "÷", "x",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };

        // Create buttons and add to panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24)); 
            button.setBackground(Color.WHITE); 
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); 
            button.addActionListener(new ButtonClickListener());
            if (!label.isEmpty()) {
                buttonPanel.add(button); 
            } else {
                buttonPanel.add(new JLabel()); 
            }
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

      
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "C":
                    historyField.setText("");
                    resultField.setText("0");
                    result = 0;
                    operator = '\0';
                    isOperatorPressed = false;
                    break;

                case "DEL":
                    String currentText = resultField.getText();
                    if (!currentText.equals("0") && currentText.length() > 0) {
                        resultField.setText(currentText.substring(0, currentText.length() - 1));
                        if (resultField.getText().isEmpty()) {
                            resultField.setText("0"); 
                        }
                    }
                    break;

                case "=":
                    if (!resultField.getText().isEmpty() && operator != '\0') {
                        double num = Double.parseDouble(resultField.getText());
                        result = performOperation(result, num, operator);
                        historyField.setText(historyField.getText() + resultField.getText() + " =");
                        resultField.setText(String.valueOf(result));
                        operator = '\0'; 
                    }
                    break;

                case "+":
                case "-":
                case "x":
                case "÷":
                    if (!resultField.getText().isEmpty()) {
                        double num = Double.parseDouble(resultField.getText());
                        if (operator != '\0') {
                            result = performOperation(result, num, operator); 
                        } else {
                            result = num; 
                        }
                        operator = command.charAt(0);
                        isOperatorPressed = true;
                        historyField.setText(result + " " + operator); 
                        resultField.setText(""); 
                    }
                    break;

                default: 
                    if (isOperatorPressed) {
                        resultField.setText(""); 
                        isOperatorPressed = false;
                    }
                    String displayText = resultField.getText();
                    if (displayText.equals("0") && !command.equals(".")) {
                        resultField.setText(command); 
                    } else {
                        resultField.setText(displayText + command); 
                    }
                    break;
            }
        }

        private double performOperation(double num1, double num2, char operator) {
            switch (operator) {
                case '+': return num1 + num2;
                case '-': return num1 - num2;
                case 'x': return num1 * num2;
                case '÷': return num2 != 0 ? num1 / num2 : 0; 
                default: return 0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
