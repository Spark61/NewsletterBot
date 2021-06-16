package de.azraanimating.newsletterbot.main;

import de.azraanimating.newsletterbot.config.Config;

import java.io.File;

public class Initializer {
    public static void main(String[] args) {

        try {
            Config config = Config.fromFile(new File("config.json"));

            System.out.println(
                    "                                                            \n" +
                            " _____               _     _   _              _____     _   \n" +
                            "|   | |___ _ _ _ ___| |___| |_| |_ ___ ___   | __  |___| |_ \n" +
                            "| | | | -_| | | |_ -| | -_|  _|  _| -_|  _|  | __ -| . |  _|\n" +
                            "|_|___|___|_____|___|_|___|_| |_| |___|_|    |_____|___|_|  \n" +
                            "                                                            \n" +
                            "Developed by AzraAnimating for North_Station\n\n"
            );

            NewsletterBot newsletterBot = new NewsletterBot(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
