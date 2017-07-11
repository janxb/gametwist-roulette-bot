package de.janxb.grb;


import org.sikuli.script.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws FindFailed, InterruptedException {
        Screen s = new Screen();
        s.setAutoWaitTimeout(20);

        /*
        App app = App.open("google-chrome --incognito");
        app.waitForWindow();
        s.type("gametwist.com");
        s.type(Key.ENTER);

        s.wait("src/main/resources/website_input_nickname.png").click();
        s.type("USERNAME");
        s.wait("src/main/resources/website_input_password.png").click();
        s.type("PASSWORD");
        s.type(Key.ENTER);

        s.wait("src/main/resources/website_button_casino.png").click();

        s.wait("src/main/resources/website_button_roulette.png").click();

        s.wait("src/main/resources/app_button_table.png").click();

        s.waitVanish("src/main/resources/app_text_loadmoney.png");
        */

        int lostCount = 0;
        int maxLostCount = 10;

        Pattern[] tableButtonPatterns = {
                new Pattern("src/main/resources/app_table_black.png").exact(),
                new Pattern("src/main/resources/app_table_red.png").exact(),
        };

        // loop tactics: increment bet by one step for each loss, decrement for each win
        //noinspection InfiniteLoopStatement
        while (true) { // main application loop

            Pattern buttonPattern = tableButtonPatterns[new Random().nextInt(tableButtonPatterns.length)];

            Match tableButton = s.wait(buttonPattern);
            for (int clickCount = 0; clickCount < (lostCount + 1); clickCount++) { // loop for placing the bets
                tableButton.click();
            }
            s.wait("src/main/resources/app_button_quickspin.png").click();
            s.wait(buttonPattern);

            // we have to use this exact pattern, because sikulix uses a default accuracy of 70%, this matches every text
            Pattern exactGameResultPattern = new Pattern("src/main/resources/app_text_gamelost.png").exact();
            boolean isGameWin = (null == s.exists(exactGameResultPattern, 0.1));

            if (isGameWin) {
                if (lostCount > 0) lostCount--;
                System.out.println("Game won, decrementing lostCount");
            } else {
                lostCount++;
                System.out.println("Game lost, incrementing lostCount");
                if (lostCount >= maxLostCount) {
                    lostCount = 0;
                    System.out.println("maxLostCount overstepped, resetting lostCount");
                }
            }
        }

/*
        // loop tactics: double bet for each loss, reset to base on each win
        //noinspection InfiniteLoopStatement
        while (true) { // main application loop

            Pattern buttonPattern = tableButtonPatterns[new Random().nextInt(tableButtonPatterns.length)];

            Match tableButton = s.wait(buttonPattern);
            for (int clickCount = 0; clickCount < (Math.pow(2, lostCount)); clickCount++) { // loop for placing the bets
                tableButton.click();
            }
            s.wait("src/main/resources/app_button_quickspin.png").click();
            s.wait(buttonPattern);

            // we have to use this exact pattern, because sikulix uses a default accuracy of 70%, this matches every text
            Pattern exactGameResultPattern = new Pattern("src/main/resources/app_text_gamelost.png").exact();
            boolean isGameWin = (null == s.exists(exactGameResultPattern, 0.1));

            if (isGameWin) {
                lostCount = 0;
                System.out.println("Game won, resetting lostCount");
            } else {
                lostCount++;
                System.out.println("Game lost, incrementing lostCount");
                if (lostCount >= maxLostCount) {
                    lostCount = 0;
                    System.out.println("maxLostCount overstepped, resetting lostCount");
                }
            }
        }
        */
    }
}