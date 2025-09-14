package com.example.myjetpack1.ui

object NavRoutes {
    const val USER_LIST = "userList"
    const val USER_DETAIL_ROUTE = "userDetail"
    const val USER_DETAIL_ARG_KEY = "userJson"
    const val USER_DETAIL = "$USER_DETAIL_ROUTE/{$USER_DETAIL_ARG_KEY}"

    fun userDetailScreen(userJson: String) = "$USER_DETAIL_ROUTE/$userJson"
}