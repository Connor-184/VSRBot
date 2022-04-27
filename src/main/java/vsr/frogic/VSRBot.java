package vsr.frogic;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * Builds VSRBot
 *
 * @author Connor-184 / Frogic
 *
 * Add me on discord if you have any problems/suggestions Frogic#4870
 */

public class VSRBot {


    public static void main(String[] args) throws LoginException, IOException {

        Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMalformed().ignoreIfMissing().load();

        JDA VSRBot = JDABuilder.createDefault(dotenv.get("TOKEN"))
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .setActivity(Activity.playing("VALORANT"))
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new Commands())
                .build();
    }
}
