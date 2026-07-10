package net.uk.rodgers.beatslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.rememberNavBackStack
import net.uk.rodgers.beatslist.data.BeatsDatabase
import net.uk.rodgers.beatslist.data.BeatsRepository
import net.uk.rodgers.beatslist.ui.navigation.Destination
import net.uk.rodgers.beatslist.ui.navigation.NavGraph
import net.uk.rodgers.beatslist.ui.theme.BeatsListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = BeatsDatabase.getDatabase(this)
        val repository = BeatsRepository(database.songDao(), database.setListDao())

        setContent {
            BeatsListTheme {
                val backStack = rememberNavBackStack(Destination.Home)
                NavGraph(
                    backStack = backStack,
                    repository = repository,
                    onNavigate = { destination -> backStack.add(destination) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    }
}
