package de.azraanimating.newsletterbot.messenger;

import de.azraanimating.newsletterbot.main.NewsletterBot;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MessageManager {

    private final NewsletterBot newsletterBot;

    private final String guildID, channelID, messageID;
    private final String reactionEmote;

    public MessageManager(NewsletterBot newsletterBot, String guildID, String channelID, String messageID, String reactionEmote) {
        this.newsletterBot = newsletterBot;

        this.guildID = guildID;
        this.channelID = channelID;
        this.messageID = messageID;

        this.reactionEmote = reactionEmote;
    }

    public void broadcastMessage(String message) {
        try {
            MessageHistory triggerMessageHistory = Objects.requireNonNull(Objects.requireNonNull(this.newsletterBot.getJda()
                    .getGuildById(this.guildID))
                    .getTextChannelById(this.channelID))
                    .getHistory();
            List<Message> retrievedMessages = triggerMessageHistory.retrievePast(100).complete(true);
            AtomicReference<Message> triggerMessage = new AtomicReference<>(null);
            retrievedMessages.forEach(retrievedMessage -> {
                if (retrievedMessage.getId().equals(this.messageID)) {
                    triggerMessage.set(retrievedMessage);
                }
            });
            if (triggerMessage.get() != null) {
                List<MessageReaction> reactions = triggerMessage.get().getReactions();
                if (!reactions.isEmpty()) {
                    ArrayList<String> alreadyHandledEmotes = new ArrayList<>();
                    reactions.forEach(messageReaction -> {
                        String reactionEmote = messageReaction.getReactionEmote().toString().substring(3);
                        if (reactionEmote.equalsIgnoreCase(this.reactionEmote) && !alreadyHandledEmotes.contains(reactionEmote)) {
                            try {
                                messageReaction.retrieveUsers().complete(true).forEach(user -> {
                                    try {
                                        PrivateChannel privateChannel = user.openPrivateChannel().complete(true);
                                        privateChannel.sendMessage(message).queue();
                                        System.out.println("Sent Message to " + user.getAsTag());
                                    } catch (RateLimitedException e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (RateLimitedException e) {
                                e.printStackTrace();
                            }
                            alreadyHandledEmotes.add(reactionEmote);
                        }
                    });
                }
            } else {
                System.out.println("Bitte Überprüfen Sie Ihre Config Einträge.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
