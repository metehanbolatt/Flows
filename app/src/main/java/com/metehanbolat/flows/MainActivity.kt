package com.metehanbolat.flows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.metehanbolat.flows.ui.theme.FlowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowsTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}
