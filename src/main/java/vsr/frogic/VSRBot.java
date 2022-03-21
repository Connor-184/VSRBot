package vsr.frogic;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class VSRBot {


    public static void main(String[] args) throws LoginException, IOException {

        JDA VSRBot = JDABuilder.createDefault(System.getenv("token"))
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .setActivity(Activity.playing("VALORANT"))
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new Commands())
                .build();
    }
}
