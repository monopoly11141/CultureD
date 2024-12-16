package com.example.cultured.core.presentation.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EventUiModel(
    /**
     *  CODENAME
     *  ex) \[뮤지컬, 오페라]
     */
    val typeList: List<String> = emptyList(),
    /**
     *  GUNAME
     *  ex) 송파구
     */
    val district: String = "",
    /**
     *  TITLE
     *  ex) \[강북문화재단] 강북문화대학 페스티 \[꿈빛]
     */
    val title: String = "",
    /**
     *  STRTDATE
     *  ex) 2024-11-29 00:00:00.0
     */
    val startDate: String = "",
    /**
     *  END_DATE
     *  ex) 2024-11-30 00:00:00.0
     */
    val endDate: String = "",
    /**
     *  PLACE
     *  ex) 서초문화예술회관 아트홀
     */
    val location: String = "",
    /**
     *  ORG_NAME
     *  ex) 은평구청
     */
    val organization: String = "",
    /**
     * USE_TRGT
     * ex) 3세 이상 관람 가능(2021년생 이후 출생자)
     */
    val targetAudience: String = "",
    /**
     * USE_FEE
     * ex) S석 55,000원｜R석(발코니석) 66,000원｜VIP석 77,000원
     */
    val feeInformation: String = "",
    /**
     * ORG_LINK
     * ex) https://craftmuseum.seoul.go.kr/progrm/view/305
     */
    val eventUrl: String = "",
    /**
     * MAIN_IMG
     * ex) https://culture.seoul.go.kr/cmmn/file/getImage.do?atchFileId=fe9914492d784f43a33d1721ac1d790e&amp;thumb=Y
     */
    val imageUrl: String = "",
    /**
     * THEMECODE
     * ex) 어린이/청소년 문화행사, 기타
     */
    val themeCode: String = "",
    /**
     * LOT
     * ex) 37.5983328190955
     */
    val longitude: Double = 0.0,
    /**
     *  LAT
     *  ex) 127.091624142481
     */
    val latitude: Double = 0.0,
    /**
     * IS_FREE
     * ex) 유료, 무료
     */
    @get:PropertyName("isFree")
    val isFree: Boolean = true,

    @get:Exclude
    val isFavorite: Boolean = false
)

fun EventUiModel.isHappeningAt(dateString: String): Boolean {
    val startLocalDate = LocalDate.parse(this.startDate)
    val endLocalDate = LocalDate.parse(this.endDate)

    val dateStringLocalDate = LocalDate.parse(dateString)

    return dateStringLocalDate in startLocalDate..endLocalDate
}

fun EventUiModel.isStartedAt(dateString: String): Boolean {
    val startLocalDate = LocalDate.parse(this.startDate)
    val dateStringLocalDate = LocalDate.parse(dateString)

    return dateStringLocalDate == startLocalDate
}

fun EventUiModel.toDocumentId(): String {
    return (this.title + this.startDate + this.endDate).replace("/", "-")
}

fun EventUiModel.changeFavoriteStatus() : EventUiModel {
    return this.copy(
        isFavorite = !this.isFavorite
    )
}