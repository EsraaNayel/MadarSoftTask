package com.esraa.nayel.madarsofttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esraa.nayel.madarsofttask.ui.DependencyProvider
import com.esraa.nayel.madarsofttask.ui.viewmodel.UserViewModel
import com.esraa.nayel.madarsofttask.ui.screens.display.DisplayScreen
import com.esraa.nayel.madarsofttask.ui.screens.input.InputScreen
import com.esraa.nayel.madarsofttask.ui.theme.MadarSoftTaskTheme

class MainActivity : ComponentActivity() {

    private lateinit var dependencyProvider: DependencyProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dependencyProvider = DependencyProvider(this)

        setContent {
            MadarSoftTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val navController = rememberNavController()

                        val userViewModel: UserViewModel = remember {
                            ViewModelProvider(
                                this@MainActivity,
                                dependencyProvider.provideViewModelFactory()
                            )[UserViewModel::class.java]
                        }
                        NavHost(
                            navController = navController,
                            startDestination = "input_screen"
                        ) {
                            composable("input_screen") {
                                InputScreen(
                                    viewModel = userViewModel,
                                    onNavigateToDisplay = {
                                        navController.navigate("display_screen")
                                    }
                                )
                            }
                            composable("display_screen") {
                                DisplayScreen(
                                    viewModel = userViewModel,
                                    onNavigateBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}