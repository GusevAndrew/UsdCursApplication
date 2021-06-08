package ru.gusev.usdcursapplication.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Valute")
class ValuteDto {
    @Attribute(name = "ID")
    var id: String? = null

    @PropertyElement(name = "NumCode")
    var numCode: Int? = null

    @PropertyElement(name = "CharCode")
    var charCode: String? = null

    @PropertyElement(name = "Nominal")
    var nominal: Int? = null

    @PropertyElement(name = "Name")
    var name: String? = null

    @PropertyElement(name = "Value")
    var value: String? = null
}