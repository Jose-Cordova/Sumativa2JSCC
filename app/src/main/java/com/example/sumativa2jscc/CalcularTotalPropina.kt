package com.example.sumativa2jscc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sumativa2jscc.cards.CardTotalPropina

@Composable
fun CalcularTotalConPropina(modifier: Modifier){
    //Variables de estado
    var nombrePlatillo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precioUnitario by remember { mutableStateOf("") }
    var porcentajePropina by remember { mutableStateOf("") }
    var totalPagar by remember { mutableStateOf(0.0) }
    var propina by remember { mutableStateOf(0.0) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //Llamamos a la funcion para pistar la card de total y propina
            CardTotalPropina("Total a pagar", totalPagar, modifier.weight(1f))
            CardTotalPropina("Propina", propina, modifier.weight(1f))
        }

        //Creamos los campos para capturarlos
        OutlinedTextField(
            value = nombrePlatillo,
            onValueChange = {nombrePlatillo = it},
            label ={ Text("Nombre del platillo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cantidad,
            onValueChange = {cantidad = it},
            label = { Text(text = "Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = precioUnitario,
            onValueChange = {precioUnitario = it},
            label = {Text("Precio unitario")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = porcentajePropina,
            onValueChange = {porcentajePropina = it},
            label = {Text("Porcentaje de propina")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        //Mostramos mensaje en caso de que hayan errores
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        //Boton para hacer los calculos
        Button(
            onClick ={
                //Convertimos a numeros la cantidad, el precio y el porcentaje
                val c = cantidad.toIntOrNull()
                val pu = precioUnitario.toDoubleOrNull()
                val p = porcentajePropina.toDoubleOrNull()
                when {
                    c == null || pu == null || p == null -> error = "Digite valores numéricos válidos"
                    p !in 0.0..10.0 -> error  = "El porcentaje debe ser entre 0 y 10"
                    else -> {
                        propina = pu * c * p / 100
                        totalPagar = pu * c + propina
                        error = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Total")
        }

        //Boton para limpiar los campos
        OutlinedButton(
            onClick = {
                nombrePlatillo = ""
                cantidad = ""
                precioUnitario = ""
                porcentajePropina = ""
                error = null
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Limpiar")
        }
    }
}