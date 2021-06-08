package ru.gusev.usdcursapplication.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ValCurs")
class DynamicCursDto {

    @Attribute(name = "ID")
    var id: String? = null

    @Attribute(name = "DateRange1")
    var dateFrom: String? = null

    @Attribute(name = "DateRange2")
    var dateTo: String? = null

    @Attribute(name = "name")
    var name: String? = null

    @Element(name = "Record")
    var records: List<CursRecordDto>? = null
}