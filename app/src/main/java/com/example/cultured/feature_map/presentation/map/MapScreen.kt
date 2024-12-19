package com.example.cultured.feature_map.presentation.map

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.R
import com.example.cultured.ui.theme.CultureDTheme
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles


@Composable
fun MapScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel(),
    latitude: Double = 0.0,
    longitude: Double = 0.0
) {

    MapScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        },
        latitude = latitude,
        longitude = longitude
    )
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: MapState,
    onAction: (MapAction) -> Unit,
    latitude: Double,
    longitude: Double
) {
    Log.d("MapScreen", "latitude :${latitude}, longitude: $longitude")
    Scaffold() { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val context = LocalContext.current

            val mapView = remember { MapView(context) }

            AndroidView(
                modifier = modifier
                    .fillMaxSize(),
                factory = { _ ->
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

                                override fun onMapReady(kakaoMap: KakaoMap) {
                                    val cameraUpdate =
                                        CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude))
                                    val style = kakaoMap.labelManager?.addLabelStyles(
                                        LabelStyles.from(
                                            LabelStyle.from(
                                                R.drawable.location
                                            )
                                        )
                                    )
                                    val options = LabelOptions.from(LatLng.from(latitude, longitude)).setStyles(style)

                                    val layer = kakaoMap.labelManager?.layer
                                    kakaoMap.moveCamera(cameraUpdate)
                                    layer?.addLabel(options)
                                }

                                override fun getPosition(): LatLng {
                                    return LatLng.from(latitude, longitude)
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
            onAction = {},
            latitude = 0.0,
            longitude = 0.0
        )
    }
}