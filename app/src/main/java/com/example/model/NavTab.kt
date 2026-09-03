package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavTab(
    val title: String,
    val icon: ImageVector,
    val badgeCount: ((Int) -> Int?)? = null
) {
    HOME("Home", Icons.Default.Home),
    TABLE("Table", Icons.Default.GridView),
    CATEGORIES("Categories", Icons.Default.Category),
    COMPARE("Compare", Icons.Default.CompareArrows),
    QUIZ("Quiz", Icons.Default.Quiz),
    FAVORITES("Favorites", Icons.Default.Bookmark),
    LEARN("Learn", Icons.AutoMirrored.Filled.MenuBook)
}
