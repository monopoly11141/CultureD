package com.example.cultured.culture_event.presentation.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CultureEventListScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    CultureEventListScreen(
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun CultureEventListScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Text("Hello CultureEventListScreen")
}

@Preview
@Composable
private fun CultureEventListScreenPreview() {
    CultureDTheme {
        CultureEventListScreen(
            navController = rememberNavController()
        )
    }
}