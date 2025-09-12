package com.example.myjetpack1.ui

object NavRoutes {
    const val USER_LIST = "userList"
    // For the detail screen, we'll need to pass the user's ID (UUID from Login object)
    // Or, for simplicity now, we could pass the whole user object if it's Parcelable.
    // However, the RandomUser API doesn't provide a direct single user fetch by ID easily,
    // and our current User object is not Parcelable.
    // Let's plan to pass the user's UUID as an argument.
    // The detail screen would then ideally find this user from the already loaded data,
    // or we'd need a way to fetch/pass the full user object.

    // Given our current setup, the easiest way to show a detail screen without
    // a new API call or complex state sharing is to pass the necessary user data
    // as navigation arguments. Gson can help serialize/deserialize for this.
    // Let's define a route that expects a serialized User object as a String argument.
    const val USER_DETAIL_ROUTE = "userDetail"
    const val USER_DETAIL_ARG_KEY = "userJson"
    const val USER_DETAIL = "$USER_DETAIL_ROUTE/{$USER_DETAIL_ARG_KEY}"

    fun userDetailScreen(userJson: String) = "$USER_DETAIL_ROUTE/$userJson"
}