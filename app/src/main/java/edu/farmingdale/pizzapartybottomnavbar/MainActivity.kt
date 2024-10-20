package edu.farmingdale.pizzapartybottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.PizzaPartyBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaPartyBottomNavBarTheme {
                // Create NavController
                val navController: NavHostController = rememberNavController()

                // Call the NavigationGraph that includes the DrawerNavigationMenu
                NavigationGraph(navController)
            }
        }
    }
}