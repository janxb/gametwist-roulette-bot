package de.janxb.grb;


import org.sikuli.script.*;

public class Main {
    public static void main(String[] args) throws FindFailed, InterruptedException {
        Screen s = new Screen();
        App app = App.open("google-chrome --incognito");
        app.waitForWindow();
        s.type("gametwist.com");
        s.type(Key.ENTER);

        s.setAutoWaitTimeout(20);

        s.wait("src/main/resources/gametwist_nickname.png").click();
        s.type("USERNAME");
        s.wait("src/main/resources/gametwist_password.png").click();
        s.type("PASSWORD");
        s.type(Key.ENTER);

        s.wait("src/main/resources/gametwist_casino.png").click();

        s.wait("src/main/resources/gametwist_roulette.png").click();

        s.wait("src/main/resources/roulette_table.png").click();

        s.waitVanish("src/main/resources/text_loading.png");

        for (int i = 0; i <= 3; i++) {
            s.wait("src/main/resources/table_black.png").click();
            s.click("src/main/resources/button_quickspin.png");
            Match m = s.exists("src/main/resources/result_black.png");
            System.out.println(m);
        }
    }
}