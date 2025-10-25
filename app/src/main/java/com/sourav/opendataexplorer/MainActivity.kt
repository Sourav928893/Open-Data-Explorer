package com.sourav.opendataexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sourav.opendataexplorer.ui.navigation.AppNavigation
import com.sourav.opendataexplorer.ui.theme.OpenDataExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenDataExplorerTheme {
                AppNavigation()
            }
        }
    }
}