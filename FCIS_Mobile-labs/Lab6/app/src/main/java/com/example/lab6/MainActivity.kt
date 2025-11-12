package com.example.lab6

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab6.ui.theme.Lab6Theme
import kotlin.collections.listOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6Theme {
                StudentApp()
            }
        }
    }
}
// three functions , TextEdit , button , List
@Preview(showBackground = true)
@Composable
fun StudentApp(){
    var random_names = listOf("Pierre","Karim","Ezzat")

    var name by remember { mutableStateOf("") }
    var names by remember { mutableStateOf(listOf<String>("")) }
    Column {
        Row {
            TextField(
                value = name,
                onValueChange = {name = it},
                label = {Text("Enter name")},
                placeholder = {Text("Name")},
                modifier = Modifier.padding(50.dp)
            )
            Button(onClick = { names += name }
            )

            {
                Text("Add")

            }
        }
        var context = LocalContext.current

        LazyColumn(Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)) {
            items(names){
                name->
                Text(text=name, fontSize = 50.sp,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            Toast.makeText(context, "Student name : $name", Toast.LENGTH_SHORT)
                                .show()
                        }

                )

            }
        }

    }
}