package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import malunki.MalunkiProvider

@Serializable
internal object MainRoute

@Serializable
internal data class MalunekRoute(val title: String)

@Composable
internal fun AppNavigation() {
    val navController = rememberNavController()
    val malunki = MalunkiProvider().getMalunki()

    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        composable<MainRoute> {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                malunki = malunki,
                onMalunekClick = { malunek ->
                    navController.navigate(MalunekRoute(title = malunek.title))
                }
            )
        }
        composable<MalunekRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MalunekRoute>()
            val malunek = malunki.find { it.title == route.title }
            if (malunek != null) {
                MalunekScreen(
                    modifier = Modifier.fillMaxSize(),
                    malunek = malunek,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}
