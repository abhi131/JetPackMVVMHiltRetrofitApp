package com.example.myjetpack1.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myjetpack1.model.User
import com.example.myjetpack1.network.RandomUserApiService
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val apiService: RandomUserApiService,
    private val query: String // For search functionality later
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1 // API pages are 1-indexed
        return try {
            val response = apiService.getUsers(page = page, results = params.loadSize)
            val users = response.results.filter { user ->
                // Basic filtering for name or email
                // We'll refine this later
                if (query.isBlank()) {
                    true
                } else {
                    val fullName = "${user.name.first} ${user.name.last}".lowercase()
                    val email = user.email.lowercase()
                    fullName.contains(query.lowercase()) || email.contains(query.lowercase())
                }
            }

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (users.isEmpty() && query.isBlank()) null else page + 1 // if query active and no users, could mean no more matching users
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}