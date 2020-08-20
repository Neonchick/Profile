package ru.skillbranch.profile.utils

import java.util.*

object Utils
{
    fun parseFullName(fullName: String?): Pair<String?, String?>
    {
        val parts: List<String>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        firstName = if (firstName == "") null else firstName
        lastName = if (lastName == "") null else lastName

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String
    {
        val dictionary =
                mutableMapOf("а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d", "е" to "e",
                             "ё" to "e", "ж" to "zh", "з" to "z", "и" to "i", "й" to "i",
                             "к" to "k", "л" to "l", "м" to "m", "н" to "n", "о" to "o", "п" to "p",
                             "р" to "r", "с" to "s", "т" to "t", "у" to "u", "ф" to "f", "х" to "h",
                             "ц" to "c", "ч" to "ch", "ш" to "sh", "щ" to "sh'", "ъ" to "",
                             "ы" to "i", "ь" to "", "э" to "e", "ю" to "yu", "я" to "ya")
        val newDictionary = dictionary.toMap().toMutableMap()
        for ((k, v) in dictionary)
            newDictionary[k.toUpperCase(Locale.getDefault())] = if (v.isEmpty()) ""
            else v[0].toUpperCase() + v.substring(1)
        val splited = payload.split(" ")
        val ans: MutableList<String> = mutableListOf()
        for (word in splited)
        {
            var temp = ""
            for (i in word.indices)
                temp += if (word[i].toString() in newDictionary) newDictionary[word[i].toString()]
                else word[i]
            ans += temp
        }
        return ans.joinToString(separator = divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String?
    {
        var ans = ""

        if (!firstName?.trim().isNullOrEmpty())
            ans += firstName!![0].toUpperCase()

        if (!lastName?.trim().isNullOrEmpty())
            ans += lastName!![0].toUpperCase()

        return if (ans.isEmpty()) null else ans
    }
}