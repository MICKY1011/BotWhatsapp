import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.awt.*;

public class loginAccess extends JFrame {
        public static Frame newFrame;

    private int x = 200, y1 = 150, y2 = 250, width = 200, height = 50;

    loginAccess(ChromeDriver driver) throws InterruptedException {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setBackground(Color.white);


        // success login label
        JLabel successLogin = new JLabel("log in successfully!");
        successLogin.setBounds(this.x - 40, 0, 300, 100);
        Font f = new Font("ARIEL", Font.BOLD, 15);
        successLogin.setForeground(Color.black);
        successLogin.setFont(f);
        successLogin.setBackground(Color.green);
        this.add(successLogin);
        successLogin.setVisible(true);

        // text field of the phone number
        JTextField phoneNumber = new JTextField();
        phoneNumber.setBounds(this.x - 60, this.y1, this.width, this.height);
        phoneNumber.setBackground(new Color(142, 255, 146));
        this.add(phoneNumber);
        phoneNumber.setVisible(true);

        //number label
        JLabel numberOf = new JLabel("enter number: ");
        numberOf.setBounds(50, this.y1, 170, this.height);
        numberOf.setBackground(new Color(142, 255, 146));
        numberOf.setOpaque(true);
        this.add(numberOf);

        //text field of the message
        JTextField message = new JTextField();
        message.setBounds(this.x - 60, this.y2, this.width, this.height);
        message.setBackground(new Color(142, 255, 146));
        this.add(message);
        message.setVisible(true);
        this.setVisible(true);

        //text label
        JLabel textOf = new JLabel("enter text: ");
        textOf.setBounds(50, this.y2, 170, this.height);
        textOf.setBackground(new Color(142, 255, 146));
        textOf.setOpaque(true);
        this.add(textOf);

        //error label
        JLabel error = new JLabel();
        error.setBounds(90, 350, 300, 50);
        error.setForeground(Color.black);
        error.setBackground(new Color(142, 255, 146));
        error.setOpaque(true);
        error.setVisible(false);
        this.add(error);

        // sendButton
        JButton sendButton = new JButton();
        sendButton.setBounds(90, 400, 300, 70);
        sendButton.setVerticalAlignment(JButton.CENTER);
        sendButton.setHorizontalAlignment(JButton.CENTER);
        sendButton.setText("send message");
        sendButton.setFocusable(true);
        this.add(sendButton);

        //sent successfully
        JFrame sentSuccessfully = new JFrame("your message sent successfully!");
        sentSuccessfully.setBounds(this.x+50, this.y2, this.width, this.height);
        sentSuccessfully.setBackground(new Color(142, 255, 146));
        this.add(sentSuccessfully);

        //after click the button
        sendButton.addActionListener((e -> {
            try {
                if (!noInput(message) || noPhoneNumber(phoneNumber) || checkNumber(phoneNumber)) {
                    error.setVisible(true);
                    if (!noInput(message) && checkNumber(phoneNumber) && noPhoneNumber(phoneNumber)) {
                        error.setText("please enter your message: ");
                    }
                    if (!checkNumber(phoneNumber) || !noPhoneNumber(phoneNumber)) {
                        error.setText("you must enter correct 10 digits");
                    }
                    if (noInput(message) && noPhoneNumber(phoneNumber) && checkNumber(phoneNumber)) {
                        this.setVisible(false);
                        successLogin.setVisible(false);
                        error.setVisible(false);
                        textOf.setVisible(false);
                        phoneNumber.setVisible(false);
                        message.setVisible(false);
                        numberOf.setVisible(false);
                        sendButton.setVisible(false);
                        driver.get("https://web.whatsapp.com/send?phone=972" + phoneNumber.getText());
                        if (readAndDelivered(driver)) {
                            WebElement input = driver.findElement(By.cssSelector("#main > footer > div._2BU3P.tm2tP.copyable-area > div > span:nth-child(2) > div > div._2lMWa > div.p3_M1 > div > div._13NKt.copyable-text.selectable-text"));
                            input.click();
                            input.sendKeys(message.getText());
                            WebElement sendMessage = driver.findElement(By.cssSelector("#main > footer > div._2BU3P.tm2tP.copyable-area > div > span:nth-child(2) > div > div._2lMWa > div._3HQNh._1Ae7k"));
                            sendMessage.click();
                        }
                        while (true) {
                            if (driver.getPageSource().contains(" נשלחה ") || driver.getPageSource().contains(" נקראה ") || driver.getPageSource().contains(" Read ") || driver.getPageSource().contains(" Delivered ")) {
                                this.add(sentSuccessfully);
                                break;
                            }
                        }
                        Thread.sleep(3000);
                        driver.close();
                    }
                }
            } catch (Exception ex) {
                error.setVisible(true);
                error.setText("you must enter correct 10 digits");
            }
        }));
    }

    //  checkNumber  method
    public boolean checkNumber(JTextField t) {
        String S = "";
        int numberOfDigits = t.getText().length();
        for (int i = 0; i < 3; i++) {
            S += t.getText().charAt(i);
        }
        if (numberOfDigits != 10)
            return false;
        return S.equals("050") || S.equals("051") || S.equals("052") || S.equals("053") || S.equals("054") || S.equals("055") || S.equals("058");
    }

    // noNumber method
    public boolean noPhoneNumber(JTextField T) {
        return !T.getText().equals("");
    }

    // check message method
    public boolean noInput(JTextField M) {
        return !M.getText().equals("");
    }

    // check visible of chat
    public boolean readAndDelivered(ChromeDriver D) {
        boolean in = false;
        while (true) {
            if (D.getPageSource().contains("הקלדת ההודעה") || D.getPageSource().contains("Type a message")) {
                in = true;
            }
            if (in == true)
                break;
        }
        return true;
    }
}


