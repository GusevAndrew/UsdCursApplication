package ru.gusev.usdcursapplication.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ValCurs")
class ValCursDto {
    @Attribute(name = "Date")
    var date: String? = null

    @Attribute(name = "name")
    var name: String? = null

    @Element(name = "Valute")
    var valutesDto: List<ValuteDto>? = null
}