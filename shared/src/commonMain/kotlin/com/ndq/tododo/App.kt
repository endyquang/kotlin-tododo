package com.ndq.tododo
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ndq.tododo.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val isSystemDark = isSystemInDarkTheme()
    var isDark by remember { mutableStateOf(isSystemDark) }
    AppTheme(darkTheme = isDark) {
        Scaffold {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { isDark = !isDark }) {
                    Text("Change theme!")
                }
            }
        }
    }
}
