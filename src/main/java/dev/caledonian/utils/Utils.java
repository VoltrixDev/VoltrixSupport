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

    @SneakyThrows
    public static boolean isDeveloper(User user){
        return developers().contains(user.getId());
    }

    public static String removePunctuation(String inputString) {
        String[] punctuation = new String[] { ".", ",", "!", "?", ";", ":", "\"", "'", "`", "~", "`", "^", "&", "*", "(", ")", "[", "]", "{", "}", "\\", "/", "|", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-" };
        for(String punctuationChar : punctuation) {
            inputString = inputString.replace(punctuationChar, "");
            inputString = inputString.replace("I", "");
            inputString = inputString.replace("you", "");
        }
        return inputString;
    }

    public static boolean containsWords(String inputString, String[] words) {
        if(isDisqualifyingStarter(removePunctuation(inputString))) return false;
        for(String word : words) {
            for(String split : removePunctuation(inputString).split(" ")) {
                if(split.toLowerCase().contains(word)) return true;
            }
        }
        return false;
    }

    public static boolean containsWordsIfQuestion(String inputString, String[] words) {
        if(isQuestion(removePunctuation(inputString))){
            return containsWords(removePunctuation(inputString), words);
        }
        return false;
    }

    public static boolean containsPhrases(String inputString, String[] phrases) {
        if(isDisqualifyingStarter(inputString)) return false;
        String[] splitInput = removePunctuation(inputString).split(" ");
        for(String phrase : phrases) {
            String[] phraseKeywords = phrase.split(" ");
            int n = 0;
            for(String word : splitInput) {
                for(String keyword : phraseKeywords) {
                    if(word.equalsIgnoreCase(keyword)) {
                        n = n + 1;
                        System.out.println("word is " + word + "and keyword is " + keyword);
                    }
                }
                System.out.println("phrase keyword is " + phraseKeywords.length);
                System.out.println("n is " + n);
            }
            if(n == phraseKeywords.length) return true;
            n = 1;
        }
        return false;
    }

    public static boolean isQuestion(String inputString) {
        String[] words = {"what", "how", "where", "wonder", "wondering"};
        for(String word : words) {
            if(removePunctuation(inputString).toLowerCase().startsWith(word)) {
                return true;
            }
            if(removePunctuation(inputString).toLowerCase().endsWith(word)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDisqualifyingStarter(String inputString) {
        String[] words = {"was", "were", "just", "you", "read"};
        for(String word : words) {
            for(String x : removePunctuation(inputString).split(" ")) {
                if(Objects.equals(x, word)) return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public static List<String> developers(){
        ArrayList<String> listdata = new ArrayList<>();
        JSONArray jsonArray = main.getConfig().getJSONObject("development").getJSONArray("developers");
        if(jsonArray != null){
            for (int i=0;i<jsonArray.length();i++){
                listdata.add(jsonArray.getString(i));
            }
        }

        return listdata;
    }

    public static void deleteAfter(Message message, int delay){
        message.delete().queueAfter(delay, TimeUnit.SECONDS);
    }

    public static String getLogTime(){
        String timeStamp;
        timeStamp = new SimpleDateFormat("M/d/yy hh:mm:ss:SSSS z").format(new Date());

        return String.format("[%s] ", timeStamp);
    }

    public static Color fromHex(String colorStr) {
        String finalColorString = colorStr;
        return new Color(
                Integer.valueOf( finalColorString.substring( 1, 3 ), 16 ),
                Integer.valueOf( finalColorString.substring( 3, 5 ), 16 ),
                Integer.valueOf( finalColorString.substring( 5, 7 ), 16 ) );
    }

    public static UUID convertToLongUUID(String shortUUID){
        StringBuffer sb = new StringBuffer(shortUUID);
        sb.insert(8, "-");

        sb = new StringBuffer(sb.toString());
        sb.insert(13, "-");

        sb = new StringBuffer(sb.toString());
        sb.insert(18, "-");

        sb = new StringBuffer(sb.toString());
        sb.insert(23, "-");

        return UUID.fromString(sb.toString());
    }

    public static void sendConsoleLog(String message, Object... components){
        System.out.printf("%s %s%n", getLogTime(), String.format(message, components));
    }
}
