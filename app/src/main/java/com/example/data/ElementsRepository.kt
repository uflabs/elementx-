package com.example.data

import com.example.model.Element
import com.example.model.ElementCategory
import com.example.model.ElementState
import java.util.Calendar

object ElementsRepository {
    val allElements: List<Element> by lazy {
        elementsPart1 + elementsPart2 + elementsPart3
    }

    private val elementsByNumber: Map<Int, Element> by lazy {
        allElements.associateBy { it.atomicNumber }
    }

    private val elementsBySymbol: Map<String, Element> by lazy {
        allElements.associateBy { it.symbol.lowercase() }
    }

    fun getByNumber(atomicNumber: Int): Element? {
        return elementsByNumber[atomicNumber]
    }

    fun getBySymbol(symbol: String): Element? {
        return elementsBySymbol[symbol.lowercase()]
    }

    fun search(query: String): List<Element> {
        val trimmed = query.trim().lowercase()
        if (trimmed.isEmpty()) return allElements

        val numberQuery = trimmed.toIntOrNull()
        if (numberQuery != null) {
            val match = getByNumber(numberQuery)
            if (match != null) {
                return listOf(match) + allElements.filter { it.atomicNumber != numberQuery && it.name.lowercase().contains(trimmed) }
            }
        }

        return allElements.filter { element ->
            element.symbol.lowercase() == trimmed ||
                element.name.lowercase().startsWith(trimmed) ||
                element.name.lowercase().contains(trimmed) ||
                element.symbol.lowercase().contains(trimmed) ||
                element.category.displayName.lowercase().contains(trimmed)
        }
    }

    fun filter(
        query: String = "",
        category: ElementCategory? = null,
        state: ElementState? = null
    ): List<Element> {
        var list = if (query.isNotBlank()) search(query) else allElements

        if (category != null) {
            list = list.filter { it.category == category }
        }

        if (state != null && state != ElementState.UNKNOWN) {
            list = list.filter { it.state == state }
        }

        return list
    }

    fun getElementOfTheDay(): Element {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        // Deterministic pseudo-random distribution based on current date
        val index = (Math.abs(year * 367 + dayOfYear * 17) % allElements.size)
        return allElements[index]
    }
}
