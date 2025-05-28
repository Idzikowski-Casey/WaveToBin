package com.application.wavetobin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun WaveCard(modifier: Modifier = Modifier, title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier.fillMaxWidth(0.5f).padding(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Heading(Modifier.padding(bottom = 8.dp),title)
            content()
        }
    }
}

@Composable
private fun Heading(modifier: Modifier = Modifier, string: String) {
    Text(modifier = modifier, text = string, style = MaterialTheme.typography.titleLarge, textDecoration = TextDecoration.Underline)
}

@Composable
fun InfoSection(modifier: Modifier = Modifier, title: String, info: String) {

    Row(modifier.fillMaxWidth()) {
        Text("$title: ", style = MaterialTheme.typography.titleMedium)
        Text(info, fontStyle = FontStyle.Italic)
    }
}