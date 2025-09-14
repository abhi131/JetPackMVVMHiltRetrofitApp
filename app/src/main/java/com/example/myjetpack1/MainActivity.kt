package com.example.myjetpack1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myjetpack1.ui.NavRoutes
import com.example.myjetpack1.ui.screens.UserDetailScreen
import com.example.myjetpack1.ui.screens.UserListScreen
import com.example.myjetpack1.ui.theme.MyJetpack1Theme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyJetpack1Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val gson = remember { Gson() } // For serializing User object

    NavHost(navController = navController, startDestination = NavRoutes.USER_LIST) {
        composable(NavRoutes.USER_LIST) {
            UserListScreen(
                onUserClick = { user ->
                    try {
                        val userJson = gson.toJson(user)
                        val encodedUserJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8.name())
                        navController.navigate(NavRoutes.userDetailScreen(encodedUserJson))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        composable(
            route = NavRoutes.USER_DETAIL,
            arguments = listOf(navArgument(NavRoutes.USER_DETAIL_ARG_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUserJson = backStackEntry.arguments?.getString(NavRoutes.USER_DETAIL_ARG_KEY)
            if (encodedUserJson != null) {
                val userJsonResult by produceState<String?>(initialValue = null, producer = {
                    value = try {
                        URLDecoder.decode(encodedUserJson, StandardCharsets.UTF_8.name())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                })

                if (userJsonResult != null) {
                    UserDetailScreen(
                        userJson = userJsonResult!!,
                        onNavigateBack = { navController.popBackStack() }
                    )
                } else {
                    // Handle exceptions during URL decoding or if result is null
                    Text("Error decoding user data.")
                }
            } else {
                // Handle the case where the argument is missing
                Text("User data not found.")
            }
        }
    }
}

