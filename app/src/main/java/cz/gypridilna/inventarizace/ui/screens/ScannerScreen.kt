package cz.gypridilna.inventarizace.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.gypridilna.inventarizace.data.entities.InventoryItem
import cz.gypridilna.inventarizace.ui.InventoryViewModel
import cz.gypridilna.inventarizace.ui.components.CameraView

@Composable
fun ScannerScreen(viewModel: InventoryViewModel = viewModel()) {
    val context = LocalContext.current
    var hasCamPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    var scannedBarcode by remember { mutableStateOf<String?>(null) }
    val scannedItem: InventoryItem? = scannedBarcode?.let { viewModel.findItemByBarcode(it) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (hasCamPermission) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                onBarcodeScanned = { barcode ->
                    scannedBarcode = barcode
                }
            )
        } else {
            Text("Camera permission required")
        }

        scannedItem?.let {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = it.name ?: "N/A")
                    Text(text = "Rack: ${it.rack ?: "-"}, Position: ${it.position ?: "-"}, Box: ${it.box ?: "-"}")
                    Text(text = "ID: ${it.id ?: "N/A"}")
                }
            }
        }
    }
}
