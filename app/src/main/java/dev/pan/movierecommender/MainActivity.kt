package dev.pan.movierecommender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.pan.movierecommender.presenter.DetailsScreen
import dev.pan.movierecommender.presenter.FavoritesScreen
import dev.pan.movierecommender.presenter.HomeScreen
import dev.pan.movierecommender.presenter.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                // TODO: different lists for bottom bar and overall navigation
                val bottomItems = listOf("home", "favorite")
                val navigationItems = listOf("home", "favorite", "details")


                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            modifier = Modifier.padding(
                                WindowInsets.navigationBars.asPaddingValues()
                            ),
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            bottomItems.forEach{ screen ->
                                BottomNavigationItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == screen } == true,
                                    onClick = {
                                        navController.navigate(screen)
                                        {
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    },
                                    label = { Text(text = screen) },
                                    icon = { /*TODO*/ })
                            }
                        }
                    }
                ) { padding ->

                    // add routes here for each screen you navigate
                    NavHost(navController = navController, startDestination = "Home", modifier = Modifier.padding(padding)) {
                        composable("home") {
                            val viewModel = hiltViewModel<HomeViewModel>()
                            val state = viewModel.homeState.collectAsState().value
                            HomeScreen(navController, state = state)
                        }
                        composable("favorite") { FavoritesScreen(navController) }
                        composable("details") { DetailsScreen(navController) }
                    }
                }

                SetBarColor(color = MaterialTheme.colorScheme.background)



            }
        }
    }


    @Composable
    fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = color
            )
        }
    }
}
