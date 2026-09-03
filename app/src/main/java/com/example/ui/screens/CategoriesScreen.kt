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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ElementsRepository
import com.example.model.Element
import com.example.model.ElementCategory
import com.example.ui.components.ElementListItem
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun CategoriesScreen(
    selectedCategory: ElementCategory,
    onCategorySelect: (ElementCategory) -> Unit,
    onElementClick: (Element) -> Unit,
    favorites: Set<Int>,
    onFavoriteToggle: (Int) -> Unit
) {
    val categoryElements = ElementsRepository.allElements.filter { it.category == selectedCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950)
            .testTag("categories_screen")
    ) {
        // Horizontal Category Tabs
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Zinc950,
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ElementCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onCategorySelect(category) }
                            .testTag("category_tab_${category.name.lowercase()}"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) category.color.copy(alpha = 0.22f) else Zinc900,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) category.color else BorderSubtle
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(category.color)
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = category.displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Slate100 else Slate400
                            )
                        }
                    }
                }
            }
        }

        // Category Detail and Elements List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Category Info Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, selectedCategory.color.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(selectedCategory.color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = selectedCategory.displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate100
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "(${categoryElements.size} elements)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = selectedCategory.color
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = selectedCategory.description,
                            fontSize = 13.5.sp,
                            lineHeight = 20.sp,
                            color = Slate400
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Category Key Properties
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Zinc950)
                                .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(10.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = selectedCategory.color,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = getCategoryStudentTip(selectedCategory),
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Slate100
                            )
                        }
                    }
                }
            }

            // Elements Header
            item {
                Text(
                    text = "Elements in this Family",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate100,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            // Elements belonging to category
            items(categoryElements) { element ->
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

private fun getCategoryStudentTip(category: ElementCategory): String {
    return when (category) {
        ElementCategory.ALKALI_METAL -> "High school exam tip: Group 1 metals have 1 valence electron, react violently with water, and are kept under oil."
        ElementCategory.ALKALINE_EARTH -> "Group 2 metals have 2 valence electrons, burn with distinct flame colors (Mg burns bright white, Ba burns apple green)."
        ElementCategory.TRANSITION_METAL -> "Fills d-orbitals. Known for multiple oxidation states, colored compounds, and acting as industrial catalysts."
        ElementCategory.POST_TRANSITION_METAL -> "Softer than transition metals with lower melting points and higher electronegativities."
        ElementCategory.METALLOID -> "Touch the zig-zag border! Have dual metallic/nonmetallic behavior and make silicon microchips possible."
        ElementCategory.OTHER_NONMETAL -> "Vital for biological life (CHONPS). High electronegativity, gain electrons to complete stable octets."
        ElementCategory.HALOGEN -> "Group 17 salt-makers with 7 valence electrons. Only need 1 more electron, making them fiercely reactive oxidizers."
        ElementCategory.NOBLE_GAS -> "Group 18 has completely filled outer electron shells (stable octet). They rarely react with any other element!"
        ElementCategory.LANTHANIDE -> "Rare earth 4f-block metals. Crucial for smartphone screens, permanent magnets, and laser optics."
        ElementCategory.ACTINIDE -> "Heavy 5f-block elements. All isotopes are radioactive; includes uranium, thorium, and synthetic transuranics."
    }
}
