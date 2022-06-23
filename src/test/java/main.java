import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.awt.*;

public class main {
    public static Frame myFrame;

    public static void main(String[] args) {

        main main = new main();
    }
    public main() {
        myFrame = new Frame();

        JLabel label = new JLabel();
        label.setText("welcome!");
        label.setFont(new Font("MV", Font.PLAIN, 25));
        label.setBackground(Color.lightGray);
        label.setBounds(180, 50, 150, 50);
        label.setOpaque(false);
        myFrame.add(label) ;

        JButton button = new JButton();
        button.setBounds(120, 300, 250, 100);
        button.setVerticalAlignment(JButton.CENTER);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setText("open Whatsapp");
        button.setFocusable(true);
        myFrame.add(button);
        button.setBorder((BorderFactory.createEtchedBorder()));
        button.addActionListener(e -> {
            button.setVisible(false);
            myFrame.setVisible(false);
            label.setVisible(false);
            System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
            ChromeDriver driver = new ChromeDriver();
            driver.get("https://web.whatsapp.com/");
            driver.manage().window().maximize();
            logIn(driver);
        });

    }
    public static void logIn(ChromeDriver d){
        Thread t = new Thread(() -> {
            boolean flag = true;
            while (flag) {
                if (d.getPageSource().contains("Search input textbox") || d.getPageSource().contains("תיבת טקסט להזנת החיפוש")) {
                    flag = false;
                    try {
                        loginAccess user = new loginAccess(d);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });t.start();

    }
}
