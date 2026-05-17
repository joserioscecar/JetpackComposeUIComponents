package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.DatePickerConfig
import co.edu.cececar.uicomponents.ui.component.DatePicker
import co.edu.cececar.uicomponents.ui.component.DateRangePicker
import co.edu.cececar.uicomponents.ui.component.TimePickerConfig
import co.edu.cececar.uicomponents.ui.component.TimePicker
import co.edu.cececar.uicomponents.ui.component.TimeRangePicker
import co.edu.cececar.uicomponents.ui.component.TimeValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("DatePicker")

        var fecha by remember { mutableStateOf<Long?>(null) }

        DatePicker(
            selectedDateMillis = fecha,
            onDateSelected = { fecha = it }
        )



        DatePicker(
            selectedDateMillis = fecha,
            onDateSelected = { fecha = it },
            config = DatePickerConfig(
                label = "Fecha reserva",
                minDateMillis = System.currentTimeMillis()  // hoy en adelante
            )
        )


        DatePicker(
            selectedDateMillis = fecha,
            onDateSelected = { fecha = it },
            config = DatePickerConfig(
                label = "Fecha de nacimiento",
                maxDateMillis = System.currentTimeMillis(),
                clearable = false
            )
        )



        Text("TimePicker")

        var rango by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }

        DateRangePicker(
            startDateMillis = rango.first,
            endDateMillis = rango.second,
            onRangeSelected = { start, end -> rango = start to end },
            startLabel = "De",
            endLabel = "Hasta"
        )


        var hora by remember { mutableStateOf<TimeValue?>(null) }

        TimePicker(
            selectedTime = hora,
            onTimeSelected = { hora = it },
            config = TimePickerConfig(
                label = "Hora de entrega",
                use24Hour = true,       // false → "2:30 PM"
                clearable = true
            )
        )

// Leer el valor
        hora?.hour      // 14
        hora?.minute    // 30
        hora?.format()              // "14:30"
        hora?.format(use24Hour = false)  // "2:30 PM"


        var inicio by remember { mutableStateOf<TimeValue?>(null) }
        var fin    by remember { mutableStateOf<TimeValue?>(null) }

        TimeRangePicker(
            startTime = inicio,
            endTime   = fin,
            onStartSelected = { inicio = it },
            onEndSelected   = { fin = it },
            startLabel = "Apertura",
            endLabel   = "Cierre"
        )


        TimePicker(
            selectedTime = hora,
            onTimeSelected = { time ->
                hora = time
                // se dispara al tocar Aceptar
                println("${time?.hour}:${time?.minute}")
            }
        )

    }

}