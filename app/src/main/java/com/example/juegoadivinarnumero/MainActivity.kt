package com.example.juegoadivinarnumero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.juegoadivinarnumero.ui.theme.JuegoAdivinarNumeroTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuegoAdivinarNumeroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JuegoAdivinarNumero(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//@Preview(showBackground = true)

@Composable
fun JuegoAdivinarNumero(modifier: Modifier = Modifier) {
    var numeroSecreto by remember { mutableStateOf((0..100).random()) }
    var intentos by remember { mutableStateOf(3) }
    var entradaUsuario by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Adivina el número entre 0 y 100") }
    var juegoTerminado by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Image(
            painter = painterResource(id = R.drawable.android_logo), // Use your drawable resource
            contentDescription = "Android Logo",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(50.dp)
        )


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Juego de adivinar el número", fontSize = 30.sp)

            Spacer(Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.user_logo), // Use your drawable resource
                contentDescription = "User Logo",
                modifier = Modifier
                    .padding(16.dp)
                    .size(250.dp)
            )

            Text(mensaje)

            Spacer(Modifier.height(10.dp))
            TextField(
                placeholder = { Text("Número a adivinar") },
                value = entradaUsuario,
                onValueChange = { entradaUsuario = it },
                enabled = !juegoTerminado
            )
            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                val numeroUsuario = entradaUsuario.toIntOrNull()

                if (numeroUsuario == null || numeroUsuario !in 0..100) {
                    mensaje = "Ingresa un número válido entre 0 y 100"
                    return@Button
                }

                if (numeroUsuario == numeroSecreto) {
                    mensaje = "¡Felicidades! Adivinaste el número."
                    juegoTerminado = true
                } else {
                    intentos--
                    mensaje = if (numeroUsuario < numeroSecreto) "El número es mayor" else "El número es menor"

                    if (intentos <= 0) {
                        mensaje = "¡Se acabaron los intentos! El número secreto era $numeroSecreto."
                        juegoTerminado = true
                    }
                }
            }, enabled = !juegoTerminado) {
                Text("Adivinar")
            }
            Spacer(Modifier.height(10.dp))
            Text("Intentos restantes: $intentos")
        }
    }
}