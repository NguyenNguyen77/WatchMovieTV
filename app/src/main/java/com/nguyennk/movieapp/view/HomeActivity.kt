@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.nguyennk.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
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


    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel
        phimLeViewModel = ViewModelProvider(this, PhimLeViewModelFactory(repository))[PhimLeViewModel::class.java]

        phimLeViewModel.listPhimLe.observe(this, Observer {
                data ->
            Log.d("NguyenNK2","data.titlePage "+ data.items.get(0).slug)
            listPhimLe = data.items as ArrayList<ItemPhimLe>
            setContent {
                MovieAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RectangleShape
                    ) {
                        listPhimLe?.let { RecycleView(it) {
                                selectedItem ->
                            // Handle item click
                            val intent = Intent(this@MainActivity, PlayMovieActivity::class.java)
                            intent.putExtra("selectedMovie", selectedItem.slug)
                            startActivity(intent)
                        } }
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
    LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)){

        items(items = listPhimLe){
            item ->
            Surface(modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)) {
                Text(text = item.name, modifier = Modifier
                    .clickable {
                        onItemClick(item)
                    })
            }

        }
    }
}