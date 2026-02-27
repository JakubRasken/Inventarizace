package cz.gypridilna.inventarizace.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object Scanner : Screen("scanner", "Scanner", Icons.Default.QrCodeScanner)
    object AddItem : Screen("add_item", "Add Item", Icons.Default.Add)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object AccessSystem : Screen("access_system", "Access", Icons.Default.VpnKey)
    object Profile : Screen("profile/{itemId}", "Profile") {
        fun createRoute(itemId: String) = "profile/$itemId"
    }
}
