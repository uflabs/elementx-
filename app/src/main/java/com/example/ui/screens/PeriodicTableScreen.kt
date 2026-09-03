package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ElementsRepository
import com.example.model.Element
import com.example.model.ElementCategory
import com.example.model.ElementState
import com.example.ui.components.ElementGridCell
import com.example.ui.components.ElementListItem
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PeriodicTableScreen(
    onElementClick: (Element) -> Unit,
    categoryFilter: ElementCategory?,
    stateFilter: ElementState?,
    onCategoryFilterChange: (ElementCategory?) -> Unit,
    onStateFilterChange: (ElementState?) -> Unit,
    onClearFilters: () -> Unit,
    favorites: Set<Int>,
    onFavoriteToggle: (Int) -> Unit
) {
    var isGridView by remember { mutableStateOf(true) }
    var showLegend by remember { mutableStateOf(false) }

    val cellSize: Dp = 60.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950)
            .testTag("periodic_table_screen")
    ) {
        // Control Bar: Filters, View Toggle, Legend
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Zinc900,
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                // Top row: View Switcher and Active Filters count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // State chips (All, Solid, Liquid, Gas)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StateFilterChip(
                            label = "All",
                            isSelected = stateFilter == null,
                            onClick = { onStateFilterChange(null) }
                        )
                        StateFilterChip(
                            label = "Solid",
                            isSelected = stateFilter == ElementState.SOLID,
                            onClick = { onStateFilterChange(if (stateFilter == ElementState.SOLID) null else ElementState.SOLID) }
                        )
                        StateFilterChip(
                            label = "Liquid",
                            isSelected = stateFilter == ElementState.LIQUID,
                            onClick = { onStateFilterChange(if (stateFilter == ElementState.LIQUID) null else ElementState.LIQUID) }
                        )
                        StateFilterChip(
                            label = "Gas",
                            isSelected = stateFilter == ElementState.GAS,
                            onClick = { onStateFilterChange(if (stateFilter == ElementState.GAS) null else ElementState.GAS) }
                        )
                    }

                    // Grid vs List Toggle
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { isGridView = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = "Grid View",
                                tint = if (isGridView) Teal400 else Slate400,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            onClick = { isGridView = false },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewList,
                                contentDescription = "List View",
                                tint = if (!isGridView) Teal400 else Slate400,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Category Filter Horizontal Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (categoryFilter != null || stateFilter != null) {
                        item {
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(onClick = onClearFilters),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFEF4444).copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Clear",
                                        fontSize = 11.sp,
                                        color = Color(0xFFEF4444),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    items(ElementCategory.values()) { category ->
                        val isSelected = categoryFilter == category
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onCategoryFilterChange(if (isSelected) null else category)
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) category.color.copy(alpha = 0.25f) else Zinc800,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) category.color else BorderSubtle
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(category.color)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = category.displayName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Slate100 else Slate400
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isGridView) {
            // Periodic Table Grid View (Horizontal + Vertical Scroll)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Zinc950)
            ) {
                val horizontalScroll = rememberScrollState()
                val verticalScroll = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(horizontalScroll)
                        .verticalScroll(verticalScroll)
                        .padding(12.dp)
                ) {
                    // Group Headers (1 to 18)
                    Row(modifier = Modifier.padding(start = 24.dp)) {
                        for (group in 1..18) {
                            Box(
                                modifier = Modifier.size(width = cellSize, height = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = group.toString(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate500
                                )
                            }
                        }
                    }

                    // Main 7 Periods
                    for (period in 1..7) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Period label (1 to 7)
                            Box(
                                modifier = Modifier.size(width = 24.dp, height = cellSize),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = period.toString(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate500
                                )
                            }

                            // 18 Group columns
                            for (group in 1..18) {
                                val element = getElementAt(period, group)

                                if (element != null) {
                                    val matchesFilter = elementMatchesFilter(element, categoryFilter, stateFilter)
                                    ElementGridCell(
                                        element = element,
                                        isFavorite = favorites.contains(element.atomicNumber),
                                        isHighlighted = matchesFilter,
                                        size = cellSize,
                                        onClick = { onElementClick(element) }
                                    )
                                } else if (period == 6 && group == 3) {
                                    // Lanthanide placeholder
                                    SeriesPlaceholderCell(
                                        label = "57–71",
                                        name = "La–Lu",
                                        color = ElementCategory.LANTHANIDE.color,
                                        size = cellSize
                                    )
                                } else if (period == 7 && group == 3) {
                                    // Actinide placeholder
                                    SeriesPlaceholderCell(
                                        label = "89–103",
                                        name = "Ac–Lr",
                                        color = ElementCategory.ACTINIDE.color,
                                        size = cellSize
                                    )
                                } else {
                                    // Empty gap cell in periodic table
                                    Spacer(modifier = Modifier.size(cellSize))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Separator for Lanthanides & Actinides
                    Row(
                        modifier = Modifier.padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(cellSize * 2)
                                .height(1.dp)
                                .background(BorderMedium)
                        )
                        Text(
                            text = "Inner Transition Metals (f-block)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Slate400,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(cellSize * 10)
                                .height(1.dp)
                                .background(BorderMedium)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Lanthanide Row (57-71)
                    Row(
                        modifier = Modifier.padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Spacers to align under group 3-17
                        Spacer(modifier = Modifier.size(width = cellSize * 2, height = cellSize))

                        for (atomicNum in 57..71) {
                            val element = ElementsRepository.getByNumber(atomicNum)
                            if (element != null) {
                                val matchesFilter = elementMatchesFilter(element, categoryFilter, stateFilter)
                                ElementGridCell(
                                    element = element,
                                    isFavorite = favorites.contains(element.atomicNumber),
                                    isHighlighted = matchesFilter,
                                    size = cellSize,
                                    onClick = { onElementClick(element) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Actinide Row (89-103)
                    Row(
                        modifier = Modifier.padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Spacers to align under group 3-17
                        Spacer(modifier = Modifier.size(width = cellSize * 2, height = cellSize))

                        for (atomicNum in 89..103) {
                            val element = ElementsRepository.getByNumber(atomicNum)
                            if (element != null) {
                                val matchesFilter = elementMatchesFilter(element, categoryFilter, stateFilter)
                                ElementGridCell(
                                    element = element,
                                    isFavorite = favorites.contains(element.atomicNumber),
                                    isHighlighted = matchesFilter,
                                    size = cellSize,
                                    onClick = { onElementClick(element) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        } else {
            // List View for fast scrolling
            val allList = ElementsRepository.filter("", categoryFilter, stateFilter)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Zinc950)
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                item {
                    Text(
                        text = "Showing ${allList.size} elements",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate400,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
                items(allList) { element ->
                    ElementListItem(
                        element = element,
                        isFavorite = favorites.contains(element.atomicNumber),
                        onClick = { onElementClick(element) },
                        onFavoriteToggle = { onFavoriteToggle(element.atomicNumber) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SeriesPlaceholderCell(
    label: String,
    name: String,
    color: Color,
    size: Dp
) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(1.5.dp),
        shape = RoundedCornerShape(8.dp),
        color = Zinc900.copy(alpha = 0.6f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = name,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = Slate400
            )
        }
    }
}

@Composable
private fun StateFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Teal400 else Zinc800,
        border = BorderStroke(1.dp, if (isSelected) Teal400 else BorderSubtle)
    ) {
        Text(
            text = label,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.Black else Slate100,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

private fun elementMatchesFilter(
    element: Element,
    categoryFilter: ElementCategory?,
    stateFilter: ElementState?
): Boolean {
    val matchesCategory = categoryFilter == null || element.category == categoryFilter
    val matchesState = stateFilter == null || element.state == stateFilter
    return matchesCategory && matchesState
}

// Maps standard (period, group) to corresponding element, leaving gap for Lanthanides/Actinides
private fun getElementAt(period: Int, group: Int): Element? {
    return when {
        period == 1 && group == 1 -> ElementsRepository.getByNumber(1)  // H
        period == 1 && group == 18 -> ElementsRepository.getByNumber(2) // He
        period == 2 && group == 1 -> ElementsRepository.getByNumber(3)  // Li
        period == 2 && group == 2 -> ElementsRepository.getByNumber(4)  // Be
        period == 2 && group in 13..18 -> ElementsRepository.getByNumber(5 + (group - 13)) // B..Ne
        period == 3 && group == 1 -> ElementsRepository.getByNumber(11) // Na
        period == 3 && group == 2 -> ElementsRepository.getByNumber(12) // Mg
        period == 3 && group in 13..18 -> ElementsRepository.getByNumber(13 + (group - 13)) // Al..Ar
        period == 4 && group in 1..18 -> ElementsRepository.getByNumber(19 + (group - 1)) // K..Kr
        period == 5 && group in 1..18 -> ElementsRepository.getByNumber(37 + (group - 1)) // Rb..Xe
        period == 6 && group == 1 -> ElementsRepository.getByNumber(55) // Cs
        period == 6 && group == 2 -> ElementsRepository.getByNumber(56) // Ba
        period == 6 && group in 4..18 -> ElementsRepository.getByNumber(72 + (group - 4)) // Hf..Rn
        period == 7 && group == 1 -> ElementsRepository.getByNumber(87) // Fr
        period == 7 && group == 2 -> ElementsRepository.getByNumber(88) // Ra
        period == 7 && group in 4..18 -> ElementsRepository.getByNumber(104 + (group - 4)) // Rf..Og
        else -> null
    }
}
