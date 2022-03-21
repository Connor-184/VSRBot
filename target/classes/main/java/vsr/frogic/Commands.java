package vsr.frogic;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Commands extends ListenerAdapter {
    public String prefix = "!";
    public String strats = "src/main/resources/strats.json";
    public String contents = new String(Files.readAllBytes(Paths.get(strats)));
    public EmbedBuilder initial = new EmbedBuilder().setTitle("Strategy Roulette").setColor(Color.RED).setDescription("Select a Map: ");
    Long messageID;
    int mapNum = 0;
    public boolean attacker;

    public Commands() throws IOException {
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        MessageChannel channel = event.getChannel();

        if (args[0].equalsIgnoreCase(prefix + "start")) {

            List<Button> mapButtons = new ArrayList<>();
            List<Button> mapButtons2 = new ArrayList<>();

            mapButtons.add(Button.primary("Ascent", "Ascent"));
            mapButtons.add(Button.primary("Bind", "Bind"));
            mapButtons.add(Button.primary("Breeze", "Breeze"));
            mapButtons.add(Button.primary("Fracture", "Fracture"));
            mapButtons2.add(Button.primary("Haven", "Haven"));
            mapButtons2.add(Button.primary("Icebox", "Icebox"));
            mapButtons2.add(Button.primary("Split", "Split"));
            mapButtons2.add(Button.primary("General", "Any Map"));

            channel.sendMessageEmbeds(initial.build()).setActionRows(ActionRow.of(mapButtons), ActionRow.of(mapButtons2)).queue(message -> {
                initial.setDescription("Choose a Side: ");
                messageID = message.getIdLong();
            });

        }

        if (args[0].equalsIgnoreCase(prefix + "stop")) {
            channel.editMessageEmbedsById(messageID).queue(end -> {
                initial.setDescription("The Game is Over. Thanks for Playing!");
                end.editMessageEmbeds(initial.build()).setActionRows().queue();
            });
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        MessageChannel channel = event.getChannel();

        List<Button> sideButtons = new ArrayList<>();
        sideButtons.add(Button.primary("Attacker", "Attacker"));
        sideButtons.add(Button.primary("Defender", "Defender"));

        List<Button> strats = new ArrayList<>();
        List<Button> genStrats = new ArrayList<>();
        strats.add(Button.primary("Strat", "Generate Strat"));
        genStrats.add(Button.primary("Strat", "Generate Strat"));
        strats.add(Button.primary("Change", "Change Sides"));
        strats.add(Button.danger("End", "End Game"));
        genStrats.add(Button.danger("End", "End Game"));

        String[] maps = new String[] {"Ascent", "Bind", "Breeze", "Fracture", "Haven", "Icebox", "Split", "General"};

        event.deferEdit().queue();
        switch (event.getButton().getId()) {
            case "Ascent":
                channel.editMessageEmbedsById(messageID).queue(ascent -> {
                    initial.setDescription("Map: Ascent \n Choose a side: ");
                    ascent.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 0;
                break;
            case "Bind":
                channel.editMessageEmbedsById(messageID).queue(bind -> {
                    initial.setDescription("Map: Bind \n Choose a Side: ");
                    bind.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 1;
                break;
            case "Breeze":
                channel.editMessageEmbedsById(messageID).queue(breeze -> {
                    initial.setDescription("Map: Breeze \n Choose a Side: ");
                    breeze.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 2;
                break;
            case "Fracture":
                channel.editMessageEmbedsById(messageID).queue(fracture -> {
                    initial.setDescription("Map: Fracture \n Choose a Side: ");
                    fracture.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 3;
                break;
            case "Haven":
                channel.editMessageEmbedsById(messageID).queue(haven -> {
                    initial.setDescription("Map: Haven \n Choose a Side: ");
                    haven.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 4;
                break;
            case "Icebox":
                channel.editMessageEmbedsById(messageID).queue(icebox -> {
                    initial.setDescription("Map: Icebox \n Choose a Side: ");
                    icebox.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 5;
                break;

            case "Split":
                channel.editMessageEmbedsById(messageID).queue(split -> {
                    initial.setDescription("Map: Split \n Choose a Side: ");
                    split.editMessageEmbeds(initial.build()).setActionRow(sideButtons).queue();
                });
                mapNum = 6;
                break;
            case "General":
                channel.editMessageEmbedsById(messageID).queue(split -> {
                    initial.setDescription("Any Map \n Choose an Option: ");
                    split.editMessageEmbeds(initial.build()).setActionRow(genStrats).queue();
                    mapNum = 7;
                });
                break;
            case "Attacker":
                channel.editMessageEmbedsById(messageID).queue(attack -> {
                    initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Attacker" + "\n " + "Choose an option: ");
                    attack.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                });
                attacker = true;
                break;
            case "Defender":
                channel.editMessageEmbedsById(messageID).queue(defend -> {
                    initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Defender" + "\n " + "Choose an option: ");
                    defend.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                });
                attacker = false;
                break;
            case "Strat":
                JSONObject jsonObject = new JSONObject(contents);
                JSONArray genstrat;
                JSONArray mapstrat;
                int rand1 = ThreadLocalRandom.current().nextInt(0, 5);

                if (!(mapNum == 7)) {
                    if (rand1 == 1) {
                        if (attacker) {
                            mapstrat = jsonObject.getJSONArray(maps[mapNum] + "A");
                        } else {
                            mapstrat = jsonObject.getJSONArray(maps[mapNum] + "D");
                        }
                        int rand2 = ThreadLocalRandom.current().nextInt(0, mapstrat.length());
                        channel.editMessageEmbedsById(messageID).queue(strat -> {
                            initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Defender" + "\n" + "Strat: " + mapstrat.getString(rand2));
                            strat.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                        });
                    } else {
                        if (attacker) {
                            genstrat = jsonObject.getJSONArray("General");
                            int rand2 = ThreadLocalRandom.current().nextInt(0, genstrat.length());
                            channel.editMessageEmbedsById(messageID).queue(strat -> {
                                initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Attacker" + "\n " + "Strat: " + genstrat.getString(rand2));
                                strat.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                            });
                        } else {
                            genstrat = jsonObject.getJSONArray("General");
                            int rand2 = ThreadLocalRandom.current().nextInt(0, genstrat.length());
                            channel.editMessageEmbedsById(messageID).queue(strat -> {
                                initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Defender" + "\n " + "Strat: " + genstrat.getString(rand2));
                                strat.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                            });
                        }
                    }
                } else {
                    genstrat = jsonObject.getJSONArray("General");
                    int rand2 = ThreadLocalRandom.current().nextInt(0, genstrat.length());
                    channel.editMessageEmbedsById(messageID).queue(strat -> {
                        initial.setDescription("Any Map" + "\n " + "Strat: " + genstrat.getString(rand2));
                        strat.editMessageEmbeds(initial.build()).setActionRow(genStrats).queue();
                    });
                }

                break;
            case "Change":

                if (attacker) {
                    attacker = false;
                    channel.editMessageEmbedsById(messageID).queue(change -> {
                        initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Defender" + "\n " + "Choose an option: ");
                        change.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                    });

                } else {
                    attacker = true;
                    channel.editMessageEmbedsById(messageID).queue(change -> {
                        initial.setDescription("Map: " + maps[mapNum] + "\n " + "Side: Attacker" + "\n" + "Choose an option: ");
                        change.editMessageEmbeds(initial.build()).setActionRow(strats).queue();
                    });
                }

                break;
            case "End":
                channel.editMessageEmbedsById(messageID).queue(end -> {
                    initial.setDescription("The Game is Over. Thanks for Playing!");
                    end.editMessageEmbeds(initial.build()).setActionRows().queue();
                });
                break;
        }
    }

}