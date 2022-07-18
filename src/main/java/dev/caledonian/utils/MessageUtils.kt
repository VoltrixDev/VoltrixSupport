package dev.caledonian.utils

import java.util.StringJoiner

class MessageUtils {

    companion object {

        // Phrase checking
        fun containsPhrases(inputString: String, phrases: Array<String>): Boolean {
            if(isDisqualifyingStarter(inputString)) return false
            val splitInput = removePunctuation(inputString).split(" ")

            for(phrase in phrases) {
                val phraseKeywords = phrase.split(" ")
                var n = 0;

                for(word in splitInput) {
                    for(keyword in phraseKeywords) {
                        if(word.contentEquals(keyword, true)) n++
                    }

                }
                if(n == phraseKeywords.size) return true
                n = 1
            }
            return false
        }

        fun containsWords(inputString: String, words: Array<String>): Boolean {
            val formattedInput = removePunctuation(inputString)
            if(isDisqualifyingStarter(formattedInput)) return false

            for(word in words) {
                for(split in formattedInput.split(" ")) {
                    if(split.lowercase().contains(word)) return true
                }
            }
            return false
        }
        fun containsWordsIfQuestion(inputString: String, words: Array<String>): Boolean {
            if(isQuestion(removePunctuation(inputString))) {
                return containsWords(inputString, words)
            }
            return false
        }

        // Utils for da utils
        private fun isQuestion(inputString: String): Boolean {
            val keywords = arrayOf("what", "how", "where", "wonder", "wondering")
            for(keyword in keywords) {
                val formattedInput = removePunctuation(inputString)
                if(formattedInput.lowercase().startsWith(keyword)) return true
                if(formattedInput.lowercase().endsWith(keyword)) return true
            }
            return false
        }

        private fun isDisqualifyingStarter(inputString: String): Boolean {
            val keywords = arrayOf("was", "were", "just", "you", "read")

            for(keyword: String in keywords) {
                for(word: String in removePunctuation(inputString).split(" ")) {
                    if(word.lowercase() == keyword) return true
                }
            }
            return false
        }

        private fun removePunctuation(inputString: String): String {
            var inputStringFinal = ""
            val punctuation: Array<String> = arrayOf(".", ",", "!", "?", ";", ":", "\"", "'", "`", "~", "`", "^", "&", "*", "(", ")", "[", "]", "{", "}", "\\", "/", "|", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-")

            for(punctuationChar in punctuation) {
                inputStringFinal = inputString.replace(punctuationChar, "")
            }
            inputStringFinal = inputStringFinal.replace("I", "")
            inputStringFinal = inputStringFinal.replace("you", "")

            return inputStringFinal
        }
    }
}