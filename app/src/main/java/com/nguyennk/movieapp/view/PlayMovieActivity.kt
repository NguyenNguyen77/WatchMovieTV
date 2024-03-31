package com.nguyennk.movieapp.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.tv.material3.MaterialTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.nguyennk.movieapp.repository.PhimLeRepository
import com.nguyennk.movieapp.ui.theme.MovieAppTheme
import com.nguyennk.movieapp.viewmodel.PhimLeViewModel
import com.nguyennk.movieapp.viewmodel.PhimLeViewModelFactory

class PlayMovieActivity : ComponentActivity() {

    private val repository = PhimLeRepository()
    private lateinit var phimLeViewModel: PhimLeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        phimLeViewModel = ViewModelProvider(this, PhimLeViewModelFactory(repository))[PhimLeViewModel::class.java]
        val selectedItem = intent.getStringExtra("selectedMovie")
        phimLeViewModel.playMovie(selectedItem.toString())

        phimLeViewModel.playMovie.observe(this, Observer {
                data ->
            Log.d("NguyenNK2", "data $data")
            setContent {
                MovieAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RectangleShape ,
                        color = Color.Black
                    ) {
                        GreetingPreview(data.server_data[0].link_m3u8)
                    }
                }
            }
        })

    }
}


@Composable
fun GreetingPreview(selectedItem: String?) {
    Log.d("NguyenNK2", "linkM3U8 $selectedItem")
    val context = LocalContext.current
//    val urlMovie = "https://s3.phim1280.tv/20240326/clEO3CMF/index.m3u8";
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(selectedItem))

    exoPlayer.setMediaItem(mediaItem)

    val playerView = StyledPlayerView(context)

    playerView.player = exoPlayer

    DisposableEffect(AndroidView(factory = {playerView},  modifier = Modifier.fillMaxSize())){

        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.release()
        }
    }
}