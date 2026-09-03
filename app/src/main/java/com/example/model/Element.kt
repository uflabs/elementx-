package com.example.model

data class Element(
    val atomicNumber: Int,
    val symbol: String,
    val name: String,
    val atomicMass: Double,
    val group: Int?,
    val period: Int,
    val block: String,
    val category: ElementCategory,
    val state: ElementState,
    val density: String,
    val meltingPoint: String,
    val boilingPoint: String,
    val electronConfiguration: String,
    val electronegativity: Double?,
    val description: String,
    val facts: List<String>,
    val uses: List<String>
) {
    val groupDisplay: String
        get() = group?.toString() ?: "N/A"

    val electronegativityDisplay: String
        get() = electronegativity?.toString() ?: "N/A"

    val atomicMassDisplay: String
        get() = if (atomicMass % 1.0 == 0.0) {
            "(${atomicMass.toInt()}) u"
        } else {
            "$atomicMass u"
        }

    // Extraction helper for numeric comparison of density if available (in g/cm³ or g/L)
    val densityNumeric: Double?
        get() {
            val clean = density.split(" ").firstOrNull() ?: return null
            return clean.toDoubleOrNull()
        }

    // Extraction helper for numeric melting point in Kelvin if available
    val meltingPointKelvin: Double?
        get() {
            val clean = meltingPoint.split(" ").firstOrNull() ?: return null
            return clean.toDoubleOrNull()
        }

    // Extraction helper for numeric boiling point in Kelvin if available
    val boilingPointKelvin: Double?
        get() {
            val clean = boilingPoint.split(" ").firstOrNull() ?: return null
            return clean.toDoubleOrNull()
        }
}
