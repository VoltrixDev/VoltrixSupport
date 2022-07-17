package dev.caledonian.events

import dev.caledonian.VoltrixSupportBot
import dev.caledonian.utils.PremadeEmbeds
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener(val main: VoltrixSupportBot, val jda: JDA, val embeds: PremadeEmbeds): ListenerAdapter() {

    override fun onMessageReceived(e: MessageReceivedEvent) {
        if(e.author.isBot) return
        e.channel.sendMessageEmbeds(embeds.success("Title", "Description").build()).queue()
    }
}