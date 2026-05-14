package co.edu.cececar.uicomponents.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cececar.uicomponents.ui.screen.AlertScreen
import co.edu.cececar.uicomponents.ui.screen.CheckboxGroupScreen
import co.edu.cececar.uicomponents.ui.screen.DropDowmScreen
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
                    onDropDowmScreen = {backStack.add(DropdownComponentRouete)},
                    onRabioButtonScreen = {backStack.add(RabioButtonRouete)},
                    onPasswordField = {backStack.add(PasswordFieldRouete)},
                    onItemList = {backStack.add(ItemListRoute)},
                    onAlert = {backStack.add(AlertRoute)}

                )
            }

            entry<CheckBoxComponentRouete> { CheckboxGroupScreen() }
            entry<DropdownComponentRouete> { DropDowmScreen() }
            entry<RabioButtonRouete> { RadioButtonGroupScreen() }
            entry<PasswordFieldRouete> { PasswordFieldScreen() }
            entry<ItemListRoute> { ItemListScreen() }
            entry<AlertRoute> { AlertScreen() }


        })
}