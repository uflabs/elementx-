package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Element
import com.example.model.ElementState
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Pink400
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900

@Composable
fun ElementGridCell(
    element: Element,
    isFavorite: Boolean = false,
    isHighlighted: Boolean = true,
    size: Dp = 62.dp,
    onClick: () -> Unit
) {
    val categoryColor = element.category.color
    val alpha = if (isHighlighted) 1.0f else 0.25f

    Surface(
        modifier = Modifier
            .size(size)
            .padding(1.5.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .testTag("element_cell_${element.symbol.lowercase()}"),
        shape = RoundedCornerShape(8.dp),
        color = if (isHighlighted) Zinc900 else Zinc900.copy(alpha = 0.35f),
        border = BorderStroke(
            1.dp,
            if (isHighlighted) categoryColor.copy(alpha = 0.6f) else BorderSubtle
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Category color accent top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(categoryColor.copy(alpha = alpha))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 3.dp, vertical = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top row: atomic number and state dot
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = element.atomicNumber.toString(),
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate400.copy(alpha = alpha),
                        lineHeight = 9.sp
                    )

                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Favorite",
                            tint = Pink400,
                            modifier = Modifier.size(7.dp)
                        )
                    } else {
                        // State indicator dot
                        val stateColor = when (element.state) {
                            ElementState.GAS -> Teal400
                            ElementState.LIQUID -> Emerald400
                            ElementState.SOLID -> Slate400
                            ElementState.UNKNOWN -> Slate500
                        }
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(stateColor.copy(alpha = alpha))
                        )
                    }
                }

                // Center Symbol
                Text(
                    text = element.symbol,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = if (isHighlighted) Slate100 else Slate400.copy(alpha = 0.3f),
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )

                // Bottom Name
                Text(
                    text = element.name,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate400.copy(alpha = alpha),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ElementListItem(
    element: Element,
    isFavorite: Boolean = false,
    onClick: () -> Unit,
    onFavoriteToggle: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("element_row_${element.atomicNumber}"),
        shape = RoundedCornerShape(14.dp),
        color = Zinc900,
        border = BorderStroke(1.dp, BorderMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Glowing Symbol Badge
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                element.category.color.copy(alpha = 0.25f),
                                element.category.color.copy(alpha = 0.08f)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        element.category.color.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = element.atomicNumber.toString(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate400
                    )
                    Text(
                        text = element.symbol,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Slate100
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = element.name,
                        fontSize = 15.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate100
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Category pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(element.category.color.copy(alpha = 0.15f))
                            .border(1.dp, element.category.color.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = element.category.displayName,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = element.category.color
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${element.atomicMassDisplay} u",
                        fontSize = 11.5.sp,
                        color = Slate400
                    )
                    Text(
                        text = "•",
                        fontSize = 10.sp,
                        color = Slate500
                    )
                    Text(
                        text = element.state.displayName,
                        fontSize = 11.5.sp,
                        color = Slate400
                    )
                    if (element.group != null) {
                        Text(
                            text = "•",
                            fontSize = 10.sp,
                            color = Slate500
                        )
                        Text(
                            text = "Group ${element.group}",
                            fontSize = 11.5.sp,
                            color = Slate400
                        )
                    }
                }
            }

            if (onFavoriteToggle != null) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onFavoriteToggle)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Pink400 else Slate500,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
