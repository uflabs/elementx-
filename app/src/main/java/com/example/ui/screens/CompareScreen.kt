package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ElementsRepository
import com.example.model.Element
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
    element1: Element?,
    element2: Element?,
    onElement1Change: (Element) -> Unit,
    onElement2Change: (Element) -> Unit,
    onSwap: () -> Unit,
    onElementDetailClick: (Element) -> Unit
) {
    var pickingForSlot by remember { mutableStateOf<Int?>(null) }
    var pickerSearchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950)
            .verticalScroll(rememberScrollState())
            .padding(14.dp)
            .testTag("compare_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Element Comparison",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate100
                )
                Text(
                    text = "Compare physical & chemical properties side by side",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            IconButton(
                onClick = onSwap,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Zinc900)
                    .border(BorderStroke(1.dp, BorderSubtle), CircleShape)
                    .testTag("swap_compare_button")
            ) {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Swap elements",
                    tint = Teal400,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Element Selectors
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Element 1 Selector Box
            ElementPickerBox(
                element = element1,
                label = "Element 1",
                accentColor = Teal400,
                modifier = Modifier.weight(1f),
                onClick = {
                    pickerSearchQuery = ""
                    pickingForSlot = 1
                }
            )

            // Element 2 Selector Box
            ElementPickerBox(
                element = element2,
                label = "Element 2",
                accentColor = Indigo400,
                modifier = Modifier.weight(1f),
                onClick = {
                    pickerSearchQuery = ""
                    pickingForSlot = 2
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (element1 != null && element2 != null) {
            // Side-by-side Properties Comparison Table
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Properties Breakdown",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    CompareRow(
                        label = "Atomic #",
                        val1 = element1.atomicNumber.toString(),
                        val2 = element2.atomicNumber.toString()
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Atomic Mass",
                        val1 = element1.atomicMassDisplay,
                        val2 = element2.atomicMassDisplay
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Category",
                        val1 = element1.category.displayName,
                        val2 = element2.category.displayName,
                        color1 = element1.category.color,
                        color2 = element2.category.color
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "State",
                        val1 = element1.state.displayName,
                        val2 = element2.state.displayName
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Period / Group",
                        val1 = "P${element1.period} / ${element1.groupDisplay}",
                        val2 = "P${element2.period} / ${element2.groupDisplay}"
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Block",
                        val1 = "${element1.block}-block",
                        val2 = "${element2.block}-block"
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Electronegativity",
                        val1 = element1.electronegativityDisplay,
                        val2 = element2.electronegativityDisplay
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Density",
                        val1 = element1.density,
                        val2 = element2.density
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Melting Point",
                        val1 = element1.meltingPoint,
                        val2 = element2.meltingPoint
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Boiling Point",
                        val1 = element1.boilingPoint,
                        val2 = element2.boilingPoint
                    )
                    HorizontalDivider(color = BorderSubtle)

                    CompareRow(
                        label = "Electron Config",
                        val1 = element1.electronConfiguration,
                        val2 = element2.electronConfiguration
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary Uses Comparison
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Practical Uses",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = element1.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Teal400
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            element1.uses.take(3).forEach { use ->
                                Text(
                                    text = "• $use",
                                    fontSize = 11.5.sp,
                                    lineHeight = 16.sp,
                                    color = Slate400,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = element2.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Indigo400
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            element2.uses.take(3).forEach { use ->
                                Text(
                                    text = "• $use",
                                    fontSize = 11.5.sp,
                                    lineHeight = 16.sp,
                                    color = Slate400,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Element Selector Bottom Sheet Modal
    if (pickingForSlot != null) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val filteredList = ElementsRepository.search(pickerSearchQuery)

        ModalBottomSheet(
            onDismissRequest = { pickingForSlot = null },
            sheetState = sheetState,
            containerColor = Zinc950
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Element for Slot $pickingForSlot",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    IconButton(onClick = { pickingForSlot = null }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate400)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = pickerSearchQuery,
                    onValueChange = { pickerSearchQuery = it },
                    placeholder = { Text("Search by name, symbol, or number...", color = Slate500) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Teal400) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Zinc900,
                        unfocusedContainerColor = Zinc900,
                        focusedBorderColor = Teal400,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = Slate100,
                        unfocusedTextColor = Slate100
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(filteredList) { element ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    if (pickingForSlot == 1) {
                                        onElement1Change(element)
                                    } else {
                                        onElement2Change(element)
                                    }
                                    pickingForSlot = null
                                },
                            color = Zinc900,
                            border = BorderStroke(1.dp, BorderSubtle)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(element.category.color.copy(alpha = 0.22f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = element.symbol,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate100
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "${element.atomicNumber}. ${element.name}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Slate100
                                    )
                                    Text(
                                        text = "${element.category.displayName} • Mass: ${element.atomicMassDisplay}",
                                        fontSize = 11.5.sp,
                                        color = Slate400
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ElementPickerBox(
    element: Element?,
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("picker_box_${label.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        color = Zinc900,
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (element != null) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(element.category.color.copy(alpha = 0.25f))
                        .border(1.dp, element.category.color, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = element.symbol,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate100
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = element.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate100
                )
                Text(
                    text = "#${element.atomicNumber} • ${element.atomicMassDisplay}",
                    fontSize = 11.sp,
                    color = Slate400
                )
            } else {
                Text(
                    text = "Tap to choose",
                    fontSize = 13.sp,
                    color = Slate400
                )
            }
        }
    }
}

@Composable
private fun CompareRow(
    label: String,
    val1: String,
    val2: String,
    color1: Color = Slate100,
    color2: Color = Slate100
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Slate500
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = val1,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = color1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = val2,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = color2,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
