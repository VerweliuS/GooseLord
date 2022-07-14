package me.verwelius.bot.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.verwelius.bot.models.Emote;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageHandler extends ListenerAdapter {

    List<Emote> emotes;
    String prefix;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User user = event.getAuthor();
        Member member = event.getMember();
        Message message = event.getMessage();
        String raw = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        if (user.isBot()) return;

        Emote emote;
        if (event.isFromGuild() && raw.startsWith(prefix) && (emote = getEmoteByComm(raw)) != null) {

            new Thread(() -> {
                channel.sendTyping().queue();

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.WHITE);
                eb.setDescription(emote.getHeader());
                eb.setImage(emote.getImageUrl());
                eb.setAuthor(member.getEffectiveName(), null, user.getAvatarUrl());
                eb.setFooter(user.getAsTag());

                channel.sendMessageEmbeds(eb.build()).queue();
            }).start();

        }

    }

    private Emote getEmoteByComm(String raw) {
        for (Emote emote : emotes) {
            if (emote.getName().equals(raw.substring(prefix.length()))) return emote;
        }
        return null;
    }

}
