package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.model.Element
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Orange400
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ElementDetailModal(
    element: Element,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onCompareClick: (Element) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Zinc950,
        tonalElevation = 8.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(BorderSubtle)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .testTag("element_detail_panel")
        ) {
            // Top Header: Category Pill & Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(element.category.color.copy(alpha = 0.15f))
                        .border(1.dp, element.category.color.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = element.category.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = element.category.color
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_detail_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Slate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hero Element Badge & Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large Glowing Element Card
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    element.category.color.copy(alpha = 0.35f),
                                    element.category.color.copy(alpha = 0.1f)
                                )
                            )
                        )
                        .border(
                            1.5.dp,
                            element.category.color.copy(alpha = 0.7f),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = element.atomicNumber.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate400
                        )
                        Text(
                            text = element.symbol,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Slate100
                        )
                    }
                }

                Spacer(modifier = Modifier.width(18.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = element.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Atomic Mass: ${element.atomicMassDisplay}",
                        fontSize = 14.sp,
                        color = Slate400
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "State: ${element.state.displayName} at room temp",
                        fontSize = 13.sp,
                        color = Teal400
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons: Add/Remove Favorite & Compare
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onFavoriteToggle,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("detail_favorite_toggle_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) Orange400 else Zinc900
                    ),
                    border = BorderStroke(1.dp, if (isFavorite) Orange400 else BorderSubtle)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Zinc950 else Orange400,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isFavorite) "Saved in Favorites" else "Add to Favorites",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = if (isFavorite) Zinc950 else Slate100
                    )
                }

                OutlinedButton(
                    onClick = {
                        onCompareClick(element)
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("detail_compare_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Teal400.copy(alpha = 0.7f))
                ) {
                    Icon(
                        imageVector = Icons.Default.CompareArrows,
                        contentDescription = null,
                        tint = Teal400,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Compare",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Teal400
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Student-friendly Description
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Zinc900,
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = null,
                            tint = Teal400,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Overview",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal400
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = element.description,
                        fontSize = 13.5.sp,
                        lineHeight = 19.sp,
                        color = Slate100
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scientific Properties Grid
            Text(
                text = "Scientific Properties",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Zinc900)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PropertyRow(label = "Group", value = element.groupDisplay)
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Period", value = element.period.toString())
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Block", value = "${element.block}-block")
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Electron Config", value = element.electronConfiguration)
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Electronegativity", value = element.electronegativityDisplay)
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Density", value = element.density)
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Melting Point", value = element.meltingPoint)
                HorizontalDivider(color = BorderSubtle)
                PropertyRow(label = "Boiling Point", value = element.boilingPoint)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interesting Facts
            Text(
                text = "Interesting Facts",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                element.facts.forEach { fact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Zinc900)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Orange400,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = fact,
                            fontSize = 12.5.sp,
                            color = Slate100,
                            lineHeight = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Common Uses
            Text(
                text = "Everyday & Industrial Uses",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                element.uses.forEach { use ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Zinc900)
                            .border(1.dp, Teal400.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = use,
                            fontSize = 12.sp,
                            color = Slate100,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Slate400
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate100
        )
    }
}

