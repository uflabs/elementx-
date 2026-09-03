package com.example

import com.example.data.ElementsRepository
import com.example.data.QuizGenerator
import com.example.data.learnTopics
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun periodicTable_containsAll118Elements() {
        val elements = ElementsRepository.allElements
        assertEquals(118, elements.size)

        val atomicNumbers = elements.map { it.atomicNumber }
        assertEquals((1..118).toList(), atomicNumbers)
    }

    @Test
    fun elements_haveValidRequiredProperties() {
        for (element in ElementsRepository.allElements) {
            assertTrue("Name must not be blank for #${element.atomicNumber}", element.name.isNotBlank())
            assertTrue("Symbol must not be blank for #${element.atomicNumber}", element.symbol.isNotBlank())
            assertNotNull("Category must not be null for #${element.atomicNumber}", element.category)
            assertNotNull("State must not be null for #${element.atomicNumber}", element.state)
            assertTrue("Description must not be blank for #${element.atomicNumber}", element.description.isNotBlank())
            assertTrue("Facts must not be empty for #${element.atomicNumber}", element.facts.isNotEmpty())
            assertTrue("Uses must not be empty for #${element.atomicNumber}", element.uses.isNotEmpty())
        }
    }

    @Test
    fun quizGenerator_generatesTenValidQuestions() {
        val questions = QuizGenerator.generateRound(count = 10)
        assertEquals(10, questions.size)

        for (q in questions) {
            assertEquals(4, q.options.size)
            assertTrue(q.correctIndex in 0..3)
            assertTrue(q.questionText.isNotBlank())
            assertTrue(q.explanation.isNotBlank())
        }
    }

    @Test
    fun learnTopics_containsEightFoundationalGuides() {
        assertEquals(8, learnTopics.size)
        for (topic in learnTopics) {
            assertTrue(topic.title.isNotBlank())
            assertTrue(topic.content.isNotBlank())
            assertTrue(topic.keyTakeaways.isNotEmpty())
            assertTrue(topic.studentTip.isNotBlank())
        }
    }
}
