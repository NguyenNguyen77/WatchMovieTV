package com.nguyennk.movieapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nguyennk.movieapp.model.CategoryMovie
import com.nguyennk.movieapp.model.Movie
import com.nguyennk.movieapp.repository.PhimLeRepository
import com.nguyennk.movieapp.ui.theme.MovieAppTheme
import com.nguyennk.movieapp.viewmodel.PhimLeViewModel
import com.nguyennk.movieapp.viewmodel.PhimLeViewModelFactory

class DetailInfoActivity : ComponentActivity() {
    private val repository = PhimLeRepository()
    private lateinit var phimLeViewModel: PhimLeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phimLeViewModel = ViewModelProvider(this, PhimLeViewModelFactory(repository))[PhimLeViewModel::class.java]
        val selectedItem = intent.getStringExtra("selectedMovie")
        phimLeViewModel.getInfoMovie(selectedItem.toString())

        phimLeViewModel.detailInfoMovie.observe(this, Observer {
                data ->
            Log.d("NguyenNK2", "data DetailInfoActivity $data")
            setContent {
                MovieAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RectangleShape ,
                        color = Color.Black
                    ) {
                        Row {
                            showBanner(url = data.poster_url, description = data.slug)
                            Column {
                                showInfoMovie(data)
                                showButtonWatchMovie(this@DetailInfoActivity, data.slug)
                            }

                        }

                    }
                }
            }
        })
    }




}

@Composable
private fun showInfoMovie(data: Movie?) {
    Text(
        text = data?.name ?: "",
        color = Color.Red,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
    )

    Text(
        text = ("Nội dung: " + data?.content )?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )
    var full = ""
    full = if(data?.episode_current=="Full" || data?.status=="completed" ){
        "Full"
    } else {
        data?.episode_current+"/" +data?.episode_total
    }
    Text(
        text = ("Số tập: $full") ?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )

    Text(
        text = ("Diễn viên: " + convertName(data?.actor)) ?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )

    Text(
        text = ("Đạo diễn: " + convertName(data?.director)) ?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )

    Text(
        text = ("Thể loại: " + convertNameCategory(data?.category)) ?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )

    Text(
        text = ("Quốc gia: " + convertNameCategory(data?.country)) ?: "",
        color = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Make the text span full width
        fontSize = 15.sp,
        textAlign = TextAlign.Start,
    )

}



@Composable
private fun showButtonWatchMovie(context: Context, slug: String) {
    Button(
        onClick = {
            // Handle button click
            val intent = Intent(context, PlayMovieActivity::class.java)
            intent.putExtra("selectedMovie", slug)
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, disabledContainerColor = Color.DarkGray),
        modifier = Modifier
            .padding(10.dp)
            //.padding(horizontal = 24.dp, vertical = 12.dp)
            .size(140.dp, 50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        // Button text
        Text(
            text = "Xem phim",
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

fun convertName(mList: List<String>?): String{
    val result = StringBuilder()
    if (mList != null) {
        for ((index, element) in mList.withIndex()) {
            result.append(element)
            if (index < mList.size - 1) {
                result.append(", ")
            }
        }
    }
    return result.toString()
}

fun convertNameCategory(category: List<CategoryMovie>?): String {
    val result = StringBuilder()
    if (category != null) {
        for ((index, element) in category.withIndex()) {
            result.append(element.name)
            if (index < category.size - 1) {
                result.append(", ")
            }
        }
    }
    return result.toString()
}

@Composable
fun showBanner(url: String, description: String) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp


    val matrix = ColorMatrix()
    matrix.setToSaturation(1F)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(screenWidth * 0.4f, screenHeight * 0.6f),
        colorFilter = ColorFilter.colorMatrix(matrix)
    )
}
