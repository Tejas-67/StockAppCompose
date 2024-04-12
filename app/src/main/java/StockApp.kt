import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tejas.stocksappcompose.R
import com.tejas.stocksappcompose.presentation.company_listings.CompanyListingsScreen

@Composable
fun StockApp(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = StockDestinations.valueOf(
        backStackEntry?.destination?.route?: StockDestinations.CompanyListings.name
    )

    Scaffold{
        NavHost(
            navController = navController,
            startDestination = StockDestinations.CompanyListings.name,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            composable(route = StockDestinations.CompanyListings.name){
                CompanyListingsScreen()
            }
            composable(route = StockDestinations.CompanyInfo.name){
                //add the screen here
            }
        }
    }
}

enum class StockDestinations(@StringRes val title: Int){
    CompanyListings(title = R.string.company_listings),
    CompanyInfo(title = R.string.company_info)
}