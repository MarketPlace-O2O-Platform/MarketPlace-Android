package dev.kichan.marketplace.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.unit.dp

val PAGE_HORIZONTAL_PADDING = 20.dp

val bottomNavItem = listOf(
    Page.Home to Icons.Filled.Home,
    Page.Like to Icons.Filled.ShoppingCart,
    Page.Map to Icons.Filled.Place,
    Page.My to Icons.Filled.Person,
)