package com.example.model

import androidx.compose.ui.graphics.Color

enum class ElementCategory(
    val displayName: String,
    val color: Color,
    val lightColor: Color,
    val description: String
) {
    ALKALI_METAL(
        displayName = "Alkali Metals",
        color = Color(0xFFEF4444), // Vibrant Red
        lightColor = Color(0x33EF4444),
        description = "Highly reactive, soft metals with one valence electron that form alkaline hydroxides."
    ),
    ALKALINE_EARTH(
        displayName = "Alkaline Earth Metals",
        color = Color(0xFFF97316), // Orange
        lightColor = Color(0x33F97316),
        description = "Shiny, silvery-white, somewhat reactive metals with two valence electrons."
    ),
    TRANSITION_METAL(
        displayName = "Transition Metals",
        color = Color(0xFFF59E0B), // Amber / Gold
        lightColor = Color(0x33F59E0B),
        description = "Ductile, malleable metals that conduct heat and electricity with partially filled d-orbitals."
    ),
    POST_TRANSITION_METAL(
        displayName = "Post-Transition Metals",
        color = Color(0xFF10B981), // Emerald
        lightColor = Color(0x3310B981),
        description = "Soft metals with higher electronegativity and lower melting points than transition metals."
    ),
    METALLOID(
        displayName = "Metalloids",
        color = Color(0xFF06B6D4), // Cyan
        lightColor = Color(0x3306B6D4),
        description = "Elements with properties intermediate between metals and nonmetals, often semiconductors."
    ),
    OTHER_NONMETAL(
        displayName = "Other Nonmetals",
        color = Color(0xFF3B82F6), // Blue
        lightColor = Color(0x333B82F6),
        description = "Essential elements of organic life that do not conduct electricity and have varied states."
    ),
    HALOGEN(
        displayName = "Halogens",
        color = Color(0xFF8B5CF6), // Purple
        lightColor = Color(0x338B5CF6),
        description = "Extremely reactive nonmetals that form strongly acidic compounds with hydrogen."
    ),
    NOBLE_GAS(
        displayName = "Noble Gases",
        color = Color(0xFFEC4899), // Pink
        lightColor = Color(0x33EC4899),
        description = "Odorless, colorless, extremely stable monatomic gases with full outer valence electron shells."
    ),
    LANTHANIDE(
        displayName = "Lanthanides",
        color = Color(0xFF6366F1), // Indigo
        lightColor = Color(0x336366F1),
        description = "Rare earth metallic elements characterized by filling 4f electron subshells."
    ),
    ACTINIDE(
        displayName = "Actinides",
        color = Color(0xFFD946EF), // Magenta
        lightColor = Color(0x33D946EF),
        description = "Heavy, highly radioactive metallic elements characterized by filling 5f electron subshells."
    )
}
