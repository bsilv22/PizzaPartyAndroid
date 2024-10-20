package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavigationMenu(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFFF3E5F5),  // Light purple background
                modifier = Modifier.width(250.dp)
            ) {
                Spacer(Modifier.height(24.dp))
                listOf(
                    "Pizza Order" to Icons.Default.ShoppingCart,
                    "GPA App" to Icons.Default.Info,
                    "Screen3" to Icons.Default.Person
                ).forEach { (title, icon) ->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(title) },
                        selected = false,
                        onClick = {
                            when (title) {
                                "Pizza Order" -> navController.navigate(BottomNavigationItems.PizzaScreen.route)
                                "GPA App" -> navController.navigate(BottomNavigationItems.GpaAppScreen.route)
                                "Screen3" -> navController.navigate(BottomNavigationItems.Screen3.route)
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { },  // Empty title
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color(0xFF673AB7)  // Deep purple color for the top bar
                        )
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    )
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    DrawerNavigationMenu(navController) {
        NavHost(navController, startDestination = BottomNavigationItems.PizzaScreen.route) {
            composable(BottomNavigationItems.PizzaScreen.route) {
                PizzaPartyScreen()
            }
            composable(BottomNavigationItems.GpaAppScreen.route) {
                GpaAppScreen()
            }
            composable(BottomNavigationItems.Screen3.route) {
                Screen3()
            }
        }
    }
}