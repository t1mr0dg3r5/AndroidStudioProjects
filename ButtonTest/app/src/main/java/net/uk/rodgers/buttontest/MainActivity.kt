package net.uk.rodgers.buttontest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.uk.rodgers.buttontest.ui.theme.ButtonTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ButtonTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(Modifier.padding(innerPadding)) {
                        ButtonTest()
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonTest(modifier: Modifier = Modifier ) {
/*
    Column(modifier = Modifier.fillMaxSize()) {
        Box {
            Button(onClick = { wasClicked() }) {
                Text(text = "Hello")
            }
        }
    }

*/

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Row {
            Box{
                Button(onClick = { wasClicked() }) {
                    Text(text = "Select")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            //Box{
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Select")
                }
            /*
                DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ } ) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = { /* Handle Item 1 click */ })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = { /* Handle Item 1 click */ })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = { /* Handle Item 1 click */ })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = { /* Handle Item 1 click */ })
                }

             */
            //}

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result")

    }

}

fun wasClicked(){
    println("Button was clicked")
}
