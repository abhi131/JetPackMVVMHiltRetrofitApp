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
import androidx.paging.compose.itemKey // Ensure this is androidx.paging.compose.itemKey
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

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = { query -> viewModel.searchUsers(query) },
            label = { Text("Search Users") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // It's often clearer to handle the overall refresh state first
        when (val refreshState = lazyUserItems.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${refreshState.error.localizedMessage}", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { lazyUserItems.retry() }) {
                        Text("Retry")
                    }
                }
            }
            is LoadState.NotLoading -> {
                if (lazyUserItems.itemCount == 0 && searchQuery.isNotEmpty()) { // Show only if search is performed and no results
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No users found for '$searchQuery'.")
                    }
                } else if (lazyUserItems.itemCount == 0 && searchQuery.isEmpty()) { // Initial empty state or empty after clearing search
                     Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Search for users to see results.")
                    }
                } else {
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

                        // Handle append state only when not in refresh loading/error
                        // and when there are items
                        when (val appendState = lazyUserItems.loadState.append) {
                            is LoadState.Loading -> {
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            is LoadState.Error -> {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Error: ${appendState.error.localizedMessage}", color = MaterialTheme.colorScheme.error)
                                        Button(onClick = { lazyUserItems.retry() }) {
                                            Text("Retry")
                                        }
                                    }
                                }
                            }
                            is LoadState.NotLoading -> {
                                if (appendState.endOfPaginationReached && lazyUserItems.itemCount > 0) {
                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                           Text("You've reached the end!")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
