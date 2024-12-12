package com.example.cultured.feature_comment.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.component.EventImage
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.preview.EventUiModelProvider
import com.example.cultured.feature_comment.presentation.component.CommentItem
import com.example.cultured.feature_comment.presentation.model.CommentUiModel
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CommentScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel(),
    eventUiModel: EventUiModel
) {
    LaunchedEffect(true) {
        viewModel.onAction(CommentAction.InitEventUiModel(eventUiModel))
    }

    CommentScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: CommentState,
    onAction: (CommentAction) -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = state.eventUiModel.title,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            EventImage(
                modifier = modifier
                    .fillMaxHeight(0.3f),
                imageUrl = state.eventUiModel.imageUrl,
                contentDescription = "${state.eventUiModel.title} 이미지"
            )

            val uriHandler = LocalUriHandler.current

            Button(
                onClick = {
                    uriHandler.openUri(state.eventUiModel.eventUrl)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "웹사이트 가기"
                )
            }

            LazyColumn(
                modifier = modifier
                    .weight(1f)
            ) {
                items(state.commentList) { commentUiModel ->
                    CommentItem(
                        modifier = modifier
                            .padding(2.dp),
                        commentUiModel = commentUiModel,
                        isCommentByCurrentUser = commentUiModel.author == state.currentUser
                    )
                }
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun CommentScreenPreview(
    @PreviewParameter(EventUiModelProvider::class) eventUiModel: EventUiModel,
) {
    CultureDTheme {
        CommentScreen(
            navController = rememberNavController(),
            state = CommentState(
                eventUiModel = eventUiModel,
                commentList = commentUiModelList
            ),
            onAction = {}
        )
    }
}

private val commentUiModelList = listOf(
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
    ),
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
    ),
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
    ),
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