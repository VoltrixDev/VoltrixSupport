package dev.caledonian.utils

import dev.caledonian.VoltrixSupportBot
import lombok.SneakyThrows
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA

class PremadeEmbeds(val main: VoltrixSupportBot, val jda: JDA) {

    // SUCCESS
        val footerImg = "https://i.imgur.com/xBCwc11.png"
        val footerText = "Copyright © Voltrix Studios 2022"

        @SneakyThrows
        fun success(title: String, message: String, cause: String?): EmbedBuilder {
            val eb = EmbedBuilder()

            eb.setColor(Utils.fromHex("#2dae57"))
            eb.setTitle(title)
            if(cause != null) {
                eb.setDescription(String.format("%s \n\n ```json\n\"%s\"\n```", message, cause))
            }else eb.setDescription(message)

            eb.setThumbnail("https://i.imgur.com/gzV8USQ.png")
            eb.setFooter(footerText, footerImg)

            return eb
        }
        @SneakyThrows
        fun success(title: String, message: String): EmbedBuilder {
            return success(title, message, null)
        }

        // NEUTRAL
        @SneakyThrows
        fun neutral(title: String, message: String, cause: String?): EmbedBuilder {
            val eb = EmbedBuilder()

            eb.setColor(Utils.fromHex("#d6d6d6"))
            eb.setTitle(title)
            if(cause != null) {
                eb.setDescription(String.format("%s \n\n ```\n%s\n```", message, cause))
            }else eb.setDescription(message)

            eb.setThumbnail("https://i.imgur.com/gzV8USQ.png")
            eb.setFooter(footerText, footerImg)

            return eb
        }
        @SneakyThrows
        fun neutral(title: String, message: String): EmbedBuilder {
            return neutral(title, message, null)
        }

        // WARNING
        fun warning(title: String, message: String, cause: String?): EmbedBuilder {
            val eb = EmbedBuilder()

            eb.setColor(Utils.fromHex("#fe3f3f"))
            eb.setTitle(title)
            if(cause != null) {
                eb.setDescription(String.format("%s \n\n ```css\n[%s]\n```", message, cause))
            }else eb.setDescription(message)

            eb.setThumbnail("https://i.imgur.com/gzV8USQ.png")
            eb.setFooter(footerText, footerImg)

            return eb
        }
        @SneakyThrows
        fun warning(title: String, message: String): EmbedBuilder {
            return warning(title, message, null)
        }

        @SneakyThrows
        fun error(title: String, message: String, cause: String?): EmbedBuilder {
            val eb = EmbedBuilder()

            eb.setColor(Utils.fromHex("#fe3f3f"))
            eb.setTitle(title)
            if(cause != null) {
                eb.setDescription(String.format("%s \n\n ```diff\n- %s\n```", message, cause))
            }else eb.setDescription(message)

            eb.setThumbnail("https://i.imgur.com/gzV8USQ.png")
            eb.setFooter(footerText, footerImg)

            return eb
        }
        @SneakyThrows
        fun error(title: String, message: String): EmbedBuilder {
            return error(title, message, null)
        }
}