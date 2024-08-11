package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel panel;
    private String[] buttons = {
            "7", "8", "9", "/", "Sin",
            "4", "5", "6", "*", "Cos",
            "1", "2", "3", "-", "Tan",
            "0", ".", "=", "+", "CE"
    };
    private JButton[] button;
    private String operand1 = "0";
    private String operand2 = "0";
    private String operator = "";
    private String activeOperator = "";
    private boolean isOperatorPressed = false;

    public Calculator() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setTitle("Simple Calculator v1.0");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        display = new JTextField();
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(true);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
//        display.setText("0");
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 5, 20, 20));
        add(panel, BorderLayout.CENTER);

        button = new JButton[20];

        for (int i = 0; i < 20; i++) {
            button[i] = new JButton(buttons[i]);
            button[i].setFont(new Font("Arial", Font.PLAIN, 24));
            button[i].addActionListener(this);
            panel.add(button[i]);
        }


        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String text = display.getText();

        if (isNumber(command) || (isPoint(command) && !text.contains("."))) {
            display.setText(text + command);
        } else if (isOperator(command)) {
            activeOperator = command;
            if (!isOperatorPressed) {
                operand1 = display.getText();
                if (operand1.endsWith(".")) {
                    operand1 += "0";
                }
                isOperatorPressed = true;
                display.setText("");
            } else if (!text.equals("")) {
                operand2 = display.getText();
                if (operand2.endsWith(".")) {
                    operand2 += "0";
                }
            }
        } else if (isFunction(command)) {
            display.setText(String.valueOf(getFunctionValue(command, operand1)));
        } else if (command.equals("=")) {
            if(isOperatorPressed) {
                operand2 = display.getText();
                double res = getOperatorValue(activeOperator, operand1, operand2);
                display.setText(String.valueOf(res));
                resetAll();
            }
        } else if (command.equals("CE")) {
            display.setText("");
            resetAll();
        }
    }

    private void resetAll() {
        operand1 = operand2 = "0";
        activeOperator = "";
        isOperatorPressed = false;
    }

    private boolean isPoint(String command) {
        return command.equals(".");
    }

    private double getOperatorValue(String command, String operand1, String operand2) {
        double op1 = Double.valueOf(operand1);
        double op2 = Double.valueOf(operand2);

        if (command.equals("+"))
            return op1 + op2;
        if (command.equals("-"))
            return op1 - op2;
        if (command.equals("*"))
            return op1 * op2;
        if (command.equals("/"))
            return op1 / op2;
        return 0.0;
    }

    private double getFunctionValue(String command, String operand) {
        if (command.equals("Sin"))
            return Math.sin(Double.valueOf(operand));
        if (command.equals("Cos"))
            return Math.cos(Double.valueOf(operand));
        if (command.equals("Tan"))
            return Math.tan(Double.valueOf(operand));
        return 0.0;
    }

    private boolean isFunction(String command) {
        return command.equals("Cos") ||
                command.equals("Sin") ||
                command.equals("Tan");
    }

    private boolean isOperator(String command) {
        return command.equals("+") ||
                command.equals("-") ||
                command.equals("*") ||
                command.equals("/");
    }

    private boolean isNumber(String command) {
        return (command.charAt(0) >= '0' && command.charAt(0) <= '9');
    }
}
