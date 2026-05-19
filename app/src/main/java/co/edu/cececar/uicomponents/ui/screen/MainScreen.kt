package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen(
    onCheckBoxScreen: () -> Unit = {},
    onRadioButtonScreen: () -> Unit = {},
    onDropDownScreen: () -> Unit = {},
    onAutoComplete: () -> Unit = {},
    onPasswordField: () -> Unit = {},
    onAlert: () -> Unit = {},
    onDatePicker: () -> Unit = {},
    onSpinner: () -> Unit = {},
    onMenuItem: () -> Unit = {},
    onKeyValueList: () -> Unit = {}
) {
    // Lista de items para mejor mantenibilidad
    val menuItems = listOf(
        "CheckBox" to onCheckBoxScreen,
        "RadioButton" to onRadioButtonScreen,
        "DropDown" to onDropDownScreen,
        "AutoComplete" to onAutoComplete,
        "PasswordField" to onPasswordField,
        "Alert" to onAlert,
        "DatePicker - TimePicker" to onDatePicker,
        "Spinner" to onSpinner,
        "Menu Item" to onMenuItem,
        "KeyValueList" to onKeyValueList
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { (title, onClick) ->
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}