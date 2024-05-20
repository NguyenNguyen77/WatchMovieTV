@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.nguyennk.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.privacysandbox.tools.core.model.Types.int
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nguyennk.movieapp.model.ItemPhimLe
import com.nguyennk.movieapp.repository.PhimLeRepository
import com.nguyennk.movieapp.ui.theme.MovieAppTheme
import com.nguyennk.movieapp.viewmodel.PhimLeViewModel
import com.nguyennk.movieapp.viewmodel.PhimLeViewModelFactory

class MainActivity : ComponentActivity() {
    // Instantiate PhimLeRepository
    private val repository = PhimLeRepository()
    private lateinit var phimLeViewModel: PhimLeViewModel
    private var listPhimLe = ArrayList<ItemPhimLe>()
    private var listPhimBo = ArrayList<ItemPhimLe>()


    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        phimLeViewModel = ViewModelProvider(this, PhimLeViewModelFactory(repository))[PhimLeViewModel::class.java]
        phimLeViewModel.listPhimBo.observe(this, Observer {
                data ->
            listPhimBo = data.items as ArrayList<ItemPhimLe>
        })
        phimLeViewModel.listPhimLe.observe(this, Observer {
                data ->
            listPhimLe = data.items as ArrayList<ItemPhimLe>
            setContent {
                MovieAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RectangleShape
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                //.background(Color(0xFFE1E1E1), shape = RectangleShape)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Netflix(Fake)",
                                    color = Color.Red,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(), // Make the text span full width
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Start,
                                )

                                Text(
                                    text = "Phim lẻ",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(), // Make the text span full width
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start,
                                )

                                listPhimLe?.let { RecycleView(it) {
                                        selectedItem ->
                                    // Handle item click
                                    val intent = Intent(this@MainActivity, DetailInfoActivity::class.java)
                                    intent.putExtra("selectedMovie", selectedItem.slug)
                                    startActivity(intent)
                                } }

                                Text(
                                    text = "Phim bộ",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(), // Make the text span full width
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start,
                                )

                                listPhimBo?.let { RecycleView(it) {
                                        selectedItem ->
                                    // Handle item click
                                    val intent = Intent(this@MainActivity, DetailInfoActivity::class.java)
                                    intent.putExtra("selectedMovie", selectedItem.slug)
                                    startActivity(intent)
                                } }
                            }
                        }

                    }
                }
            }
        })


    }

    override fun onResume() {
        super.onResume()
    }
}

@ExperimentalTvMaterial3Api
@Composable
fun RecycleView(listPhimLe: List<ItemPhimLe>, onItemClick: (ItemPhimLe) -> Unit) {
    LazyRow(modifier = Modifier.padding(horizontal = 10.dp)){

        items(items = listPhimLe){
            item ->
            Column(
                modifier = Modifier
                    //.padding(4.dp)
                    .width(100.dp)
                    //.size(100.dp, 170.dp)
                    //.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .clickable { onItemClick(item) }
            ){
                //Log.d("NguyenNK2", "item.thumbURL: https://img.phimapi.com/${item.poster_url}")
                LoadImageFromUrlWithDefault("https://img.phimapi.com/"+item.poster_url, item.name)
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item)
                        }
                )
            }


        }
    }
}

@Composable
fun LoadImageFromUrlWithDefault(
    url: String,
    description: String
) {

    val matrix = ColorMatrix()
    matrix.setToSaturation(1F)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp, 120.dp),
        colorFilter = ColorFilter.colorMatrix(matrix)
    )
}