package vsr.frogic;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
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

/**
 * Handles commands and interactions for VSRBot
 *
 * @author Connor-184 / Frogic
 */

public class Commands extends ListenerAdapter {
    public final String prefix = "!";
    public final String strats = "src/main/resources/strats.json";
    public final String contents = new String(Files.readAllBytes(Paths.get(strats)));
    public final String[] maps = new String[]{"Ascent", "Bind", "Breeze", "Fracture", "Haven", "Icebox", "Split", "General"};
    public final EmbedBuilder initial = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Choose a Map: ").setColor(Color.RED);

    public Commands() throws IOException {
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Gets message sent in discord channel
        String[] args = event.getMessage().getContentRaw().split(" ");
        MessageChannel channel = event.getChannel();

        // !start
        if (args[0].equalsIgnoreCase(prefix + "start")) {

            // Buttons for action lists
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

            // Sets up embed for choosing a map
            channel.sendMessageEmbeds(initial.build()).setActionRows(ActionRow.of(mapButtons), ActionRow.of(mapButtons2)).queue(message -> initial.setDescription("Choose a Map: "));

        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        // Buttons for action lists
        List<Button> sideButtons = new ArrayList<>();
        sideButtons.add(Button.primary("Attacker", "Attacker"));
        sideButtons.add(Button.primary("Defender", "Defender"));
        sideButtons.add(Button.danger("End", "End Game"));
        List<Button> strats = new ArrayList<>();
        List<Button> genStrats = new ArrayList<>();
        strats.add(Button.primary("Strat", "Generate Strat"));
        genStrats.add(Button.primary("Strat", "Generate Strat"));
        strats.add(Button.primary("Change", "Change Sides"));
        strats.add(Button.danger("End", "End Game"));
        genStrats.add(Button.danger("End", "End Game"));

        // for keeping track of map/side
        MessageEmbed a;
        String desc;

        //Acknowledge the button press
        event.deferEdit().queue();

        // handles each button event
        switch (event.getButton().getId()) {
            case "Ascent" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(ascent -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Ascent \n Choose a side: ").setColor(Color.RED);
                ascent.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Bind" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(bind -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Bind \n Choose a side: ").setColor(Color.RED);
                bind.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Breeze" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(breeze -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Breeze \n Choose a side: ").setColor(Color.RED);
                breeze.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Fracture" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(fracture -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Fracture \n Choose a side: ").setColor(Color.RED);
                fracture.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Haven" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(haven -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Haven \n Choose a side: ").setColor(Color.RED);
                haven.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Icebox" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(icebox -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Icebox \n Choose a side: ").setColor(Color.RED);
                icebox.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "Split" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(split -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: Split \n Choose a side: ").setColor(Color.RED);
                split.editMessageEmbeds(embed.build()).setActionRow(sideButtons).queue();
            });
            case "General" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(split -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Any Map \n Choose an Option: ").setColor(Color.RED);
                split.editMessageEmbeds(embed.build()).setActionRow(genStrats).queue();
            });
            case "Attacker" -> {
                a = event.getMessage().getEmbeds().get(0);
                desc = a.getDescription();
                event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(attack -> {
                    EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Attacker" + "\n " + "Choose an option: ").setColor(Color.RED);
                    attack.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                });
            }
            case "Defender" -> {
                a = event.getMessage().getEmbeds().get(0);
                desc = a.getDescription();
                event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(defend -> {
                    EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Defender" + "\n " + "Choose an option: ").setColor(Color.RED);
                    defend.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                });
            }
            // generates a new strat
            case "Strat" -> {
                a = event.getMessage().getEmbeds().get(0);
                desc = a.getDescription();
                // attacker
                if (getSide(desc)) {
                    event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(strat -> {
                        EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Attacker" + "\n" + "Strat: " + getStrat(getMap(desc), getSide(desc))).setColor(Color.RED);
                        strat.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                    });
                    // defender
                } else {
                    event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(strat -> {
                        EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Defender" + "\n" + "Strat: " + getStrat(getMap(desc), getSide(desc))).setColor(Color.RED);
                        strat.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                    });
                }
            }
            // switches sides between attacker and defender
            case "Change" -> {
                a = event.getMessage().getEmbeds().get(0);
                desc = a.getDescription();

                // attacker -> defender
                if (getSide(desc)) {
                    event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(change -> {
                        EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Defender" + "\n " + "Choose an option: ").setColor(Color.RED);
                        change.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                    });
                    // defender -> attacker
                } else {
                    event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(change -> {
                        EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("Map: " + maps[getMap(desc)] + "\n " + "Side: Attacker" + "\n " + "Choose an option: ").setColor(Color.RED);
                        change.editMessageEmbeds(embed.build()).setActionRow(strats).queue();
                    });
                }
            }
            case "End" -> event.getChannel().editMessageEmbedsById(event.getMessageId()).queue(end -> {
                EmbedBuilder embed = new EmbedBuilder().setTitle("Strategy Roulette").setDescription("The Game is Over. Thanks for Playing!").setColor(Color.RED);
                end.editMessageEmbeds(embed.build()).setActionRows().queue();
            });
        }
    }

    /**
     * Generates a random strategy based on map and side chosen
     *
     * @param attacker true if attacker false if defender
     * @param mapNum   corresponds to the map chosen
     * @return a string containing the generated strategy
     */

    private String getStrat(int mapNum, boolean attacker) {

        // Gets strats from strats.json and creates new json object
        JSONObject jsonObject = new JSONObject(contents);
        JSONArray genstrat;
        JSONArray mapstrat;
        String strat = "";
        // 1/5 chance to get map-specific strat
        int rand1 = ThreadLocalRandom.current().nextInt(0, 6);

        // map specific strat
        if (mapNum != 7 || rand1 != 1) {
            genstrat = jsonObject.getJSONArray("General");
            int rand2 = ThreadLocalRandom.current().nextInt(0, genstrat.length());
            strat = genstrat.getString(rand2);
        }
        if (mapNum != 7 && rand1 == 1) {
            if (attacker) {
                mapstrat = jsonObject.getJSONArray(maps[mapNum] + "A");
            } else {
                mapstrat = jsonObject.getJSONArray(maps[mapNum] + "D");
            }
            int rand2 = ThreadLocalRandom.current().nextInt(0, mapstrat.length());
            strat = mapstrat.getString(rand2);
        }

        return strat;
    }

    /**
     * Gets the map chosen
     *
     * @param desc the description of the embed containing the map selected
     * @return an int containing the "map number"
     */
    private int getMap(String desc) {

        if (desc.contains("Ascent")) {
            return 0;
        }
        if (desc.contains("Bind")) {
            return 1;
        }
        if (desc.contains("Breeze")) {
            return 2;
        }
        if (desc.contains("Fracture")) {
            return 3;
        }
        if (desc.contains("Haven")) {
            return 4;
        }
        if (desc.contains("Icebox")) {
            return 5;
        }
        if (desc.contains("Split")) {
            return 6;
        }

        return 7;
    }

    /**
     * Gets the side chosen
     *
     * @param desc the description of the embed containing the side selected
     * @return true if attacker and false if defender
     */
    private boolean getSide(String desc) {

        return !desc.contains("Defender");

    }
}