package de.azraanimating.newsletterbot.listener;

import de.azraanimating.newsletterbot.main.NewsletterBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class PrivateMessageReceivedListener extends ListenerAdapter {

    private final NewsletterBot newsletterBot;
    private final ArrayList<String> allowedSenders;

    public PrivateMessageReceivedListener(NewsletterBot newsletterBot, String allowedSenders) {
        this.newsletterBot = newsletterBot;
        String[] allowedSendersArray = allowedSenders.split(",");
        this.allowedSenders = new ArrayList<>();
        Collections.addAll(this.allowedSenders, allowedSendersArray);
    }


    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if(this.allowedSenders.contains(event.getAuthor().getId())) {
            this.newsletterBot.getMessageManager().broadcastMessage(event.getMessage().getContentDisplay());
        }
    }

}
