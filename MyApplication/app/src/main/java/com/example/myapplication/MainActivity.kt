package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(

                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Mi aplicación")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black,titleContentColor = Color.White)
                        )
                    }

                ) { innerPadding ->

                    Column(
                        modifier = Modifier.padding(innerPadding).padding(top = 16.dp).padding(horizontal = 16.dp),
                      //  horizontalAlignment = Alignment.CenterHorizontally,

                        ) {

                        Text("Campo uno")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "",
                            onValueChange = {},

                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text("Campo dos")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "",
                            onValueChange = {},

                        )


                        Spacer(modifier = Modifier.height(20.dp))

                        Button(modifier = Modifier.padding(innerPadding).align(Alignment.CenterHorizontally), onClick = { }) {

                            Text(text = "Hello World")
                        }

                    }


                }
            }
        }
    }
}

