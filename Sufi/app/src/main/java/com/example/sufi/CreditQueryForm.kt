package com.example.sufi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditQueryForm(onNavigateToCreditPayment: () -> Unit) {
    var selectedDocumentType by remember { mutableStateOf("") }
    var documentNumber by remember { mutableStateOf("") }
    var paymentReference by remember { mutableStateOf("") }
    var expandedDropdown by remember { mutableStateOf(false) }

    val documentTypes = listOf(
        "Cédula de ciudadanía",
        "Cédula de extranjería",
        "Pasaporte",
        "NIT"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Por favor ingresa la siguiente información, así consultaremos el valor de la cuota de tu crédito.",
                fontSize = 16.sp,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Dropdown - Tipo de documento
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDocumentType,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            "Elige el tipo de documento",
                            fontSize = 14.sp
                        )
                    },
                    placeholder = {
                        Text(
                            "*Por favor selecciona tu tipo de documento de identidad",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF666666),
                        unfocusedBorderColor = Color(0xFFCCCCCC)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    documentTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedDocumentType = type
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de número de documento
            OutlinedTextField(
                value = documentNumber,
                onValueChange = { documentNumber = it },
                label = {
                    Text(
                        "Ingresa el número del documento",
                        fontSize = 14.sp
                    )
                },
                placeholder = {
                    Text(
                        "*Digita el número de tu documento de identidad",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF666666),
                    unfocusedBorderColor = Color(0xFFCCCCCC)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de referencia de pago
            OutlinedTextField(
                value = paymentReference,
                onValueChange = { paymentReference = it },
                label = {
                    Text(
                        "Ingresa el número de referencia de pago",
                        fontSize = 14.sp
                    )
                },
                placeholder = {
                    Text(
                        "*Digita el número de tu referencia de pago",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF666666),
                    unfocusedBorderColor = Color(0xFFCCCCCC)
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Cancelar
                OutlinedButton(
                    onClick = {
                        // Acción de cancelar
                        selectedDocumentType = ""
                        documentNumber = ""
                        paymentReference = ""
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFDC143C)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp
                    )
                ) {
                    Text(
                        "CANCELAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Botón Consultar
                Button(
                    onClick = {
                        // Acción de consultar
                        onNavigateToCreditPayment()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC143C),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "CONSULTAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}