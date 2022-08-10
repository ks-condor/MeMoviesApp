package com.kevinserrano.apps.memoviesapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.kevinserrano.apps.memoviesapp.R
import com.kevinserrano.apps.memoviesapp.data.models.Movie
import com.kevinserrano.apps.memoviesapp.presentation.ui.theme.PelisVideosLiteTheme
import com.kevinserrano.apps.memoviesapp.presentation.ui.theme.WhiteEEE7E4
import com.kevinserrano.apps.memoviesapp.presentation.viewmodels.HomeViewModel
import com.kevinserrano.apps.memoviesapp.utilities.getYearFromFormat
import com.kevinserrano.apps.memoviesapp.utilities.put
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PelisVideosLiteTheme {
                CollapsingEffectScreen()
            }
        }
    }
}


@Composable
private fun LoadingList(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        contentPadding = PaddingValues(6.dp),
        state = rememberLazyListState()
    ) {
        content()
    }
}

@Composable
private fun Header(lazyListState: LazyListState) {
    var scrolledY = 0f
    var previousOffset = 0
    Box(
        modifier = Modifier
            .graphicsLayer {
                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.5f
                previousOffset = lazyListState.firstVisibleItemScrollOffset
            }
            .fillMaxWidth()
            .height(340.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.spiral_from_the_book_of_saw),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.app_name)
        )
        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = { }) {
            Icon(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(30.dp),
                tint = Color.White,
                imageVector = Icons.Filled.More,
                contentDescription = stringResource(R.string.exit)
            )
        }
        Text(
            text = stringResource(R.string.wellcome),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp),
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color.White
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(color = Color.White)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CollapsingEffectScreen() {
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeViewModel>()
    val state: HomeViewModel.State by viewModel.state.collectAsState(HomeViewModel.State())
    val events: HomeViewModel.Event? by viewModel.events.collectAsState(initial = null)
    val lazyListState = rememberLazyListState()
    val showLoading = state.loading && state.movies == null
    val showMoviesList = state.movies != null
    val showError = !state.error.isNullOrEmpty()
    if (events is HomeViewModel.Event.NavigateToDetail) {
        val movie = (events as HomeViewModel.Event.NavigateToDetail).movie
        context.startActivity(Intent(context, MovieDetailActivity::class.java).apply {
            put(movie)
        })
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        /*verticalArrangement = Arrangement.spacedBy(4.dp),*/
        state = lazyListState,
    ) {
        item {
            Header(lazyListState)
        }
        when {
            showLoading -> {
                items((0..3).toList()) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = Color.Gray,
                        contentColor = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(100.dp)
                            .placeholder(
                                visible = true,
                                shape = RoundedCornerShape(6.dp),
                                color = WhiteEEE7E4,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(
                                        0xFF9C9999
                                    )
                                )
                            )
                            .wrapContentHeight()
                    ) { }
                }
            }
            showMoviesList -> {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = stringResource(id = R.string.popular_movies),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF132743), style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                items(state.movies!!) { movie ->
                    MovieCard(movie = movie) {
                        viewModel.onClickMovie(movie = movie)
                    }
                }
            }
            showError -> {
                item {
                    LottieAnimationError() {
                        viewModel.tryAgain()
                    }
                }
            }
        }
    }
}

@Composable
private fun LottieAnimationError(onButtonClicked: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    Column(modifier = Modifier.fillMaxWidth()) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(280.dp)
        )
        Text(
            text = stringResource(id = R.string.error_loading_movies),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(colors = ButtonDefaults.buttonColors(
            backgroundColor = WhiteEEE7E4
        ),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onButtonClicked() }) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Composable
private fun MovieCard(movie: Movie, onItemClicked: () -> Unit) {
    Card(
        backgroundColor = WhiteEEE7E4
        /*.copy(alpha = 0.9f).compositeOver(Color.White)*/,
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        CardContent(
            onItemClicked = onItemClicked,
            title = movie.title, description = movie.description, year =
            movie.releaseDate.getYearFromFormat().toString(), poster = movie.backdropPath
        )
    }
}

@Composable
private fun CardContent(
    onItemClicked: () -> Unit,
    title: String,
    description: String,
    year: String,
    poster: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { onItemClicked() }
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(poster)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color(0xFF132743)
                )
                Text(fontSize = 13.sp, text = "Año de estreno: $year", color = Color(0xFF132743))
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
        }
        if (expanded) {
            Text(
                modifier = Modifier.padding(12.dp),
                fontSize = 11.sp,
                textAlign = TextAlign.Justify,
                text = description,
            )
        }
    }
}
