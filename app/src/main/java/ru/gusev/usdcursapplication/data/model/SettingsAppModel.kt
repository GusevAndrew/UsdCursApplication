package ru.gusev.usdcursapplication.data.model

import java.util.*

data class SettingsAppModel(
    var timeNotification: Calendar?,
    var cursValue: Float?,
    var isNotificationEnabled: Boolean
)