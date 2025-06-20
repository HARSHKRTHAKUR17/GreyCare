package com.example.greycare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.greycare.ui.theme.GreyCareTheme

class VoiceAssistantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreyCareTheme {
                VoiceAssistantScreen()
            }
        }
    }
}
