package com.example.cultured.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_comment.presentation.CommentScreenRoot
import com.example.cultured.feature_comment.presentation.CommentWriteScreenRoot
import com.example.cultured.feature_event.presentation.list.EventListScreenRoot
import com.example.cultured.feature_login.presentation.login.LoginScreenRoot
import com.example.cultured.feature_map.presentation.map.MapScreenRoot
import com.google.android.gms.common.internal.service.Common.API

@Composable
fun Navigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.EventListScreen.route) {
            EventListScreenRoot(
                navController = navHostController
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreenRoot(
                navController = navHostController
            )
        }

        composable<EventUiModel> { backStackEntry ->
            val eventUiModel: EventUiModel = backStackEntry.toRoute()
            CommentScreenRoot(
                navController = navHostController,
                eventUiModel = eventUiModel,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(
            route = Screen.CommentWriteScreen.route,
        ) {
            CommentWriteScreenRoot(
                navController = navHostController,
                viewModel = if (navHostController.previousBackStackEntry != null) {
                    hiltViewModel(navHostController.previousBackStackEntry!!)
                }else {
                    hiltViewModel()
                }
            )
        }

        composable(
            route = "${Screen.MapScreen.route}/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") {
                    type = NavType.StringType
                },
                navArgument("longitude") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            MapScreenRoot(
                navController = navHostController,
                /**
                 * API give longitude value as latitude, vise versa
                 */
                latitude = entry.arguments?.getString("longitude")?.toDouble() ?: 0.0,
                longitude = entry.arguments?.getString("latitude")?.toDouble() ?: 0.0
            )
        }


    }
}