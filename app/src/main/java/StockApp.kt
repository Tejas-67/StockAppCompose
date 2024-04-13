import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tejas.stocksappcompose.R
import com.tejas.stocksappcompose.presentation.company_info.CompanyInfoScreen
import com.tejas.stocksappcompose.presentation.company_listings.CompanyListingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockApp(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold{
        NavHost(
            navController = navController,
            startDestination = "companyListings",
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            composable(route = "companyListings"){
                CompanyListingsScreen(navController)
            }
            composable(
                route = "companyInfo/{symbol}",
                arguments = listOf(navArgument("symbol") { type = NavType.StringType })
            ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
                CompanyInfoScreen(symbol = symbol)
            }
        }
    }
}