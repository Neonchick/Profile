package ru.skillbranch.profile.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String
{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date
{
    var time = this.time

    time += when (units)
    {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR   -> value * HOUR
        TimeUnits.DAY    -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits
{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.humanizeDiff(date: Date = Date()): String
{
    val abs = (this.time - date.time).absoluteValue
    val str: String = when
    {
        abs < 45 * SECOND          -> "несколько секунд"
        abs < 75 * SECOND          -> "минуту"
        abs < 45 * MINUTE && abs / MINUTE % 10 == 1L
        && abs / MINUTE / 10 != 1L -> "${abs / MINUTE} минуту"
        abs < 45 * MINUTE && abs / MINUTE % 10 < 5
        && abs / MINUTE / 10 != 1L -> "${abs / MINUTE} минуты"
        abs < 45 * MINUTE          -> "${abs / MINUTE} минут"
        abs < 75 * MINUTE          -> "час"
        abs / HOUR == 21L          -> "21 час"
        abs < 22 * HOUR && abs / HOUR % 10 < 5
        && abs / HOUR / 10 != 1L   -> "${abs / HOUR} часа"
        abs < 22 * HOUR            -> "${abs / HOUR} часов"
        abs < 26 * HOUR            -> "день"
        abs <= 360 * DAY && abs / DAY % 10 == 1L
        && abs / DAY / 10 != 1L    -> "${abs / DAY} день"
        abs <= 360 * DAY && abs / DAY % 10 < 5
        && abs / DAY / 10 != 1L    -> "${abs / DAY} дня"
        else                       -> "${abs / DAY} дней"
    }

    if (abs > 360 * DAY)
        return if (this.time < date.time) "более года назад"
        else "более чем через год"

    if (abs < SECOND) return "только что"

    return if (this.time < date.time) "$str назад" else "через $str"
}