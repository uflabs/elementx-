package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ElementsRepository
import com.example.model.Element
import com.example.model.ElementCategory
import com.example.model.NavTab
import com.example.ui.components.ElementListItem
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Orange400
import com.example.ui.theme.Pink400
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun HomeScreen(
    elementOfTheDay: Element,
    isElementOfTheDayFavorite: Boolean,
    onElementClick: (Element) -> Unit,
    onFavoriteToggle: (Int) -> Unit,
    onNavigateTab: (NavTab) -> Unit,
    bestQuizScore: Int,
    quizzesCompleted: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950)
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Quick Stats Row (Sophisticated Dark 3-metric bar)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatPill(
                    label = "TOTAL ELEMENTS",
                    value = "118",
                    accentColor = Teal400,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "BOOKMARKED",
                    value = if (isElementOfTheDayFavorite) "Active" else "Offline",
                    accentColor = Pink400,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "QUIZ BEST",
                    value = if (quizzesCompleted > 0) "$bestQuizScore/10" else "Ready",
                    accentColor = Indigo400,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Discovery of the Day Card
        item {
            ElementOfTheDayCard(
                element = elementOfTheDay,
                isFavorite = isElementOfTheDayFavorite,
                onCardClick = { onElementClick(elementOfTheDay) },
                onFavoriteToggle = { onFavoriteToggle(elementOfTheDay.atomicNumber) }
            )
        }

        // Explorer Tools (Interactive shortcuts)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EXPLORER TOOLS",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate400
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickToolCard(
                    title = "Periodic Table",
                    subtitle = "118 Elements",
                    icon = Icons.Default.GridView,
                    accentColor = Teal400,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(NavTab.TABLE) }
                )
                QuickToolCard(
                    title = "Compare",
                    subtitle = "Side by Side",
                    icon = Icons.Default.CompareArrows,
                    accentColor = Indigo400,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(NavTab.COMPARE) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickToolCard(
                    title = "Chemistry Quiz",
                    subtitle = if (quizzesCompleted > 0) "Best: $bestQuizScore/10" else "Test Knowledge",
                    icon = Icons.Default.Quiz,
                    accentColor = Orange400,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(NavTab.QUIZ) }
                )
                QuickToolCard(
                    title = "Learn Chemistry",
                    subtitle = "8 Study Guides",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    accentColor = Emerald400,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(NavTab.LEARN) }
                )
            }
        }

        // Chemical Families / Categories Quick Selector
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ELEMENT FAMILIES",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate400
                )
                Text(
                    text = "View All",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Teal400,
                    modifier = Modifier
                        .clickable { onNavigateTab(NavTab.CATEGORIES) }
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ElementCategory.values()) { category ->
                    CategoryPill(
                        category = category,
                        onClick = { onNavigateTab(NavTab.CATEGORIES) }
                    )
                }
            }
        }

        // Essential Elements for Students
        item {
            Column {
                Text(
                    text = "KEY ELEMENTS TO EXPLORE",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate400
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fundamental elements every chemistry student should know",
                    fontSize = 12.sp,
                    color = Slate500
                )
            }
        }

        // List of Essential Elements
        val keyElements = listOf(
            ElementsRepository.getByNumber(1),  // Hydrogen
            ElementsRepository.getByNumber(6),  // Carbon
            ElementsRepository.getByNumber(8),  // Oxygen
            ElementsRepository.getByNumber(11), // Sodium
            ElementsRepository.getByNumber(26), // Iron
            ElementsRepository.getByNumber(79), // Gold
            ElementsRepository.getByNumber(92)  // Uranium
        ).filterNotNull()

        items(keyElements) { element ->
            ElementListItem(
                element = element,
                isFavorite = false,
                onClick = { onElementClick(element) },
                onFavoriteToggle = { onFavoriteToggle(element.atomicNumber) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatPill(
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Zinc900,
        border = BorderStroke(1.dp, BorderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 8.5.sp,
                letterSpacing = 0.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate400
            )
        }
    }
}

@Composable
fun ElementOfTheDayCard(
    element: Element,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onCardClick)
            .testTag("element_of_the_day_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Zinc900),
        border = BorderStroke(1.dp, BorderMedium)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header: "DISCOVERY OF THE DAY" badge + Favorite
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Teal400.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = Teal400,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DISCOVERY OF THE DAY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teal400,
                        letterSpacing = 1.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Zinc800)
                        .clickable(onClick = onFavoriteToggle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark",
                        tint = if (isFavorite) Pink400 else Slate500,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main element showcase
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large Atomic Symbol badge
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Teal400.copy(alpha = 0.2f),
                                    Indigo400.copy(alpha = 0.08f)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Teal400.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = element.atomicNumber.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate400
                        )
                        Text(
                            text = element.symbol,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate100
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = element.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = element.category.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = element.category.color
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Mass: ${element.atomicMassDisplay} u • ${element.state.displayName}",
                        fontSize = 11.sp,
                        color = Slate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Interesting daily fact callout
            val fact = element.facts.firstOrNull() ?: element.description
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Zinc800)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Orange400,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = fact,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = Slate100,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Explore element profile",
                    fontSize = 11.5.sp,
                    color = Teal400,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Teal400,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun QuickToolCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(82.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("tool_card_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        color = Zinc900,
        border = BorderStroke(1.dp, BorderMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate100
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Slate400
                )
            }
        }
    }
}

@Composable
fun CategoryPill(
    category: ElementCategory,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        color = Zinc900,
        border = BorderStroke(1.dp, BorderSubtle)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(category.color)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = category.displayName,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Medium,
                color = Slate100
            )
        }
    }
}
