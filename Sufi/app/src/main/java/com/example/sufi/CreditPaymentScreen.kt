package com.example.sufi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

data class CreditDetails(
    val creditNumber: String,
    val creditType: String,
    val paymentDeadline: String
)

@Composable
fun CreditPaymentScreen(
    amountToPay: String = "$0.00",
    totalAmount: String = "$48,167,736.77",
    creditDetails: CreditDetails = CreditDetails(
        creditNumber = "0000020000153539",
        creditType = "VEHÍCULOS",
        paymentDeadline = "01/11/2025"
    ),
    showMinimumPayment: Boolean = true,
    minimumPaymentLabel: String = "Valor pago mínimo",
    onNavigateToPaymentForm: () -> Unit
) {
    var selectedPaymentOption by remember { mutableStateOf("total") }
    var customAmount by remember { mutableStateOf("") }
    var showExternalLinkDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Tarjeta "Valor a pagar"
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF3F3F51))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Valor a pagar:",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Contenido
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icono de dinero
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            tint = Color(0xFFDC143C),
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Monto
                        Text(
                            text = amountToPay,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C2C2C)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(
                            modifier = Modifier.width(120.dp),
                            color = Color(0xFFE0E0E0),
                            thickness = 1.dp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Radio button valor mínimo (opcional)
                        if (showMinimumPayment) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = false,
                                    onClick = { },
                                    colors = RadioButtonDefaults.colors(
                                        unselectedColor = Color(0xFFCCCCCC)
                                    )
                                )
                                Text(
                                    text = minimumPaymentLabel,
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección "También puedes seleccionar otro valor a pagar"
            Text(
                text = "También puedes seleccionar otro valor a pagar:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C),
                modifier = Modifier.padding(bottom = 12.dp)
            )

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

            // Campo de entrada personalizado
            OutlinedTextField(
                value = customAmount,
                onValueChange = {
                    customAmount = it
                    selectedPaymentOption = "other"
                },
                placeholder = {
                    Text(
                        "Digita el valor que q...",
                        fontSize = 14.sp,
                        color = Color(0xFF999999)
                    )
                },
                leadingIcon = {
                    Text(
                        text = "$",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF666666)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF666666),
                    unfocusedBorderColor = Color(0xFFCCCCCC)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Detalles del crédito
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3F3F51))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Detalles del crédito:",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // Campos de detalles
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailItem(
                    label = "Número de crédito:",
                    value = creditDetails.creditNumber,
                    modifier = Modifier.weight(1f)
                )
                DetailItem(
                    label = "Tipo de crédito:",
                    value = creditDetails.creditType,
                    modifier = Modifier.weight(1f)
                )
                DetailItem(
                    label = "Fecha límite de pago:",
                    value = creditDetails.paymentDeadline,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de advertencia
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3F3)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color(0xFFDC143C),
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Si realizas el pago total de tu crédito, ten en cuenta que podrían quedar valores generados y no cobrados pendientes por cancelar. Comunícate al 018000517834 para mayor información.",
                        fontSize = 13.sp,
                        color = Color(0xFF2C2C2C),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { /* Cancelar */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFDC143C)
                    )
                ) {
                    Text(
                        "CANCELAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { showExternalLinkDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC143C)
                    )
                ) {
                    Text(
                        "REALIZAR EL PAGO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Diálogo de confirmación de enlace externo
    if (showExternalLinkDialog) {
        ExternalLinkDialog(
            onDismiss = { showExternalLinkDialog = false },
            onConfirm = {
                showExternalLinkDialog = false
                onNavigateToPaymentForm()
            }
        )
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(end = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF666666),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C2C2C)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            thickness = DividerDefaults.Thickness, color = Color(0xFFE0E0E0)
        )
    }
}

@Composable
fun ExternalLinkDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(12.dp),
        icon = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = Color(0xFFFFA726),
                        shape = RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✋",
                    fontSize = 32.sp
                )
            }
        },
        title = null,
        text = {
            Text(
                text = "Estás a punto de abrir una página externa al sitio oficial de Sufi, al hacerlo ingresarás a la pasarela de pagos Wompi. ¿Deseas continuar?",
                fontSize = 15.sp,
                color = Color(0xFF2C2C2C),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth(0.48f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC143C)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Sí",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },

        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth(0.48f)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFDC143C)
                ),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = Color(0xFFDC143C)
                )
            ) {
                Text(
                    text = "No",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }


    )
}