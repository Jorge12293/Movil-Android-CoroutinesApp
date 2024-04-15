package com.example.coroutinesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.coroutinesapp.ui.theme.CoroutinesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        val itemsViewModel: ItemsViewModel by viewModels()
        setContent {
            CoroutinesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Content(viewModel)
                    ItemsView(viewModel = itemsViewModel)
                }
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ButtonColor()
        if(viewModel.isLoading){
            CircularProgressIndicator()
        }else{
            Text(text = viewModel.resultState)
        }
        Button(onClick = { viewModel.fetchData() }) {
            Text(text = "Call Api")
        }
    }
}

@Composable
fun ButtonColor(){
    var color by remember { mutableStateOf(false) }

    Button(
        onClick = { color=!color },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(color) Color.Blue else Color.Red
        )) {
        Text(text = "Change Color")
    }
}

@Composable
fun ItemsView(viewModel:ItemsViewModel){
    val itemsList = viewModel.itemList

    // Flow
    val list by viewModel.list.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchData()
    }

    Column {
        if(viewModel.isLoading){
          CircularProgressIndicator()
        }else{
            LazyColumn{
//                items(itemsList){item->
//                    Text(text = item.name )
//                }
                items(list){item->
                    Text(text = item.name )
                }
            }
        }
    }
}




