package com.example.sufi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentForm() {


    var selectedPaymentOption by remember { mutableStateOf("total") }
    var cardholderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var installments by remember { mutableStateOf("") }
    var documentId by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(true) }

    var expandedDropdown by remember { mutableStateOf(false) }
    var selectedDocumentType by remember { mutableStateOf("") }

    val documentTypes = listOf(
        "Cédula de ciudadanía",
        "Cédula de extranjería",
        "Pasaporte",
        "NIT"
    )

    val totalAmount = "$48,167,736.77"
    val options = listOf("Total", "Parcial")


    val (selected, onSelected) = remember { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header con iconos de tarjetas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tarjeta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2D1B69)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Aquí irían los logos de las tarjetas (Visa, Discover, Amex, Mastercard)
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Toggle",
                                tint = Color(0xFF2D1B69)
                            )
                        }
                    }
                }

                if (expanded) {

                    if (nameError) {
                        Text(
                            text = "Please enter correct name on card",
                            color = Color(0xFFE74C3C),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Número de tarjeta
                    Text(
                        text = "Número de Tarjeta",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = {
                            if (it.length <= 19) cardNumber = formatCardNumber(it)
                        },
                        placeholder = { Text("0000 0000 0000 0000") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6C5CE7),
                            unfocusedBorderColor = Color.LightGray
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha de expiración y CVC
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Fecha de expiración",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            OutlinedTextField(
                                value = expiryDate,
                                onValueChange = {
                                    if (it.length <= 5) expiryDate = formatExpiryDate(it)
                                },
                                placeholder = { Text("MM/AA") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF6C5CE7),
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                singleLine = true
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Código CVC",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            OutlinedTextField(
                                value = cvc,
                                onValueChange = {
                                    if (it.length <= 4 && it.all { c -> c.isDigit() }) cvc = it
                                },
                                placeholder = { Text("CVC") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF6C5CE7),
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cuotas
                    Text(
                        text = "Cuotas",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = installments,
                        onValueChange = { installments = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6C5CE7),
                            unfocusedBorderColor = Color.LightGray
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

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


                    // Documento de Identidad
                    Text(
                        text = "Documento de Identidad (CC)",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = documentId,
                        onValueChange = { documentId = it },
                        placeholder = { Text("Documento de Identidad (CC)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6C5CE7),
                            unfocusedBorderColor = Color.LightGray
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))


                    // Opción Pago total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPaymentOption == "total",
                            onClick = { selectedPaymentOption = "total" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFDC143C)
                            )
                        )
                        Column {
                            Text(
                                text = "Pago total:",
                                fontSize = 14.sp,
                                color = Color(0xFF2C2C2C)
                            )
                            Text(
                                text = totalAmount,
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Opción Otro valor
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPaymentOption == "other",
                            onClick = { selectedPaymentOption = "other" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFDC143C)
                            )
                        )
                        Text(
                            text = "Otro valor",
                            fontSize = 14.sp,
                            color = Color(0xFF2C2C2C)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))


/*
                    Text(
                        text = "Pago",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectableGroup()
                    ) {
                        options.forEach { option ->
                            val selected = null
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                                    .selectable(
                                        selected = (option == selected),
                                        onClick = { onSelected(option) },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (option == selected),
                                    onClick = null
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }


*/

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { false }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "He leido y acepto los terminos y condiciones",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }



                    // Botón Enviar pago
                    Button(
                        onClick = { /* Procesar pago */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C5CE7)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Enviar pago",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mensaje de seguridad
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Secure",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Pagos encriptados y seguros",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Términos y condiciones
                    Text(
                        text = "Al finalizar la compra, aceptas nuestros Términos de Servicio y confirmas que has leído nuestra Política de Privacidad. Puedes cancelar los pagos recurrentes en cualquier momento.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

private fun RowScope.onSelected(option: String) {}

fun formatCardNumber(input: String): String {
    val digits = input.filter { it.isDigit() }
    return digits.chunked(4).joinToString(" ").take(19)
}

fun formatExpiryDate(input: String): String {
    val digits = input.filter { it.isDigit() }
    return when {
        digits.length <= 2 -> digits
        else -> "${digits.take(2)}/${digits.drop(2).take(2)}"
    }
}