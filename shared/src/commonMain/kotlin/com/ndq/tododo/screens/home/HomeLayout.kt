package com.ndq.tododo.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    menuButton: @Composable () -> Unit,
    searchInput: @Composable (modifier: Modifier) -> Unit,
    profileButton: @Composable () -> Unit,
    floatingAddButton: @Composable (modifier: Modifier) -> Unit,
    body: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = { menuButton() },
                title = { searchInput(Modifier.fillMaxWidth()) },
                actions = { profileButton() }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            body()
            floatingAddButton(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 20.dp, vertical = 28.dp),
            )
        }
    }
}
