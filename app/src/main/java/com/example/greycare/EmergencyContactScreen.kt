package com.example.greycare

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Contact", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Contact Help Instantly", fontSize = 22.sp)

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:1234567890") // ✅ Change to actual emergency contact
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📞 Call Emergency Contact", fontSize = 18.sp)
            }

            Button(
                onClick = {
                    val smsIntent = Intent(Intent.ACTION_VIEW)
                    smsIntent.data = Uri.parse("sms:1234567890") // ✅ Change to actual number
                    smsIntent.putExtra("sms_body", "This is an emergency. Please help.")
                    context.startActivity(smsIntent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✉️ Send Emergency SMS", fontSize = 18.sp)
            }
        }
    }
}
