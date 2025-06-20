package com.example.greycare

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.greycare.models.Vitals
import com.example.greycare.models.VitalsDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsScreen(navController: NavController) {
    var heartRate by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var glucoseLevel by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = remember { VitalsDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vitals Tracker", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = { navController.navigate("history") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text("View History")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color.White)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = heartRate,
                onValueChange = { heartRate = it },
                label = { Text("Heart Rate (bpm)") },
                leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bloodPressure,
                onValueChange = { bloodPressure = it },
                label = { Text("Blood Pressure (mmHg)") },
                leadingIcon = { Icon(Icons.Filled.LocalHospital, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = glucoseLevel,
                onValueChange = { glucoseLevel = it },
                label = { Text("Glucose Level (mg/dL)") },
                leadingIcon = { Icon(Icons.Filled.WaterDrop, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (heartRate.isNotBlank() && bloodPressure.isNotBlank() && glucoseLevel.isNotBlank()) {
                        val vitals = Vitals(
                            heartRate = heartRate,
                            bloodPressure = bloodPressure,
                            glucoseLevel = glucoseLevel
                        )
                        scope.launch {
                            db.vitalsDao().insert(vitals)
                            Toast.makeText(context, "Vitals saved!", Toast.LENGTH_SHORT).show()
                        }

                        heartRate = ""
                        bloodPressure = ""
                        glucoseLevel = ""
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Vitals")
            }
        }
    }
}
