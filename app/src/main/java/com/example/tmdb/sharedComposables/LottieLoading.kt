package com.example.tmdb.sharedComposables

import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.model.content.MergePaths

@Composable
fun LoopLottieLoading(
    modifier: Modifier = Modifier,
    @RawRes animation: Int,
    alignment: Alignment = Alignment.Center,
    enableMergePaths: Boolean = true
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))
    val reverse = remember { mutableStateOf(false) }
    val anim = rememberLottieAnimatable()
    if (reverse.value.not()) {
        LaunchedEffect(composition) {
            anim.animate(composition = composition, speed = 1F)
            reverse.value = true
        }
    }
    if (reverse.value) {
        LaunchedEffect(composition) {
            anim.animate(composition = composition, speed = -1F)
            reverse.value = false
        }
    }
    LottieAnimation(
        composition,
        anim.value,
        modifier = modifier,
        alignment = alignment,
        enableMergePaths = enableMergePaths
    )
}
@Composable
fun LottieLoading(
    modifier: Modifier,
    @RawRes animation: Int
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation),
        onRetry ={ failCount, exception ->
            Log.d("TAG", "FailedLoading: $failCount ${exception.localizedMessage}")
            false
        } )

    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(composition,progress,modifier)
}