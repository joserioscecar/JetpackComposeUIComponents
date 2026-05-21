package co.edu.cececar.uicomponents.navegation

import co.edu.cececar.uicomponents.ui.screen.SpinnerScreen
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cececar.uicomponents.ui.screen.AlertScreen
import co.edu.cececar.uicomponents.ui.screen.AutoCompleteScreen
import co.edu.cececar.uicomponents.ui.screen.CheckboxGroupScreen
import co.edu.cececar.uicomponents.ui.screen.DatePickerScreen
import co.edu.cececar.uicomponents.ui.screen.DropdowScreen
import co.edu.cececar.uicomponents.ui.screen.KeyValueListScreen

import co.edu.cececar.uicomponents.ui.screen.MainScreen
import co.edu.cececar.uicomponents.ui.screen.MenuItemScreen
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
                    onDropDownScreen = {backStack.add(DropDowmComponentRouete)},
                    onRadioButtonScreen = {backStack.add(RabioButtonRouete)},
                    onFieldScreen = {backStack.add(InputFieldRouete)},
                    onAlert = {backStack.add(AlertRoute)},
                    onDatePicker = {backStack.add(DatePickerRoute)},
                    onAutoComplete = {backStack.add(AutoCompleteRoute)},
                    onSpinner = {backStack.add(SpinnerRoute)},
                    onMenuItem = {backStack.add(MenuItemRoute)},
                    onDataList = {backStack.add(DataListRoute)}

                )
            }

            entry<CheckBoxComponentRouete> { CheckboxGroupScreen() }
            entry<DropDowmComponentRouete> { DropdowScreen() }
            entry<RabioButtonRouete> { RadioButtonGroupScreen() }
            entry<InputFieldRouete> { PasswordFieldScreen() }
            entry<AlertRoute> { AlertScreen() }
            entry<DatePickerRoute> { DatePickerScreen() }
            entry<AutoCompleteRoute> { AutoCompleteScreen() }
            entry<SpinnerRoute> { SpinnerScreen() }
            entry<MenuItemRoute> { MenuItemScreen() }
            entry<DataListRoute> { KeyValueListScreen() }

        })
}