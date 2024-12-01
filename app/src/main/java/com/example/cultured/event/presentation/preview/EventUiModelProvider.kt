package com.example.cultured.event.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.cultured.event.presentation.model.EventUiModel

class EventUiModelProvider : PreviewParameterProvider<EventUiModel> {
    override val values = sequenceOf(
        EventUiModel(
            typeList = listOf("교육", "체험"),
            district = "중랑구",
            title = "[중랑문화재단] 창의융합 유아동 예술프로그램 [말랑말랑, 중랑] 11월",
            startDate = "2024-11-05",
            endDate = "2024-11-30",
            location = "중랑아트센터 프로그램실",
            organization = "중랑문화재단",
            targetAudience = "5세(20년생)~10세(15년생)",
            feeInformation = "말랑 플레잉 : 월 30,000원 말랑 쿠킹 : 월 45,000원 말랑 클래식 : 월 30,000원",
            eventUrl = "https://www.jnfac.or.kr/art/edu/view/520",
            imageUrl = "https://culture.seoul.go.kr/cmmn/file/getImage.do?atchFileId=b14a6ab0cb9b4fda884ef1ce5b32816a&amp;thumb=Y",
            themeCode = "어린이/청소년 문화행사",
            longitude = 37.5983328190955,
            latitude = 127.091624142481,
            isFree = false
        )
    )
}