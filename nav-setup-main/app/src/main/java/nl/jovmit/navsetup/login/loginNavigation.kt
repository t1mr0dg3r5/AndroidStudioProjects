package nl.jovmit.navsetup.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavGraphBuilder.loginScreen(
     onNavigateToSignUp: () -> Unit,
     onNavigateToMain: () -> Unit
 ) {
     composable<LoginDestination> {
         LoginScreen(
             onNavigateToSignUp = onNavigateToSignUp,
             onNavigateToMain = onNavigateToMain
         )
     }
 }

@Serializable
data object LoginDestination

fun NavController.navigateToLogin() {
    navigate(LoginDestination)
}