package ru.gusev.usdcursapplication.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Record")
class CursRecordDto {

    @Attribute(name = "Date")
    var date: String? = null

    @Attribute(name = "Id")
    var id: String? = null

    @PropertyElement(name = "Nominal")
    var nominal: Int? = null

    @PropertyElement(name = "Value")
    var value: String? = null

}