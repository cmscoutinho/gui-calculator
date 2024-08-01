package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel panel;
    private String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
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
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        display = new JTextField();
        display.setEditable(true);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 20, 20));
        add(panel, BorderLayout.CENTER);

        button = new JButton[16];

        for (int i = 0; i < 16; i++) {
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

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            if (!isOperatorPressed) {
                operand1 += command;
                display.setText(operand1);
            } else {
                operand2 += command;
                display.setText(operand2);
            }
        } else if (command.equals("=")) {
            double result = 0;
            double num1 = Double.parseDouble(operand1);
            double num2 = Double.parseDouble(operand2);

            switch (operator) {
                case "/":
                    result = num1 / num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "+":
                    result = num1 + num2;
                    break;
            }
            display.setText(String.valueOf(result));
            operand1 = String.valueOf(result);
            operand2 = "";
            isOperatorPressed = false;
        } else {
            if (!operand1.equals("")) {
                operator = command;
                isOperatorPressed = true;
            }
        }
    }
}
