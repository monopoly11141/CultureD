package com.example.cultured.feature_comment.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.cultured.feature_comment.presentation.model.CommentUiModel

class CommentUiModelProvider : PreviewParameterProvider<CommentUiModel> {
    override val values = sequenceOf(
        CommentUiModel(
            title = " 탄탄한 두께감과 부드러운 촉감이 장점인",
            author = "accumsan", 
            content = "테리 원단을 사용했기 때문에 포근하고 좋죠 ~ \uD83D\uDE18 이름에서 알 수 있듯이 넉넉한 오버핏으로 제작되어 누구나 편안하고 멋스럽게 스타일링 할 수 있답니다 !! 봄 , 가을에는 단품으로 가볍게 입기 좋고 , 겨울에는 레이어드로 차곡차곡 껴입기 좋으니 스타일과 취향에 맞게 오래 오래 즐겨주시길 바라요 ❤\uFE0F",
            timeStamp = "2024-12-13 06:25:00"
        ),
        CommentUiModel(
            title = "신축성 통기성 가벼움 모두 좋은 제품이에요. 색별로 구매했어요 적극추천합니다.",
            author = "ddfsdsa",
            content = "사용해서 우수한 통기성을 가졌고 두께감이 가벼워서 더운 여름 날씨에 시원하고 편안하게 착용이 가능해요 !! 린넨과 유사하지만 린넨보다 세탁 관리가 훨씬 편하다는 장점이 있답니다 ~ ✨ 잘 맞는 사이즈와 스타일의 제품으로 받아보셨으니 올 여름 데일리 팬츠로 아낌없이 즐겨주세요 !! \uD83E\uDE75",
            timeStamp = "2024-12-12 16:45:00"
        )
    )
}
