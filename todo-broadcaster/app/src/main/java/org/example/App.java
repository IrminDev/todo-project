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

                try {
                    DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
                    webhook.setContent("A new todo:" + response);
                    webhook.setAvatarUrl("https://avatars.githubusercontent.com/u/78762447?s=400&u=5e2a40972282425a80b884c5802a87900b6431a0&v=4");
                    webhook.setUsername("IrminDev");
                    webhook.setTts(true);
                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                            .setTitle("New todo!")
                            .setDescription("A new todo has been added!")
                            .setColor(Color.green));
                    webhook.execute(); //Handle exception
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}