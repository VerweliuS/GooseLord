package me.verwelius.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.verwelius.bot.models.Config;
import me.verwelius.bot.handlers.MessageHandler;
import me.verwelius.bot.utils.FileUtils;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Paths;

public class GooseLordBot {

    public static void main(String[] args) throws IOException, LoginException, InterruptedException {

        File configFile = Paths.get("config.yaml").toFile();

        if (!FileUtils.createIfNotExists(configFile)) {
            System.err.println("Specify variables in " + configFile.getName());
            System.exit(0);
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(configFile, Config.class);

        JDABuilder.createDefault(config.getToken())
                .addEventListeners(new MessageHandler(config.getEmotes(), config.getPrefix()))
                .setActivity(Activity.playing(config.getStatus()))
                .build()
                .awaitReady();

    }

}
