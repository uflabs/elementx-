package com.example.data

import com.example.model.Element
import com.example.model.ElementCategory
import com.example.model.ElementState
import com.example.model.QuizQuestion
import com.example.model.QuizQuestionType
import kotlin.random.Random

object QuizGenerator {
    fun generateRound(elements: List<Element> = ElementsRepository.allElements, count: Int = 10): List<QuizQuestion> {
        val types = QuizQuestionType.values().toList()
        val questions = mutableListOf<QuizQuestion>()
        val usedTargetNumbers = mutableSetOf<Int>()

        for (i in 1..count) {
            val type = types[(i - 1) % types.size]
            val question = generateQuestion(i, type, elements, usedTargetNumbers)
            questions.add(question)
        }
        return questions
    }

    private fun generateQuestion(
        id: Int,
        type: QuizQuestionType,
        elements: List<Element>,
        usedTargetNumbers: MutableSet<Int>
    ): QuizQuestion {
        val available = elements.filterNot { it.atomicNumber in usedTargetNumbers }
        val target = if (available.isNotEmpty()) available.random() else elements.random()
        usedTargetNumbers.add(target.atomicNumber)

        return when (type) {
            QuizQuestionType.IDENTIFY_ELEMENT -> {
                // "Which element has atomic number X?"
                val wrongElements = elements.filter { it.atomicNumber != target.atomicNumber }.shuffled().take(3)
                val options = (wrongElements.map { it.name } + target.name).shuffled()
                val correctIndex = options.indexOf(target.name)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = "Which element has atomic number ${target.atomicNumber}?",
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "${target.name} (${target.symbol}) has atomic number ${target.atomicNumber}, indicating it has exactly ${target.atomicNumber} protons in its nucleus."
                )
            }

            QuizQuestionType.IDENTIFY_SYMBOL -> {
                // "What is the chemical symbol for [Element]?"
                val wrongOptions = elements.filter { it.symbol != target.symbol }.shuffled().take(3).map { it.symbol }
                val options = (wrongOptions + target.symbol).shuffled()
                val correctIndex = options.indexOf(target.symbol)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = "What is the chemical symbol for ${target.name}?",
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "The chemical symbol for ${target.name} is ${target.symbol}. Its atomic number is ${target.atomicNumber}."
                )
            }

            QuizQuestionType.IDENTIFY_ATOMIC_NUMBER -> {
                // "What is the atomic number of [Element]?"
                val targetNum = target.atomicNumber
                val wrongNumbers = mutableSetOf<Int>()
                val deltas = listOf(-2, -1, 1, 2, 3, -3, 5, -5, 10, -10).shuffled()
                for (delta in deltas) {
                    val candidate = targetNum + delta
                    if (candidate in 1..118 && candidate != targetNum) {
                        wrongNumbers.add(candidate)
                        if (wrongNumbers.size >= 3) break
                    }
                }
                while (wrongNumbers.size < 3) {
                    val randomNum = Random.nextInt(1, 119)
                    if (randomNum != targetNum) wrongNumbers.add(randomNum)
                }

                val options = (wrongNumbers.map { it.toString() } + targetNum.toString()).shuffled()
                val correctIndex = options.indexOf(targetNum.toString())
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = "What is the atomic number of ${target.name} (${target.symbol})?",
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "${target.name} is element #${target.atomicNumber} on the periodic table, with an atomic mass of ${target.atomicMassDisplay}."
                )
            }

            QuizQuestionType.CATEGORY -> {
                // "Which category does [Element] belong to?"
                val correctCategory = target.category
                val otherCategories = ElementCategory.values().filter { it != correctCategory }.shuffled().take(3)
                val options = (otherCategories.map { it.displayName } + correctCategory.displayName).shuffled()
                val correctIndex = options.indexOf(correctCategory.displayName)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = "Which category does ${target.name} (${target.symbol}) belong to?",
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "${target.name} is classified as a ${correctCategory.displayName}: ${correctCategory.description}"
                )
            }

            QuizQuestionType.STATE -> {
                // "Which of these elements is a [State] at room temperature?"
                val stateToAsk = listOf(ElementState.GAS, ElementState.LIQUID, ElementState.SOLID).random()
                val matchingElements = elements.filter { it.state == stateToAsk }
                val targetMatching = if (matchingElements.isNotEmpty()) matchingElements.random() else target
                val nonMatching = elements.filter { it.state != stateToAsk }.shuffled().take(3)
                val options = (nonMatching.map { "${it.name} (${it.symbol})" } + "${targetMatching.name} (${targetMatching.symbol})").shuffled()
                val correctTargetStr = "${targetMatching.name} (${targetMatching.symbol})"
                val correctIndex = options.indexOf(correctTargetStr)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = "Which of these elements is a ${stateToAsk.displayName.uppercase()} at room temperature?",
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "$correctTargetStr is in the ${stateToAsk.displayName.lowercase()} state at standard temperature and pressure (298 K / 25 °C)."
                )
            }

            QuizQuestionType.RANDOM_CLUES -> {
                // "I'm a [category], my atomic number is X, and my symbol starts with [Letter]. Who am I?"
                val firstLetter = target.symbol.first()
                val wrongElements = elements.filter { it.atomicNumber != target.atomicNumber }.shuffled().take(3)
                val options = (wrongElements.map { "${it.name} (${it.symbol})" } + "${target.name} (${target.symbol})").shuffled()
                val correctTargetStr = "${target.name} (${target.symbol})"
                val correctIndex = options.indexOf(correctTargetStr)
                val clue = "I belong to the ${target.category.displayName.lowercase()}, my atomic number is ${target.atomicNumber}, and my chemical symbol starts with '$firstLetter'. Who am I?"
                QuizQuestion(
                    id = id,
                    type = type,
                    questionText = clue,
                    options = options,
                    correctIndex = correctIndex,
                    explanation = "You found it! ${target.name} (${target.symbol}) has atomic number ${target.atomicNumber} and is a ${target.category.displayName}."
                )
            }
        }
    }
}
