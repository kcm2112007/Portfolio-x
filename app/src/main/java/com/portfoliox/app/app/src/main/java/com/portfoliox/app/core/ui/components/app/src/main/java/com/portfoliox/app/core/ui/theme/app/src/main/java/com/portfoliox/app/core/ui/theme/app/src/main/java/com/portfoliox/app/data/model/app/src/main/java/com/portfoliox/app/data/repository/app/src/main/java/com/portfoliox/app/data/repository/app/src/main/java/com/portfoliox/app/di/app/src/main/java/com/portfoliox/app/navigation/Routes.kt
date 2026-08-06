package com.portfoliox.app.navigation

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login : Routes("login")
    data object Home : Routes("home")
    data object About : Routes("about")
    data object Skills : Routes("skills")
    data object Projects : Routes("projects")
    data object ProjectDetails : Routes("project_details/{projectId}") {
        fun createRoute(projectId: String) = "project_details/$projectId"
    }
    data object Certificates : Routes("certificates")
    data object Gallery : Routes("gallery")
    data object Blog : Routes("blog")
    data object Contact : Routes("contact")
    data object Settings : Routes("settings")
    data object AdminDashboard : Routes("admin_dashboard")
    data object AddEditProject : Routes("add_edit_project?projectId={projectId}") {
        fun createRoute(projectId: String? = null) = "add_edit_project?projectId=${projectId ?: ""}"
    }
    data object UploadCertificate : Routes("upload_certificate")
    data object UploadGallery : Routes("upload_gallery")
    data object ProfileEditor : Routes("profile_editor")
}
