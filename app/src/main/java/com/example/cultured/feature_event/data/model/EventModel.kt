package com.example.cultured.feature_event.data.model

import com.example.cultured.core.presentation.model.EventUiModel
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "culturalEventInfo")
data class EventModel(
    @PropertyElement(name = "list_total_count")
    val totalCount: Long = 0,
    @Element(name = "row")
    val eventList: List<Event> = emptyList()
)

@Xml(name = "row")
data class Event(
    @PropertyElement(name = "CODENAME")
    val codeName: String = "",
    @PropertyElement(name = "GUNAME")
    val guName: String = "",
    @PropertyElement(name = "TITLE")
    val title: String = "",
    @PropertyElement(name = "DATE")
    val date: String = "",
    @PropertyElement(name = "PLACE")
    val place: String = "",
    @PropertyElement(name = "ORG_NAME")
    val orgName: String = "",
    @PropertyElement(name = "USE_TRGT")
    val useTarget: String = "",
    @PropertyElement(name = "USE_FEE")
    val useFee: String = "",
    @PropertyElement(name = "ORG_LINK")
    val organizationLink: String = "",
    @PropertyElement(name = "MAIN_IMG")
    val mainImage: String = "",
    @PropertyElement(name = "STRTDATE")
    val startDate: String = "",
    @PropertyElement(name = "END_DATE")
    val endDate: String = "",
    @PropertyElement(name = "THEMECODE")
    val themeCode: String = "",
    @PropertyElement(name = "LOT")
    val longitude: String = "",
    @PropertyElement(name = "LAT")
    val latitude: String = "",
    @PropertyElement(name = "IS_FREE")
    val free: String = ""
)

fun Event.toEventUiModel(): EventUiModel {
    return EventUiModel(
        typeList = this.codeName.split("-", "/"),
        district = this.guName,
        title = this.title,
        startDate = this.startDate.split(" ").first(),
        endDate = this.endDate.split(" ").first(),
        location = this.place,
        organization = this.orgName,
        targetAudience = this.useTarget,
        feeInformation = this.useFee,
        eventUrl = this.organizationLink,
        imageUrl = this.mainImage,
        themeCode = this.themeCode,
        longitude = this.longitude.toDouble(),
        latitude = this.latitude.toDouble(),
        isFree = this.free == "무료"

    )
}