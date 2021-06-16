package de.azraanimating.newsletterbot.main;

import de.azraanimating.newsletterbot.config.Config;
import de.azraanimating.newsletterbot.listener.PrivateMessageReceivedListener;
import de.azraanimating.newsletterbot.messenger.MessageManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class NewsletterBot {

    private JDA jda;
    private MessageManager messageManager;

    public NewsletterBot(Config config) throws LoginException {

        if(config.botToken.equals("botToken")) {
            System.out.println("Bitte füllen Sie die erstellte config.json mit den notwendigen Details aus!");
            return;
        }

        this.jda = JDABuilder.createDefault(config.botToken).addEventListeners(new PrivateMessageReceivedListener(this, config.newsletterSenders)).build();

        this.messageManager = new MessageManager(this, config.triggerServerID, config.triggerChannelID, config.triggerMessageID, config.reactionEmote);
    }

    public JDA getJda() {
        return jda;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
