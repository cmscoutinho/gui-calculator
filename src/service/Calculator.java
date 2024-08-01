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
    private String operand1 = "";
    private String operand2 = "";
    private String operator = "";
    private boolean isOperatorPressed = false;

    public Calculator() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setTitle("Calculator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20,20));

        display = new JTextField();
        display.setEditable(true);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
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

        if(isNumber(command)) {
            String text = display.getText();
            if(!text.contains(".") || !command.equals(".")) {
                display.setText(text + command);
            }
        } else if(isOperand(command)) {
            operand1 = display.getText();
        } else if(isFunction(command)) {
            operand1 = display.getText();
            display.setText(String.valueOf(getFunctionValue(command, operand1)));
        }

//        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
//            if (!isOperatorPressed) {
//                operand1 += command;
//                display.setText(operand1);
//            } else {
//                operand2 += command;
//                display.setText(operand2);
//            }
//        } else if (command.equals("=")) {
//            double result = 0;
//            double num1 = Double.parseDouble(operand1);
//            double num2 = Double.parseDouble(operand2);
//
//            switch (operator) {
//                case "/":
//                    result = num1 / num2;
//                    break;
//                case "*":
//                    result = num1 * num2;
//                    break;
//                case "-":
//                    result = num1 - num2;
//                    break;
//                case "+":
//                    result = num1 + num2;
//                    break;
//            }
//            display.setText(String.valueOf(result));
//            operand1 = String.valueOf(result);
//            operand2 = "";
//            isOperatorPressed = false;
//        } else {
//            if (!operand1.equals("")) {
//                operator = command;
//                isOperatorPressed = true;
//            }
//        }
    }

    private double getFunctionValue(String command, String operand) {
        if(command.equals("Sin"))
            return Math.sin(Double.valueOf(operand));
        if(command.equals("Cos"))
            return Math.cos(Double.valueOf(operand));
        if(command.equals("Tan"))
            return Math.tan(Double.valueOf(operand));
        return 0.0;
    }

    private boolean isFunction(String command) {
        return  command.equals("Cos") ||
                command.equals("Sin") ||
                command.equals("Tan") ||
                command.equals("CE");
    }

    private boolean isOperand(String command) {
        return command.equals("+")    ||
                command.equals("-")   ||
                command.equals("*")   ||
                command.equals("/")   ||
                command.equals("=");
    }

    private boolean isNumber(String command) {
        return command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".");
    }
}
