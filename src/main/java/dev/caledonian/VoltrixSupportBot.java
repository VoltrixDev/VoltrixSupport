package dev.caledonian;

import dev.caledonian.events.MessageListener;
import dev.caledonian.utils.PremadeEmbeds;
import dev.caledonian.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class VoltrixSupportBot {

    @Getter private JDA jda;
    @Getter private PremadeEmbeds embeds;

    private long time = 0;
    private long startTime;

    public static void main(String[] args) {
        VoltrixSupportBot botMain = new VoltrixSupportBot();
        botMain.startBot();
    }

    @SneakyThrows
    private void startBot() {
        time = System.currentTimeMillis();
        Utils.sendConsoleLog("[BOT] Load VoltrixSupportBot by Caledonian");

        Utils.sendConsoleLog("[BOT] Connecting to the bot using the provided discord token.");
        try {
            JDABuilder builder = JDABuilder.createDefault(getConfig().getString("token"))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL);

            jda = builder.build().awaitReady();
            Utils.sendConsoleLog("[BOT] Successfully connected to %s with a valid token. Took %sms.", jda.getSelfUser().getAsTag(), System.currentTimeMillis() - time);
        }catch (Exception ex) {
            Utils.sendConsoleLog("[FAILED] [BOT] Failed to connect to the bot. Failed in %sms. Stacktrace: %s", System.currentTimeMillis() - time, ex.getStackTrace());
        }

        // Activities
        if(getConfig().getJSONObject("activity").getBoolean("enabled")) {
            Utils.sendConsoleLog("[BOT] Registering cosmetic status for %s", jda.getSelfUser().getAsTag());
            time = System.currentTimeMillis();
            jda.getPresence().setStatus(OnlineStatus.fromKey(getConfig().getJSONObject("activity").getString("status")));
            jda.getPresence().setActivity(Activity.watching(getConfig().getJSONObject("activity").getString("activity")));
            Utils.sendConsoleLog("[BOT] Successfully set status for %s", jda.getSelfUser().getAsTag());
        }

        Utils.sendConsoleLog("[BOT] Registering command classes");
        time = System.currentTimeMillis();

        embeds = new PremadeEmbeds(this, jda);
        registerEvents();
    }

    public void registerEvents() {
        jda.addEventListener(new MessageListener(this, jda, embeds));
    }

    // Configuration
    @SneakyThrows
    public JSONObject getConfig() {
        try {
            JSONObject config = new JSONObject(new String(Files.readAllBytes(Paths.get("config.json"))));
            return config;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
