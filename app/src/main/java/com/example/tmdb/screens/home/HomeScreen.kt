package com.example.tmdb.screens.home

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmdb.R
import com.example.tmdb.model.Movie
import com.example.tmdb.screens.watchList.WatchListViewModel
import com.example.tmdb.sharedComposables.LoopLottieLoading
import com.example.tmdb.ui.theme.AppOnPrimaryColor
import com.example.tmdb.ui.theme.AppPrimaryColor
import com.example.tmdb.ui.theme.ButtonColor
import com.example.tmdb.ui.theme.Selected
import com.example.tmdb.util.Constants.BASE_BACKDROP_IMAGE_URL
import com.example.tmdb.util.Constants.BASE_POSTER_IMAGE_URL
import com.example.tmdb.util.MediaType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.MovieDetailsDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileDestination
import com.ramcosta.composedestinations.generated.destinations.SearchDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import retrofit2.HttpException
import java.io.IOException

@Destination<RootGraph>(start = true)
@Composable
fun Home(
  navigator: DestinationsNavigator?,
  homeViewModel: HomeViewModel = hiltViewModel(),
  watchListViewModel: WatchListViewModel = hiltViewModel()
){
  Column(modifier = Modifier.fillMaxSize().background(AppPrimaryColor)) {
    SearchBar(navigator!!,homeViewModel)
    Scroll(navigator,homeViewModel,watchListViewModel)
  }
}

@Composable
fun SearchBar(navigator: DestinationsNavigator,homeViewModel: HomeViewModel){
  Row(modifier = Modifier
    .padding(top = 30.dp, bottom = 10.dp, start = 10.dp, end = 4.dp)
    .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
    ) {
    Box(contentAlignment = Alignment.Center) {

      IconButton(onClick = { navigator.navigate(direction = ProfileDestination()){launchSingleTop = true} }) {
        Icon(painter = painterResource(R.drawable.profile),
          tint = AppOnPrimaryColor,
          contentDescription = "Profile"
        )
      }
    }
    val mediaTypes = listOf(MediaType.MOVIE, MediaType.TVSHOW)
    val selectedMediaType =homeViewModel.selectedMediaType.value
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        mediaTypes.forEachIndexed { index, type ->
          Text(
            text = if (type == MediaType.MOVIE) "Movies" else "TVShows",
            fontWeight = if (selectedMediaType == mediaTypes[index]) FontWeight.Bold else FontWeight.Light,
            fontSize = if (selectedMediaType == mediaTypes[index]) 30.sp else 25.sp,
            color = if (selectedMediaType == mediaTypes[index]) AppOnPrimaryColor else Color.LightGray.copy(alpha = 0.75F),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null){
              if (homeViewModel.selectedMediaType.value != mediaTypes[index]){
                homeViewModel.selectedMediaType.value = mediaTypes[index]
                homeViewModel.getGenres()
                homeViewModel.refresh(null)
              }
            }
          )
        }
      }
      val animOffest = animateDpAsState(
        targetValue = when(mediaTypes.indexOf(selectedMediaType)){
          0 -> (-50).dp
          else -> 50.dp
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy)
      )
      Box(
        modifier = Modifier
          .width(70.dp)
          .height(2.dp)
          .offset(animOffest.value)
          .clip(RoundedCornerShape(4.dp))
          .background(AppOnPrimaryColor)

      )
    }

    IconButton(onClick ={
      navigator.navigate(direction = SearchDestination()){
        launchSingleTop = true
      }

    } ) {
      Icon(
        painter = painterResource(R.drawable.search),
        tint = AppOnPrimaryColor,
        contentDescription = "Search"
      )
    }
  }
}
@Composable
fun Movie(image: String,title: String,modifier: Modifier,landscape: Boolean,onclick: () -> Unit){
  Column(
    modifier = Modifier.padding(all = 4.dp).wrapContentHeight()
      .clickable(interactionSource = remember {  MutableInteractionSource() },indication = null){onclick()},
    horizontalAlignment = Alignment.Start
  ) {
    CoilImage(
      imageModel = {image}, component = rememberImageComponent { +ShimmerPlugin(Shimmer.Flash(
        baseColor = AppOnPrimaryColor,
        highlightColor = ButtonColor,
        dropOff = 0.65F,
        tilt = 20F,
        duration = 500
      )) },
      failure = {
        Box(contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))) {
          Image(painter = painterResource(R.drawable.image_not_available),contentDescription = "image not available")
        }
      }

    )
    AnimatedVisibility(visible = landscape) {
      Text(
        text = trimText(title),
        modifier = Modifier.padding(start = 4.dp, top = 4.dp).fillMaxWidth(),
        maxLines = 1,
        color = AppOnPrimaryColor,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
      )
    }
  }
}
private fun trimText(text: String)= if (text.length <= 26) text else{
  val trimTitle = text.removeRange(startIndex = 26, endIndex = text.length)
  "$trimTitle..."
}
@Composable
fun SelectedGenre(
  genre: String,
  selected: Boolean,
  onclick: () -> Unit
){
  val animateChip by animateColorAsState(
    targetValue = if (selected) Selected else ButtonColor,
    animationSpec = tween(
      durationMillis = if (selected) 100 else 50,
      delayMillis = 0,
      easing = LinearOutSlowInEasing
    )
  )
  Box(
    modifier = Modifier.padding(end = 4.dp)
      .clip(CircleShape)
      .background(color = animateChip)
      .height(32.dp)
      .widthIn(80.dp)
      .padding(horizontal = 8.dp)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
      ){onclick()}
  ) {
    Text(
      text = genre,
      color = if (selected) AppPrimaryColor else Color.White.copy(0.80F),
      fontWeight = if (selected) FontWeight.Bold else FontWeight.Light,
      textAlign = TextAlign.Center,
      modifier = Modifier.align(Alignment.Center)
    )
  }
}
@Composable
private fun ScrollMovies(
  landscape: Boolean= false,
  selectedMediaType: MediaType,
  navigator: DestinationsNavigator,
  pagingItems: LazyPagingItems<Movie>,
  onErrorClick: () -> Unit
){
  Box(modifier = Modifier.fillMaxWidth()
    .height(if (!landscape)220.dp else 200.dp),
    contentAlignment = Alignment.Center) {
    when(pagingItems.loadState.refresh){
      is LoadState.Loading ->{
        LoopLottieLoading(animation = R.raw.loader)
      }
      is LoadState.NotLoading -> {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
          items(pagingItems.itemSnapshotList) {movie ->
            val imagePath = if (landscape) "$BASE_BACKDROP_IMAGE_URL${movie?.backdropPath}"
            else "$BASE_POSTER_IMAGE_URL${movie?.posterPath}"
            movie?.let {
              Movie(
                image = imagePath,
                title = it.title,
                modifier = Modifier.width(if (landscape) 220.dp else 130.dp)
                  .height(if (landscape) 160.dp else 200.dp),
                landscape = landscape
              ) {
                navigator.navigate(MovieDetailsDestination()){
                  launchSingleTop = true
                }
              }
            }
          }
        }
      }
      is LoadState.Error ->{
        val error = pagingItems.loadState.refresh as LoadState.Error
        val errorMessage = when(error.error){
          is IOException -> "Please check your internet connection"
          is HttpException -> "Something went wrong!\nTap to retry"
          else -> "Something went wrong"
        }
        Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessage,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black,
            textAlign = TextAlign.Center)
        }
      }

      else ->{}
    }
  }
}
@Composable
fun Scroll(navigator: DestinationsNavigator,homeViewModel: HomeViewModel,watchListViewModel: WatchListViewModel){
  val trendingMovies = homeViewModel.trendingMovies.value.collectAsLazyPagingItems()
  val topRatedMovies = homeViewModel.topRatedMovies.value.collectAsLazyPagingItems()
  val upcomingMovies = homeViewModel.upComingMovies.value.collectAsLazyPagingItems()
  val popularMovies = homeViewModel.popularMovies.value.collectAsLazyPagingItems()
  val nowPlayingMovies = homeViewModel.nowPlayingMovies.value.collectAsLazyPagingItems()
  val recommendedMovies = homeViewModel.recommendedMovies.value.collectAsLazyPagingItems()
  val myWatchList = watchListViewModel.watchList.value.collectAsState(emptyList())
  val selectedMediaType = homeViewModel.selectedMediaType.value
  LaunchedEffect(myWatchList.value.size) {
    if (myWatchList.value.isNotEmpty()){
      homeViewModel.randomMovie = myWatchList.value[(0..myWatchList.value.lastIndex).random()].mediaId
      if (recommendedMovies.itemCount == 0){
        homeViewModel.getRecommendedMovies(homeViewModel.randomMovie!!)
      }
    }
  }
  val listState : LazyListState = rememberLazyListState()
  LazyColumn(modifier = Modifier.padding(horizontal = 2.dp).fillMaxSize(),state = listState) {
    item {
      Text(
        text = "Genres",
        modifier = Modifier.padding(4.dp),
        color = AppOnPrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
    }
    item {
      val genres = homeViewModel.movieGenres
      LazyRow(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
        items(genres.size){
          SelectedGenre(genre = genres[it].name!!,
            selected = genres[it].name == homeViewModel.selectedGenre.value.name,
            onclick = {
              if (homeViewModel.selectedGenre.value.name != genres[it].name){
                homeViewModel.selectedGenre.value = genres[it]
                homeViewModel.filterByGenre(genres[it])
              }
            }
          )
        }
      }
    }
    item {
      Text(
        text = "Trending",
        modifier = Modifier.padding(start = 4.dp, top = 8.dp),
        color = AppOnPrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
    }
    item {
      ScrollMovies(
        landscape = true,
        selectedMediaType = selectedMediaType,
        navigator = navigator,
        pagingItems = trendingMovies,
        onErrorClick = {
          homeViewModel.refresh()
        }
      )
    }
    item {
      Text(
        text = "Popular",
        modifier = Modifier.padding(start = 4.dp, top = 8.dp),
        color = AppOnPrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
    }
    item {
      ScrollMovies(
        selectedMediaType = selectedMediaType,
        navigator = navigator,
        pagingItems = popularMovies,
        onErrorClick = {
          homeViewModel.refresh()
        }
      )
    }
    item {
      Text(
        text = "Top Rated",
        modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 8.dp),
        color = AppOnPrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
    }
    item {
      ScrollMovies(
        selectedMediaType = selectedMediaType,
        navigator = navigator,
        pagingItems = topRatedMovies,
        onErrorClick = {
          homeViewModel.refresh()
        }
      )
    }
    if (homeViewModel.selectedMediaType.value == MediaType.MOVIE) {
      item {
        Text(
          text = "Upcoming",
          modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp),
          color = AppOnPrimaryColor,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold
        )
      }
      item {
        ScrollMovies(
          selectedMediaType = selectedMediaType,
          navigator = navigator,
          pagingItems = upcomingMovies,
          onErrorClick = {
            homeViewModel.refresh()
          }
        )
      }
    }
    item {
      Text(
        text = "Now Playing",
        modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp),
        color = AppOnPrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
    }
    item {
      ScrollMovies(
        selectedMediaType = selectedMediaType,
        navigator = navigator,
        pagingItems = nowPlayingMovies,
        onErrorClick = {
          homeViewModel.refresh()
        }
      )
    }
  }
}