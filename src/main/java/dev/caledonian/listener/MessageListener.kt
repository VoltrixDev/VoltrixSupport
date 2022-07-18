package dev.caledonian.listener

import dev.caledonian.VoltrixSupportBot
import dev.caledonian.utils.MessageUtils
import dev.caledonian.utils.PremadeEmbeds
import dev.caledonian.utils.Utils
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button

class MessageListener(val main: VoltrixSupportBot, val jda: JDA, val embeds: PremadeEmbeds) : ListenerAdapter() {

    override fun onMessageReceived(e: MessageReceivedEvent) {
        if (e.author.isBot) return
        if (e.channel.idLong == main.config.getJSONObject("auto-support").getLong("channel-id")) {
            val message = e.message.contentRaw
            Utils.sendConsoleLog("Message received in support channel: $message")

            if (MessageUtils.containsWordsIfQuestion(message, arrayOf("download", "install")) or MessageUtils.containsPhrases(message, arrayOf("download client", "client link", "download link"))) {
                // Download client
                e.message.reply(String.format(":wave: Hey %s, if you're looking for the download link, you can find it by clicking the button below! Hopefully this helps!", e.author.asMention)).setActionRow(
                    Button.link("https://voltrix.caledonian.dev/", "Download")).queue()
                return
            }
            if(MessageUtils.containsWordsIfQuestion(message, arrayOf("wiki", "docs", "install", "installing", "installed", "error"))
                or MessageUtils.containsPhrases(message, arrayOf("dont know how", "help install", "how install", "help error", "dont work", "what do install"))) {
                e.message.reply(String.format("%s :wave:, your question could already be answered! Check our documentation by clicking the button below!", e.author.asMention)).setActionRow(
                    Button.link("https://voltrix.caledonian.dev/", "Client wiki")).queue()
                return
            }
            if(MessageUtils.containsWordsIfQuestion(message, arrayOf("support", "ticket"))
                or MessageUtils.containsPhrases(message, arrayOf("staff support", "voltrix support", "client support", "report error"))) {
                e.message.reply(String.format("%s :wave:, it looks like you're trying to create a ticket. If so, check the <#868239889341247558> channel, and click the button!", e.author.asMention)).queue()
                return
            }
            if(MessageUtils.containsPhrases(message, arrayOf("create mod", "add own mod", "install mod", "implement mod"))) {
                e.message.reply(String.format("%s :wave:, Have you checked our wiki yet? It should have everything you need!", e.author.asMention)).setActionRow(
                    Button.link("https://voltrix.caledonian.dev/smp/api-docs/spigot-events", "Spigot API")).queue()
                return
            }
        }
    }
}