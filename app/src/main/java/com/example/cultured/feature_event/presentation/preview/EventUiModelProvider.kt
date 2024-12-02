package com.example.cultured.feature_event.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.cultured.feature_event.presentation.model.EventUiModel

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
        ),
        EventUiModel(
            typeList = listOf("축제", "문화", "예술"),
            district = "종로구",
            title = "[서울생활문화센터 체부] 시민이 만드는 시민의 음악 축제, [2024 서울시민음악제] 개최!",
            startDate = "2024-11-03",
            endDate = "2024-11-30",
            location = "체부홀 등",
            organization = "서울생활문화센터-체부",
            targetAudience = "누구나",
            feeInformation = "",
            eventUrl = "http://www.ccasc.or.kr/bbs/notice_view.php?idx=381&amp;page=1",
            imageUrl = "https://culture.seoul.go.kr/cmmn/file/getImage.do?atchFileId=7c01d31eb1c345a998293fd647ae8fad&amp;thumb=Y",
            themeCode = "기타",
            longitude = 37.5767084987432,
            latitude = 126.97148941423,
            isFree = true
        )

    )
}