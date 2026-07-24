package com.example.navigartionsample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondScreen(name: String, age: String, navigateToFirstScreen: (String) -> Unit, navigateToThirdScreen: () -> Unit ) {

    val name = remember { mutableStateOf(name) }
    val age = remember { mutableStateOf(age) }

    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Text(text="This is the Second Screen", fontSize = 24.sp)
        Text(text="Welcome ${name.value}. You are ${age.value} years old.", fontSize = 24.sp)

        Button(onClick={navigateToFirstScreen(name.value)}) {
            Text(text="Go To First Screen")
        }
        Button(onClick={navigateToThirdScreen()}) {
            Text(text="Go To Third Screen")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    SecondScreen("Test","0",{}, {})
}
