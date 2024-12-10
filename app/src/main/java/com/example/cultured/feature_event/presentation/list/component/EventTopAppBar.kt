package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cultured.R
import com.example.cultured.feature_event.presentation.list.EventListAction
import com.example.cultured.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String,
    onLogoutButtonClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = titleText) },
        actions = {
            IconButton(onClick = {
                onLogoutButtonClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.signout),
                    contentDescription = "로그아웃",
                    modifier = Modifier
                        .scale(0.7f)
                )
            }
        }
    )

}