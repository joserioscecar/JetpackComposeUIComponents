package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.DatePickerConfig
import co.edu.cececar.uicomponents.ui.component.DatePickerField
import co.edu.cececar.uicomponents.ui.component.DateRangePickerField

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


        var fecha by remember { mutableStateOf<Long?>(null) }

        DatePickerField(
            selectedDateMillis = fecha,

            onDateSelected = { fecha = it }
        )



        DatePickerField(
            selectedDateMillis = fecha,
            onDateSelected = { fecha = it },
            config = DatePickerConfig(
                label = "Fecha de nacimiento",
                maxDateMillis = System.currentTimeMillis(),
                clearable = false
            )
        )


        var rango by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }

        DateRangePickerField(
            startDateMillis = rango.first,
            endDateMillis = rango.second,
            onRangeSelected = { start, end -> rango = start to end },
            startLabel = "Check-in",
            endLabel = "Check-out"
        )

    }

}