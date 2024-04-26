package com.paperloong.illuminometer.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paperloong.illuminometer.ui.detect.IlluminanceDetectScreen

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
@Composable
fun IlluminometerApp() {
    val navController = rememberNavController()
    IlluminometerNavHost(navController = navController)
}

@Composable
fun IlluminometerNavHost(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = Screen.IlluminanceDetect.route) {
        composable(route = Screen.IlluminanceDetect.route) {
            IlluminanceDetectScreen(snackbarHostState = snackbarHostState)
        }
    }
}