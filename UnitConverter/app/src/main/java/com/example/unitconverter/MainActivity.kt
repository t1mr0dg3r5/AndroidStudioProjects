package com.example.unitconverter

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.fillMaxSize().padding(innerPadding), color= MaterialTheme.colorScheme.background) {

                        UnitConverter(modifier = Modifier.fillMaxSize())

                    }
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableDoubleStateOf(1.00) }
    val oConversionFactor = remember { mutableDoubleStateOf(1.00) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.doubleValue * 100.0 / oConversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value= inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter Value")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        val iT = IconTemplate(painter = painterResource(id = R.drawable.outline_arrow_drop_down_24), contentDescription = "Arrow Down")
        
        Row {
            Box{
                // Input button
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)

                    Icon(
                        painter = iT.painter,
                        //tint = MaterialTheme.colorScheme.primary, // Applies dynamic theme color
                        contentDescription = iT.contentDescription, // Essential for accessibility
                    )
                }
               DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false } ) {
                   DropdownMenuItem(
                       text = { Text("Centimeters") },
                       onClick = {
                           inputUnit = "Centimeters"
                           iExpanded=false
                           conversionFactor.doubleValue = 0.01
                           convertUnits()
                       })
                   DropdownMenuItem(
                       text = { Text("Meters") },
                       onClick = {
                           inputUnit = "Meters"
                           iExpanded=false
                           conversionFactor.doubleValue = 1.0
                           convertUnits()
                       })
                   DropdownMenuItem(
                       text = { Text("Feet") },
                       onClick = {
                           inputUnit = "Feet"
                           iExpanded=false
                           conversionFactor.doubleValue = 0.3048
                           convertUnits()
                       })
                   DropdownMenuItem(
                       text = { Text("Millimeters") },
                       onClick = {
                           inputUnit = "Millimeters"
                           iExpanded=false
                           conversionFactor.doubleValue = 0.001
                           convertUnits()
                       })
               }
           }
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                // Output button
                Button(onClick = { oExpanded = true}) {
                    Text(text = outputUnit)
                    Icon(
                        painter = iT.painter,
                        contentDescription = iT.contentDescription, // Essential for accessibility
                    )
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false } ) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            outputUnit = "Centimeters"
                            oExpanded=false
                            oConversionFactor.doubleValue = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            outputUnit = "Meters"
                            oExpanded=false
                            oConversionFactor.doubleValue = 1.0
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            outputUnit = "Feet"
                            oExpanded=false
                            oConversionFactor.doubleValue = 0.3048
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            outputUnit = "Millimeters"
                            oExpanded=false
                            oConversionFactor.doubleValue = 0.001
                            convertUnits()
                        })
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
            )
    }

}


class IconTemplate(var painter: Painter, var contentDescription: String?)


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
        UnitConverter()
}
