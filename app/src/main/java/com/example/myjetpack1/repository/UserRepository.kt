package com.example.myjetpack1.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myjetpack1.model.User
import com.example.myjetpack1.network.RandomUserApiService
import com.example.myjetpack1.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: RandomUserApiService
) {
    fun getUsers(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items to load per page
                enablePlaceholders = false // Disable placeholders for now
            ),
            pagingSourceFactory = { UserPagingSource(apiService, query) }
        ).flow
    }
}