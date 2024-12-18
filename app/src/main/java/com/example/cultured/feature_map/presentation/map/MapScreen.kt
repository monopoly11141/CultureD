package com.example.cultured.feature_map.presentation.map

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.R
import com.example.cultured.feature_event.presentation.list.EventListAction
import com.example.cultured.feature_event.presentation.list.EventListEvent
import com.example.cultured.feature_event.presentation.list.EventListScreen
import com.example.cultured.feature_event.presentation.list.EventListState
import com.example.cultured.feature_event.presentation.list.EventListViewModel
import com.example.cultured.ui.theme.CultureDTheme
import com.example.cultured.util.KeyUtil
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun MapScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    MapScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: MapState,
    onAction: (MapAction) -> Unit
) {
    Scaffold() { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()


            KakaoMapSdk.init(context, KeyUtil.KAKAO_KEY)

            val mapView = remember { MapView(context) } // KakaoMapView를 기억하여 재사용할 수 있도록 설정

            AndroidView(
                modifier = modifier.height(200.dp), // AndroidView의 높이 임의 설정
                factory = { context ->
                    mapView.apply {
                        mapView.start(
                            object : MapLifeCycleCallback() {
                                // 지도 생명 주기 콜백: 지도가 파괴될 때 호출
                                override fun onMapDestroy() {
                                    Toast.makeText(context, "지도를 불러오는데 실패했습니다", Toast.LENGTH_SHORT).show()
                                }

                                // 지도 생명 주기 콜백: 지도 로딩 중 에러가 발생했을 때 호출
                                override fun onMapError(exception: Exception?) {
                                    Toast.makeText(context, "지도를 불러오는 중 알 수 없는 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            object : KakaoMapReadyCallback() {
                                // KakaoMap이 준비되었을 때 호출
                                override fun onMapReady(kakaoMap: KakaoMap) {
                                    // 카메라를 (locationY, locationX) 위치로 이동시키는 업데이트 생성
                                    val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(state.positionY, state.positionX))

                                    // 지도에 표시할 라벨의 스타일 설정
                                    val style = kakaoMap.labelManager?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.password_show)))

                                    // 라벨 옵션을 설정하고 위치와 스타일을 적용
                                    val options = LabelOptions.from(LatLng.from(state.positionY, state.positionX)).setStyles(style)

                                    // KakaoMap의 labelManager에서 레이어를 가져옴
                                    val layer = kakaoMap.labelManager?.layer

                                    // 카메라를 지정된 위치로 이동
                                    kakaoMap.moveCamera(cameraUpdate)

                                    // 지도에 라벨을 추가
                                    layer?.addLabel(options)
                                }

                                override fun getPosition(): LatLng {
                                    // 현재 위치를 반환
                                    return LatLng.from(state.positionY, state.positionX)
                                }
                            },
                        )
                    }
                },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MapScreenPreview() {
    CultureDTheme {
        MapScreen(
            navController = rememberNavController(),
            state = MapState(),
            onAction = {}
        )
    }
}