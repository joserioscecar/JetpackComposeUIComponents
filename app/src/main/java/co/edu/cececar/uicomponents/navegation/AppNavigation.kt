package co.edu.cececar.uicomponents.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cececar.uicomponents.ui.screen.AlertScreen
import co.edu.cececar.uicomponents.ui.screen.AutoCompleteScreen
import co.edu.cececar.uicomponents.ui.screen.CheckboxGroupScreen
import co.edu.cececar.uicomponents.ui.screen.DatePickerScreen
import co.edu.cececar.uicomponents.ui.screen.SelectScreen
import co.edu.cececar.uicomponents.ui.screen.ItemListScreen

import co.edu.cececar.uicomponents.ui.screen.MainScreen
import co.edu.cececar.uicomponents.ui.screen.PasswordFieldScreen
import co.edu.cececar.uicomponents.ui.screen.RadioButtonGroupScreen

@Composable
fun AppNavigation() {

    val backStack = rememberNavBackStack(MainRouete)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<MainRouete> {

                MainScreen(
                    onCheckBoxScreen = {backStack.add(CheckBoxComponentRouete)},
                    onSelectFielScreen = {backStack.add(SelectComponentRouete)},
                    onRabioButtonScreen = {backStack.add(RabioButtonRouete)},
                    onPasswordField = {backStack.add(PasswordFieldRouete)},
                    onItemList = {backStack.add(ItemListRoute)},
                    onAlert = {backStack.add(AlertRoute)},
                    onDatePicker = {backStack.add(DatePickerRoute)},
                    onAutoComplete = {backStack.add(AutoCompleteRoute)}

                )
            }

            entry<CheckBoxComponentRouete> { CheckboxGroupScreen() }
            entry<SelectComponentRouete> { SelectScreen() }
            entry<RabioButtonRouete> { RadioButtonGroupScreen() }
            entry<PasswordFieldRouete> { PasswordFieldScreen() }
            entry<ItemListRoute> { ItemListScreen() }
            entry<AlertRoute> { AlertScreen() }
            entry<DatePickerRoute> { DatePickerScreen() }
            entry<AutoCompleteRoute> { AutoCompleteScreen() }


        })
}