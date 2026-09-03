package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.ElementsRepository
import com.example.data.QuizGenerator
import com.example.data.UserPreferencesRepository
import com.example.data.learnTopics
import com.example.model.Element
import com.example.model.ElementCategory
import com.example.model.ElementState
import com.example.model.LearnTopic
import com.example.model.NavTab
import com.example.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ElementXViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesRepository = UserPreferencesRepository(application)

    // Navigation
    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    // Element selection for Detail Modal / Sheet
    private val _selectedElement = MutableStateFlow<Element?>(null)
    val selectedElement: StateFlow<Element?> = _selectedElement.asStateFlow()

    // Search query & results
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Element>>(ElementsRepository.allElements)
    val searchResults: StateFlow<List<Element>> = _searchResults.asStateFlow()

    // Periodic Table Filters
    private val _categoryFilter = MutableStateFlow<ElementCategory?>(null)
    val categoryFilter: StateFlow<ElementCategory?> = _categoryFilter.asStateFlow()

    private val _stateFilter = MutableStateFlow<ElementState?>(null)
    val stateFilter: StateFlow<ElementState?> = _stateFilter.asStateFlow()

    // Filtered elements for Periodic Table
    private val _tableElements = MutableStateFlow<List<Element>>(ElementsRepository.allElements)
    val tableElements: StateFlow<List<Element>> = _tableElements.asStateFlow()

    // Favorites
    private val _favorites = MutableStateFlow<Set<Int>>(preferencesRepository.getFavorites())
    val favorites: StateFlow<Set<Int>> = _favorites.asStateFlow()

    // Deterministic Element of the Day
    val elementOfTheDay: Element = ElementsRepository.getElementOfTheDay()

    // Comparison Mode
    private val _compareElement1 = MutableStateFlow<Element?>(ElementsRepository.getByNumber(1)) // Hydrogen default
    val compareElement1: StateFlow<Element?> = _compareElement1.asStateFlow()

    private val _compareElement2 = MutableStateFlow<Element?>(ElementsRepository.getByNumber(8)) // Oxygen default
    val compareElement2: StateFlow<Element?> = _compareElement2.asStateFlow()

    // Categories Screen
    private val _selectedCategory = MutableStateFlow<ElementCategory>(ElementCategory.ALKALI_METAL)
    val selectedCategory: StateFlow<ElementCategory> = _selectedCategory.asStateFlow()

    // Learn Screen
    private val _selectedLearnTopic = MutableStateFlow<LearnTopic?>(learnTopics.firstOrNull())
    val selectedLearnTopic: StateFlow<LearnTopic?> = _selectedLearnTopic.asStateFlow()

    // Quiz State
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    private val _bestQuizScore = MutableStateFlow(preferencesRepository.getBestQuizScore())
    val bestQuizScore: StateFlow<Int> = _bestQuizScore.asStateFlow()

    private val _quizzesCompleted = MutableStateFlow(preferencesRepository.getQuizzesCompleted())
    val quizzesCompleted: StateFlow<Int> = _quizzesCompleted.asStateFlow()

    init {
        startNewQuiz()
        updateTableElements()
    }

    // Navigation functions
    fun navigateTo(tab: NavTab) {
        _currentTab.value = tab
    }

    fun selectElement(element: Element?) {
        _selectedElement.value = element
    }

    // Search functions
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _searchResults.value = ElementsRepository.search(query)
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = ElementsRepository.allElements
    }

    // Filter functions
    fun setCategoryFilter(category: ElementCategory?) {
        _categoryFilter.value = if (_categoryFilter.value == category) null else category
        updateTableElements()
    }

    fun setStateFilter(state: ElementState?) {
        _stateFilter.value = if (_stateFilter.value == state) null else state
        updateTableElements()
    }

    fun clearFilters() {
        _categoryFilter.value = null
        _stateFilter.value = null
        updateTableElements()
    }

    private fun updateTableElements() {
        _tableElements.value = ElementsRepository.filter(
            query = "",
            category = _categoryFilter.value,
            state = _stateFilter.value
        )
    }

    // Favorites
    fun toggleFavorite(atomicNumber: Int) {
        preferencesRepository.toggleFavorite(atomicNumber)
        _favorites.value = preferencesRepository.getFavorites()
    }

    fun isFavorite(atomicNumber: Int): Boolean {
        return _favorites.value.contains(atomicNumber)
    }

    fun removeFavorite(atomicNumber: Int) {
        preferencesRepository.removeFavorite(atomicNumber)
        _favorites.value = preferencesRepository.getFavorites()
    }

    // Comparison functions
    fun setCompareElement1(element: Element) {
        _compareElement1.value = element
    }

    fun setCompareElement2(element: Element) {
        _compareElement2.value = element
    }

    fun swapCompareElements() {
        val temp = _compareElement1.value
        _compareElement1.value = _compareElement2.value
        _compareElement2.value = temp
    }

    fun addToCompare(element: Element) {
        // If element 1 is empty or equals element, set element 1; else set element 2
        if (_compareElement1.value == null || _compareElement1.value?.atomicNumber == element.atomicNumber) {
            _compareElement1.value = element
        } else {
            _compareElement2.value = element
        }
        _currentTab.value = NavTab.COMPARE
    }

    // Category Screen
    fun selectCategory(category: ElementCategory) {
        _selectedCategory.value = category
    }

    // Learn Screen
    fun selectLearnTopic(topic: LearnTopic) {
        _selectedLearnTopic.value = topic
    }

    // Quiz Functions
    fun startNewQuiz() {
        _quizQuestions.value = QuizGenerator.generateRound(count = 10)
        _currentQuestionIndex.value = 0
        _quizScore.value = 0
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
        _isQuizFinished.value = false
    }

    fun selectQuizAnswer(index: Int) {
        if (_isAnswerSubmitted.value) return
        _selectedAnswerIndex.value = index
        _isAnswerSubmitted.value = true

        val currentQ = _quizQuestions.value.getOrNull(_currentQuestionIndex.value)
        if (currentQ != null && index == currentQ.correctIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        if (_currentQuestionIndex.value < _quizQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
            _selectedAnswerIndex.value = null
            _isAnswerSubmitted.value = false
        } else {
            // Finished
            _isQuizFinished.value = true
            val finalScore = _quizScore.value
            preferencesRepository.recordQuizCompletion(finalScore)
            _bestQuizScore.value = preferencesRepository.getBestQuizScore()
            _quizzesCompleted.value = preferencesRepository.getQuizzesCompleted()
        }
    }
}
