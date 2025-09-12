package com.example.myjetpack1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.myjetpack1.model.User
import com.example.myjetpack1.viewmodel.UserViewModel
import com.example.myjetpack1.ui.components.UserItem

@Composable
fun UserListScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onUserClick: (User) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lazyUserItems = viewModel.users.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
        TextField(
            value = searchQuery,
            onValueChange = { query -> viewModel.searchUsers(query) },
            label = { Text("Search Users") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                count = lazyUserItems.itemCount,
                key = lazyUserItems.itemKey { user -> user.login.uuid } 
            ) { index ->
                val user = lazyUserItems[index]
                if (user != null) {
                    UserItem(user = user, onClick = { onUserClick(user) })
                }
            }

            lazyUserItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        if (lazyUserItems.itemCount == 0) { // Only show if the list is empty
                            item {
                                Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyUserItems.loadState.refresh as LoadState.Error
                        item {
                            Column(modifier = Modifier.fillParentMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Error: ${e.error.localizedMessage}", color = MaterialTheme.colorScheme.error)
                                Button(onClick = { retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                         val e = lazyUserItems.loadState.append as LoadState.Error
                        item {
                             Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Error: ${e.error.localizedMessage}", color = MaterialTheme.colorScheme.error)
                                Button(onClick = { retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
