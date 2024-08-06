package org.example;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.Subscription;
import io.nats.client.Dispatcher;

import java.awt.Color;

import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) {
        String natsServer = System.getenv("NATS_SERVER");
        String natsSubject = System.getenv("NATS_SUBJECT");
        String discordWebhookUrl = System.getenv("DISCORD_WEBHOOK_URL");
        String natsQueueGroup = System.getenv("NATS_QUEUE_GROUP"); 

        Options options = new Options.Builder()
            .server(natsServer)
            .connectionListener((connection, event) -> System.out.println("Connection Event: " + event))
            .build();
        try {
            // Establecer conexión con NATS
            Connection natsConnection = Nats.connect(options);

            Dispatcher dispatcher = natsConnection.createDispatcher();

            // Suscribirse al tema
            Subscription subscription = dispatcher.subscribe(natsSubject, natsQueueGroup, (msg) -> {
                String response = new String(msg.getData(), StandardCharsets.UTF_8);
                System.out.println("Message received:" + response);

                if(discordWebhookUrl != null) {
                    try{
                        DiscordWebhook discordWebhook = new DiscordWebhook(discordWebhookUrl);
                        discordWebhook.setContent(response);
                        discordWebhook.setAvatarUrl("https://i.imgur.com/8Ikjw8W.png");
                        discordWebhook.setUsername("NATS Broadcaster");
                        discordWebhook.setTts(false);
                        discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                            .setTitle("NATS Broadcaster")
                            .setDescription(response)
                            .setColor(Color.GREEN));
                        discordWebhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}