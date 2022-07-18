/*
 * Copyright (c) 2/26/22, 8:55 PM, Caledonian. (https://www.caledonian.dev)
 *
 * Not intended for distribution without explicit permission from the original author.
 */

package dev.caledonian.utils;

import dev.caledonian.VoltrixSupportBot;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static VoltrixSupportBot main;
    private static JDA jda;

    public Utils(VoltrixSupportBot main, JDA jda){
        this.main = main;
        this.jda = jda;
    }

    public static Color fromHex(String colorStr) {
        String finalColorString = colorStr;
        return new Color(
                Integer.valueOf( finalColorString.substring( 1, 3 ), 16 ),
                Integer.valueOf( finalColorString.substring( 3, 5 ), 16 ),
                Integer.valueOf( finalColorString.substring( 5, 7 ), 16 ) );
    }


    public static String getLogTime(){
        String timeStamp;
        timeStamp = new SimpleDateFormat("M/d/yy hh:mm:ss:SSSS z").format(new Date());

        return String.format("[%s] ", timeStamp);
    }
    public static void sendConsoleLog(String message, Object... components){
        System.out.printf("%s %s%n", getLogTime(), String.format(message, components));
    }
}
