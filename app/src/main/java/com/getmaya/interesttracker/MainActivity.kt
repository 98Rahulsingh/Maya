package com.getmaya.interesttracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("interest_scores", Context.MODE_PRIVATE)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    InterestScreen(
                        paidToFree = prefs.getInt("paid_to_free", 0),
                        freeToPaid = prefs.getInt("free_to_paid", 0),
                        onPaidToFreeTap = {
                            val next = prefs.getInt("paid_to_free", 0) + 1
                            prefs.edit().putInt("paid_to_free", next).apply()
                            next
                        },
                        onFreeToPaidTap = {
                            val next = prefs.getInt("free_to_paid", 0) + 1
                            prefs.edit().putInt("free_to_paid", next).apply()
                            next
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun InterestScreen(
    paidToFree: Int,
    freeToPaid: Int,
    onPaidToFreeTap: () -> Int,
    onFreeToPaidTap: () -> Int
) {
    var paidToFreeCount by remember { mutableIntStateOf(paidToFree) }
    var freeToPaidCount by remember { mutableIntStateOf(freeToPaid) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Maya – User Interest", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Choose the change you are more interested in. Every switch-on tap adds +1.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))
        InterestOption(
            title = "Paid → Free",
            count = paidToFreeCount,
            onTap = { paidToFreeCount = onPaidToFreeTap() }
        )
        Spacer(Modifier.height(12.dp))
        InterestOption(
            title = "Free → Paid",
            count = freeToPaidCount,
            onTap = { freeToPaidCount = onFreeToPaidTap() }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            "Current leading choice: " + when {
                paidToFreeCount > freeToPaidCount -> "Paid → Free"
                freeToPaidCount > paidToFreeCount -> "Free → Paid"
                else -> "Equal interest"
            },
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Scores are saved only on this device. They are not uploaded or combined with other users.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun InterestOption(title: String, count: Int, onTap: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text("Interest score: $count", style = MaterialTheme.typography.bodyMedium)
            }
            Switch(
                checked = false,
                onCheckedChange = { enabled -> if (enabled) onTap() }
            )
        }
    }
}
