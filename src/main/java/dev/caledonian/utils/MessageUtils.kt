package dev.caledonian.utils

import java.util.StringJoiner

class MessageUtils {

    companion object {

        /**
         * Method returns boolean if <b>all</b> keywords (seperated by spaces) are present.<br>
         * <b>Example:</b> ["keyword1 keyword2", "billy bob joe"]
         *
         * @param inputString The string to find keywords in
         * @param phrases The array of phrases.
         *
         * @return If one of the provided sets contain all the keywords
         */
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

        /**
         * Method returns boolean if any of the provided keywords exist
         *
         * @param inputString The string to find keywords in
         * @param words The array of words to iterate through.
         *
         * @return If any of the words exist in the input string
         */
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

        /**
         * Method returns boolean if any of the provided keywords exist, <b>ONLY</b> if the input string is determined to be a question
         *
         * @param inputString The string to find keywords in
         * @param words The array of words to iterate through.
         *
         * @return If any of the words exist in the input string
         * @see MessageUtils.isQuestion
         */
        fun containsWordsIfQuestion(inputString: String, words: Array<String>): Boolean {
            if(isQuestion(removePunctuation(inputString))) {
                return containsWords(inputString, words)
            }
            return false
        }

        // Utils for da utils

        /**
         * Determines if an input string is a question, by checking for "question words" at the very start and end of a string
         *
         * @param inputString The string to find question words in
         *
         * @return If any the string is a question
         */
        private fun isQuestion(inputString: String): Boolean {
            val keywords = arrayOf("what", "how", "where", "wonder", "wondering")
            for(keyword in keywords) {
                val formattedInput = removePunctuation(inputString)
                if(formattedInput.lowercase().startsWith(keyword)) return true
                if(formattedInput.lowercase().endsWith(keyword)) return true
            }
            return false
        }

        /**
         * Determines if an input string contains keywords that would disqualify it from being a question
         *
         * @param inputString The string to find question words in
         *
         * @return If the input string contains words that invalidate itself as a question
         */
        private fun isDisqualifyingStarter(inputString: String): Boolean {
            val keywords = arrayOf("was", "were", "just", "you", "read")

            for(keyword: String in keywords) {
                for(word: String in removePunctuation(inputString).split(" ")) {
                    if(word.lowercase() == keyword) return true
                }
            }
            return false
        }

        /**
         * Strips the input string from any punctuation marks
         *
         * @param inputString The string to find question words in
         *
         * @return The filtered input string
         */
        private fun removePunctuation(inputString: String): String {
            var inputStringFinal = ""
            val punctuation: Array<String> = arrayOf(".", ",", "!", "?", ";", ":", "\"", "'", "`", "~", "^", "&", "*", "(", ")", "[", "]", "{", "}", "\\", "/", "|", "@", "#", "$", "%", "-")

            for(punctuationChar in punctuation) {
                inputStringFinal = inputString.replace(punctuationChar, "")
            }
            inputStringFinal = inputStringFinal.replace("I", "")
            inputStringFinal = inputStringFinal.replace("you", "")

            return inputStringFinal
        }
    }
}