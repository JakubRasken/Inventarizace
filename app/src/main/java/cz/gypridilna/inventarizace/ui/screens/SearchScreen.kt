package cz.gypridilna.inventarizace.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.gypridilna.inventarizace.navigation.Screen
import cz.gypridilna.inventarizace.ui.InventoryViewModel

@Composable
fun SearchScreen(viewModel: InventoryViewModel = viewModel(), navController: NavController) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val items by viewModel.searchedItems.collectAsState()

    Column {
        TextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        )
        LazyColumn {
            items(items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { item.id?.let { navController.navigate(Screen.Profile.createRoute(it)) } },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = item.name ?: "N/A")
                        Text(text = "Rack: ${item.rack ?: "-"}, Position: ${item.position ?: "-"}, Box: ${item.box ?: "-"}")
                        Text(text = "ID: ${item.id ?: "N/A"}")
                    }
                }
            }
        }
    }
}
