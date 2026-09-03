package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavTab
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun AppBottomNavigation(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit
) {
    val tabs = listOf(
        NavTab.HOME,
        NavTab.TABLE,
        NavTab.CATEGORIES,
        NavTab.COMPARE,
        NavTab.QUIZ,
        NavTab.LEARN
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Zinc950,
        border = BorderStroke(1.dp, BorderSubtle)
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .testTag("app_bottom_navigation"),
            containerColor = Zinc950,
            tonalElevation = 0.dp
        ) {
            tabs.forEach { tab ->
                val isSelected = currentTab == tab

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = tab.title,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = Teal400,
                        indicatorColor = Teal400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate500
                    ),
                    modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                )
            }
        }
    }
}

