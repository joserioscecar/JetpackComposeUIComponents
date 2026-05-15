package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/*


            entry<CheckBoxComponentRouete> { CheckboxGroupScreen() }
            entry<DropdownComponentRouete> { DropDowmScreen() }
            entry<RabioButtonRouete> { RadioButtonGroupScreen() }
            entry<RabioButtonRouete> { RadioButtonGroupScreen() }
            entry<PasswordFieldRouete> { PasswordFieldScreen() }
            entry<ItemListRoute> { ItemListScreen() }


* */


@Composable
fun MainScreen(

    onCheckBoxScreen:()->Unit,
    onRabioButtonScreen:()->Unit,
    onDropDowmScreen:()->Unit,
    onPasswordField:()->Unit,
    onItemList:()->Unit,
    onAlert:()->Unit,
    onDatePicker:()->Unit

) {


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {


        Button(
            onClick = onCheckBoxScreen,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("CheckBox")
        }

        Button(
            onClick = onRabioButtonScreen,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("RabioButton")
        }

        Button(
            onClick = onDropDowmScreen,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("DropDowm")
        }

        Button(
            onClick = onPasswordField,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("PasswordField")
        }


        Button(
            onClick = onItemList,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("ItemList")
        }


        Button(
            onClick = onAlert,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Alert")
        }


        Button(
            onClick = onDatePicker,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("DatePicker")
        }

    }

}