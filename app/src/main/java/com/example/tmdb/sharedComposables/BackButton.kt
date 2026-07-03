package com.example.tmdb.sharedComposables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmdb.ui.theme.AppOnPrimaryColor
import com.example.tmdb.ui.theme.ButtonColor

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit){
    FloatingActionButton(
        modifier = Modifier.size(40.dp),
        containerColor = ButtonColor,
        contentColor = AppOnPrimaryColor,
        onClick = {onClick()}
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Back"
        )
    }
}