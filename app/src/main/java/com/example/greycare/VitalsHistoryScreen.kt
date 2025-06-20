package com.example.greycare

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.greycare.models.Vitals
import com.example.greycare.models.VitalsDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.greycare.utils.exportVitalsToCSV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsHistoryScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { VitalsDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    var vitalsList by remember { mutableStateOf<List<Vitals>>(emptyList()) }

    var editingVitals by remember { mutableStateOf<Vitals?>(null) }
    var editedHeart by remember { mutableStateOf("") }
    var editedBP by remember { mutableStateOf("") }
    var editedSugar by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        scope.launch {
            db.vitalsDao().getAllVitals().collectLatest {
                vitalsList = it
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vitals History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)

        ) {
            // 🚀 Export Button
            Button(
                onClick = {
                    exportVitalsToCSV(context, vitalsList)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Export to CSV")
            }

            if (vitalsList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No vitals recorded yet.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(vitalsList) { vitals ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Heart Rate: ${vitals.heartRate} bpm")
                                Text("Blood Pressure: ${vitals.bloodPressure} mmHg")
                                Text("Glucose Level: ${vitals.glucoseLevel} mg/dL")
                                Text("Time: ${vitals.timestamp}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            editingVitals = vitals
                                            editedHeart = vitals.heartRate
                                            editedBP = vitals.bloodPressure
                                            editedSugar = vitals.glucoseLevel
                                        }
                                    ) {
                                        Text("Edit")
                                    }

                                    Button(
                                        onClick = {
                                            scope.launch {
                                                db.vitalsDao().delete(vitals)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                    ) {
                                        Text("Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            editingVitals?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Editing Entry", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = editedHeart,
                    onValueChange = { editedHeart = it },
                    label = { Text("Heart Rate") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = editedBP,
                    onValueChange = { editedBP = it },
                    label = { Text("Blood Pressure") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = editedSugar,
                    onValueChange = { editedSugar = it },
                    label = { Text("Glucose Level") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Button(onClick = {
                        scope.launch {
                            db.vitalsDao().update(
                                it.copy(
                                    heartRate = editedHeart,
                                    bloodPressure = editedBP,
                                    glucoseLevel = editedSugar
                                )
                            )
                            editingVitals = null
                        }
                    }) {
                        Text("Save")
                    }

                    OutlinedButton(onClick = {
                        editingVitals = null
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
