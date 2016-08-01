import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

/**
 * Created by hmly on 6/29/16.
 * Implementation of a simple calculator.
 * http://www.leepoint.net/examples/components/calculator/calc.html
 */
public class Calculator extends JFrame {

    private static final Font DEFAULT_FONT = new Font("monospaced", Font.PLAIN, 15);

    private JTextField displayField;
    private boolean inputIsNum = true;
    private String previousOp = "=";
    private CLogic logic = new CLogic();

    private JButton clearButton;
    private JPanel buttonPanel;

    private ActionListener numListener;
    private ActionListener opListener;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception x) {
            x.printStackTrace();
        }
        Calculator calc = new Calculator();
        calc.setVisible(true);
    }

    private Calculator() {
        displayField = new JTextField("0", 12);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(DEFAULT_FONT);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Implement clear button
        clearButton = new JButton("C");
        clearButton.setFont(DEFAULT_FONT);
        clearButton.addActionListener(new ClearListener());

        // For numeric, operator keys
        numListener = new NumListener();
        opListener = new OpListener();
        generateKeysGrid();

        // Layout the top-level content panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(6, 6));
        content.add(displayField, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setContentPane(content);
        pack();
        setTitle("Simple Calc");
        setLocationRelativeTo(null);
        setSize(350, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void generateKeysGrid() {
        String[] buttonOrder = {
                "7", "8", "9", "÷", "1/x", "sin",
                "4", "5", "6", "x", "x^2", "cos",
                "1", "2", "3", "-", "√", "tan",
                "0", ".", "+", "%", "="
        };
        JButton button;

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3));
        for (String key : buttonOrder) {
            button = new JButton(key);
            if (key.matches("\\d|[.]")) {
                button.addActionListener(numListener);
            } else {
                button.addActionListener(opListener);
            }
            button.setFont(DEFAULT_FONT);
            buttonPanel.add(button);
        }
        buttonPanel.add(clearButton);
    }

    private void actionClear() {
        inputIsNum = true;
        displayField.setText("0");
        previousOp = "=";
        logic.setTotal("0");
    }

    // Opeator key listener
    private class OpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputIsNum) {
                actionClear();
                displayField.setText("ERROR - No operator");
            } else {
                inputIsNum = true;
                try {
                    String input = displayField.getText();
                    switch (previousOp) {
                        case "=":
                            logic.setTotal(input);
                            break;
                        case "+":
                            logic.add(input);
                            break;
                        case "-":
                            logic.subtract(input);
                            break;
                        case "x":
                            logic.multiply(input);
                            break;
                        case "÷":
                            logic.divide(input);
                            break;
                        case "%":
                            logic.modulo(input);
                            break;
                        case "1/x":
                            logic.reciprocal(input);
                            break;
                        case "x^2":
                            logic.square(input);
                            break;
                        case "√":
                            logic.sqroot(input);
                            break;
                        case "sin":
                            logic.sin(input);
                            break;
                        case "cos":
                            logic.cos(input);
                            break;
                        case "tan":
                            logic.tan(input);
                            break;
                        case ".":
                        default:
                            break;
                    }
                    displayField.setText(logic.toString());
                } catch (NumberFormatException x) {
                    actionClear();
                    displayField.setText("Error - Number format");
                }
                previousOp = e.getActionCommand();
            }
        }
    }

    // Numeric key listener
    private class NumListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String digit = e.getActionCommand();
            if (inputIsNum) {
                displayField.setText(digit);
                inputIsNum = false;
            } else {
                displayField.setText(displayField.getText() + digit);
            }
        }
    }

    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionClear();
        }
    }
}
