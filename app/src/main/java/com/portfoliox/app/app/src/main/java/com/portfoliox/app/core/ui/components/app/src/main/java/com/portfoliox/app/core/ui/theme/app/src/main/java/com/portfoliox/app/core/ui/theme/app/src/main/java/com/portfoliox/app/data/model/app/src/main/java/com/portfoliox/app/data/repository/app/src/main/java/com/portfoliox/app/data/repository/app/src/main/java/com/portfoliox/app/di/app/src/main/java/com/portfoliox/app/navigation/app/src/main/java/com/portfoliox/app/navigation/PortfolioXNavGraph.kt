package com.portfoliox.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.portfoliox.app.core.ui.components.PlaceholderScreen
import com.portfoliox.app.features.about.AboutScreen
import com.portfoliox.app.features.home.HomeScreen
import com.portfoliox.app.features.skills.SkillsScreen
import com.portfoliox.app.features.splash.SplashScreen

@Composable
fun PortfolioXNavGraph(
    navController: NavHostController = rememberNavController(),
    onToggleTheme: () -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.Splash.route) {

        composable(Routes.Splash.route) {
            SplashScreen(
                onNavigateHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(
                onToggleTheme = onToggleTheme,
                onOpenAbout = { navController.navigate(Routes.About.route) },
                onOpenSkills = { navController.navigate(Routes.Skills.route) },
                onOpenProjects = { navController.navigate(Routes.Projects.route) },
                onOpenContact = { navController.navigate(Routes.Contact.route) }
            )
        }

        // --- Screens below are stubbed. Ask Claude to build each one out
        // the same way Home/Splash/About/Skills were built (ViewModel + Supabase repo). ---
        composable(Routes.Login.route) { PlaceholderScreen("Admin Login") }
        composable(Routes.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Skills.route) {
            SkillsScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Projects.route) { PlaceholderScreen("Projects") }
        composable(Routes.ProjectDetails.route) { PlaceholderScreen("Project Details") }
        composable(Routes.Certificates.route) { PlaceholderScreen("Certificates") }
        composable(Routes.Gallery.route) { PlaceholderScreen("Gallery") }
        composable(Routes.Blog.route) { PlaceholderScreen("Blog") }
        composable(Routes.Contact.route) { PlaceholderScreen("Contact") }
        composable(Routes.Settings.route) { PlaceholderScreen("Settings") }
        composable(Routes.AdminDashboard.route) { PlaceholderScreen("Admin Dashboard") }
        composable(Routes.AddEditProject.route) { PlaceholderScreen("Add / Edit Project") }
        composable(Routes.UploadCertificate.route) { PlaceholderScreen("Upload Certificate") }
        composable(Routes.UploadGallery.route) { PlaceholderScreen("Upload Gallery") }
        composable(Routes.ProfileEditor.route) { PlaceholderScreen("Profile Editor") }
    }
}
