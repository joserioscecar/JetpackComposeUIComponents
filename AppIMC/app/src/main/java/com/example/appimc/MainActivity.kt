package com.example.appimc

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appimc.ui.theme.AppIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppIMCTheme {
                CalculadoraIMC();
            }
        }
    }
}


@Composable
fun CalculadoraIMC() {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    var color by remember { mutableStateOf(Color.Blue) }


    // Lógica local segura de IMC
    fun calcularIMC() {


        color = Color.Red;

        // Validar si los campos están vacíos
        if (peso.isBlank() || altura.isBlank()) {
            resultado = "Por favor, ingresa el peso y la altura."

            return
        }

        // Intentar convertir a número
        val pesoDouble = peso.toDoubleOrNull()
        val alturaDouble = altura.toDoubleOrNull()

        // Validar conversión y que sean positivos
        if (pesoDouble == null || alturaDouble == null) {
            resultado = "Los valores deben ser numéricos."
            return
        }

        if (pesoDouble <= 0 || alturaDouble <= 0) {
            resultado = "Los valores deben ser mayores que cero."
            return
        }

        color = Color.Black;

        val imc = ImcCalculator.calcularIMC(pesoDouble,alturaDouble);
        val categoria = ImcCalculator.calcularCategoria(imc);

        resultado = "IMC: %.2f - $categoria".format(imc)

    }

    fun limpiarCampos() {
        peso = ""
        altura = ""
        resultado = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calculadora de IMC",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start // <-- texto alineado a la izquierda
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Peso
        Text(
            text = "Peso (kg)",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Altura
        Text(
            text = "Altura (m)",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (m)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Resultado
        if (resultado.isNotEmpty()) {
            Text(
                text = resultado,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = color
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón Calcular
        Button(
            onClick = { calcularIMC() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Limpiar
        Button(
            onClick = { limpiarCampos() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7),
                contentColor = Color.White
            )
        ) {
            Text(text = "Limpiar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraIMCPreview() {
    AppIMCTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CalculadoraIMC()
        }
    }
}
