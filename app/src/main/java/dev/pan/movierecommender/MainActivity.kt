package dev.pan.movierecommender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.pan.movierecommender.presenter.DetailsScreen
import dev.pan.movierecommender.presenter.FavoritesScreen
import dev.pan.movierecommender.presenter.HomeScreen
import dev.pan.movierecommender.presenter.HomeViewModel
import dev.pan.movierecommender.presenter.PopularScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

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

                val viewModel = hiltViewModel<HomeViewModel>()
                val state = viewModel.homeState.collectAsStateWithLifecycle().value

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            modifier = Modifier.padding(
                                WindowInsets.navigationBars.asPaddingValues()
                            ),
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            //val currentDestination = navBackStackEntry?.destination
                            val currentRoute = navBackStackEntry?.destination?.route
                            bottomNavItems.forEach { screen ->
                                BottomNavigationItem(
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        navController.navigate(screen.route)
                                        {
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when re-selecting a previously selected item
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(
                                        painterResource(id = screen.icon),
                                        contentDescription = screen.label,
                                        modifier = Modifier
                                            .size(24.dp)
                                    ) }
                                )
                            }
                        }
                    }
                ) { padding ->
                    // add routes here for each screen you navigate
                    NavHost(navController = navController, startDestination = "Home") {
                        // TODO: last item gets chopped off
                        composable(Screen.Home.route) {
                            HomeScreen(navController, state = state, modifier = Modifier.padding(padding))
                        }
                        composable(Screen.Favorites.route) { FavoritesScreen(navController) }
                        composable(
                            route = Screen.Details.route + "/{id}",
                            arguments = listOf(navArgument("id") {type = NavType.IntType})
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id")
                            DetailsScreen(id = id, navController = navController, state = state, viewModel = viewModel)
                        }
                        composable(Screen.Popular.route) {
                            PopularScreen()
                        }
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

sealed class Screen(val route: String, val icon: Int, val label: String) {
    object Home : Screen("home", R.drawable.ic_home, "home")
    object Favorites : Screen("favorite", R.drawable.ic_favorite, "favorite")
    object Details : Screen("details", R.drawable.ic_baby, "details")
    object Popular : Screen("popular", R.drawable.ic_baby, "popular")
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Favorites
)