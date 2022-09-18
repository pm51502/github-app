package com.example.githubapp.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.formatTime(): String {
    if (this.isEmpty()) return ""
    val localDateTime = ZonedDateTime.parse(this).toLocalDateTime()
    return DateTimeFormatter.ofPattern("dd.MM.yyyy-hh:mm").format(localDateTime)
}
