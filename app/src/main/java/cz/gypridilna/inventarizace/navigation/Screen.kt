package cz.gypridilna.inventarizace.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Scanner : Screen("scanner", "Scanner", Icons.Filled.QrCodeScanner)
    object AddItem : Screen("add_item", "Add Item", Icons.Default.Add)
    object Search : Screen("search", "Search", Icons.Default.Search)
}
