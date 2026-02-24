package cz.gypridilna.inventarizace.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.gypridilna.inventarizace.data.InventoryRepository
import cz.gypridilna.inventarizace.data.entities.InventoryItem
import cz.gypridilna.inventarizace.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel : ViewModel() {

    private val repository = InventoryRepository(RetrofitClient.apiService)

    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchedItems: StateFlow<List<InventoryItem>> = _searchQuery
        .combine(_inventoryItems) { query, items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter {
                    (it.name?.contains(query, ignoreCase = true) == true) ||
                            (it.rack?.contains(query, ignoreCase = true) == true) ||
                            (it.position?.contains(query, ignoreCase = true) == true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        fetchInventory()
    }

    private fun fetchInventory() {
        viewModelScope.launch {
            try {
                _inventoryItems.value = repository.getInventory()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun findItemByBarcode(barcode: String): InventoryItem? {
        // Updated logic: Check if the item's ID ends with the scanned barcode.
        return _inventoryItems.value.find { it.id?.endsWith(barcode) == true }
    }
}
