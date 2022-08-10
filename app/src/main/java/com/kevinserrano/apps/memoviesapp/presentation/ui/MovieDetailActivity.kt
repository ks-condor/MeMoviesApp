package com.kevinserrano.apps.memoviesapp.presentation.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.ads.AdSize
import com.kevinserrano.apps.memoviesapp.R
import com.kevinserrano.apps.memoviesapp.composables.GoogleBannerAdView
import com.kevinserrano.apps.memoviesapp.data.models.Movie
import com.kevinserrano.apps.memoviesapp.presentation.ui.theme.PelisVideosLiteTheme
import com.kevinserrano.apps.memoviesapp.utilities.get
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = get(Movie::class.java)
        setContent {
            PelisVideosLiteTheme {
                MovieDetail(movie = movie)
            }
        }
    }
}

@Composable
fun MovieDetail(movie: Movie) {
    val context = LocalContext.current
    Box() {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.backdropPath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = { (context as Activity).onBackPressed() }) {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp, top = 30.dp)
                    .size(30.dp),
                tint = Color.White,
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = stringResource(R.string.exit)
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(270.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(topStartPercent = 14, topEndPercent = 14)
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        ) {
            FloatingActionButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                backgroundColor = Color.White,
                onClick = { }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White,
                text = stringResource(id = R.string.watch_now)
            )
            Text(
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(top = 28.dp),
                color = Color.White,
                text = movie.title,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                fontSize = 15.sp,
                color = Color.White,
                text = movie.description.substring(
                    startIndex = 0,
                    endIndex = movie.description.length / 3
                ),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Light
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            GoogleBannerAdView(adSize = AdSize.LARGE_BANNER)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    PelisVideosLiteTheme {
        MovieDetail(
            movie = Movie(
                title = "Movie test",
                description = "Movie description test test test test test test",
                backdropPath = "https://storage.googleapis.com/afs-prod/media/e53811360eed4b8ba26b5f635d703a7c/3000.jpeg"
            )
        )
    }
}