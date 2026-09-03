package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavTab
import com.example.ui.ElementXViewModel
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.AppHeader
import com.example.ui.components.ElementDetailModal
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CompareScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.PeriodicTableScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SearchResultsView
import com.example.ui.theme.ElementXTheme
import com.example.ui.theme.Zinc950

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElementXTheme {
                ElementXApp()
            }
        }
    }
}

@Composable
fun ElementXApp(viewModel: ElementXViewModel = viewModel()) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedElement by viewModel.selectedElement.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val categoryFilter by viewModel.categoryFilter.collectAsStateWithLifecycle()
    val stateFilter by viewModel.stateFilter.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val compareElement1 by viewModel.compareElement1.collectAsStateWithLifecycle()
    val compareElement2 by viewModel.compareElement2.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val quizQuestions by viewModel.quizQuestions.collectAsStateWithLifecycle()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val quizScore by viewModel.quizScore.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedAnswerIndex.collectAsStateWithLifecycle()
    val isAnswerSubmitted by viewModel.isAnswerSubmitted.collectAsStateWithLifecycle()
    val isQuizFinished by viewModel.isQuizFinished.collectAsStateWithLifecycle()
    val bestQuizScore by viewModel.bestQuizScore.collectAsStateWithLifecycle()
    val quizzesCompleted by viewModel.quizzesCompleted.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950),
        containerColor = Zinc950,
        topBar = {
            AppHeader(
                searchQuery = searchQuery,
                onSearchChanged = viewModel::onSearchQueryChanged,
                onClearSearch = viewModel::clearSearch,
                favoritesCount = favorites.size,
                onFavoritesClick = {
                    viewModel.clearSearch()
                    viewModel.navigateTo(NavTab.FAVORITES)
                },
                onLogoClick = {
                    viewModel.clearSearch()
                    viewModel.navigateTo(NavTab.HOME)
                }
            )
        },
        bottomBar = {
            AppBottomNavigation(
                currentTab = currentTab,
                onTabSelected = { tab ->
                    viewModel.clearSearch()
                    viewModel.navigateTo(tab)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Zinc950)
        ) {
            if (searchQuery.isNotBlank()) {
                SearchResultsView(
                    results = searchResults,
                    query = searchQuery,
                    favorites = favorites,
                    onElementClick = viewModel::selectElement,
                    onFavoriteToggle = viewModel::toggleFavorite
                )
            } else {
                when (currentTab) {
                    NavTab.HOME -> {
                        HomeScreen(
                            elementOfTheDay = viewModel.elementOfTheDay,
                            isElementOfTheDayFavorite = favorites.contains(viewModel.elementOfTheDay.atomicNumber),
                            onElementClick = viewModel::selectElement,
                            onFavoriteToggle = viewModel::toggleFavorite,
                            onNavigateTab = viewModel::navigateTo,
                            bestQuizScore = bestQuizScore,
                            quizzesCompleted = quizzesCompleted
                        )
                    }

                    NavTab.TABLE -> {
                        PeriodicTableScreen(
                            onElementClick = viewModel::selectElement,
                            categoryFilter = categoryFilter,
                            stateFilter = stateFilter,
                            onCategoryFilterChange = viewModel::setCategoryFilter,
                            onStateFilterChange = viewModel::setStateFilter,
                            onClearFilters = viewModel::clearFilters,
                            favorites = favorites,
                            onFavoriteToggle = viewModel::toggleFavorite
                        )
                    }

                    NavTab.CATEGORIES -> {
                        CategoriesScreen(
                            selectedCategory = selectedCategory,
                            onCategorySelect = viewModel::selectCategory,
                            onElementClick = viewModel::selectElement,
                            favorites = favorites,
                            onFavoriteToggle = viewModel::toggleFavorite
                        )
                    }

                    NavTab.COMPARE -> {
                        CompareScreen(
                            element1 = compareElement1,
                            element2 = compareElement2,
                            onElement1Change = viewModel::setCompareElement1,
                            onElement2Change = viewModel::setCompareElement2,
                            onSwap = viewModel::swapCompareElements,
                            onElementDetailClick = viewModel::selectElement
                        )
                    }

                    NavTab.QUIZ -> {
                        QuizScreen(
                            questions = quizQuestions,
                            currentIndex = currentQuestionIndex,
                            score = quizScore,
                            selectedAnswerIndex = selectedAnswerIndex,
                            isAnswerSubmitted = isAnswerSubmitted,
                            isQuizFinished = isQuizFinished,
                            bestScore = bestQuizScore,
                            onSelectAnswer = viewModel::selectQuizAnswer,
                            onNextQuestion = viewModel::nextQuizQuestion,
                            onRestartQuiz = viewModel::startNewQuiz,
                            onBackToExplorer = { viewModel.navigateTo(NavTab.TABLE) }
                        )
                    }

                    NavTab.FAVORITES -> {
                        FavoritesScreen(
                            favorites = favorites,
                            onElementClick = viewModel::selectElement,
                            onFavoriteToggle = viewModel::toggleFavorite,
                            onExploreClick = { viewModel.navigateTo(NavTab.TABLE) }
                        )
                    }

                    NavTab.LEARN -> {
                        LearnScreen()
                    }
                }
            }
        }

        // Element Detail Modal Sheet
        selectedElement?.let { element ->
            ElementDetailModal(
                element = element,
                isFavorite = favorites.contains(element.atomicNumber),
                onDismiss = { viewModel.selectElement(null) },
                onFavoriteToggle = { viewModel.toggleFavorite(element.atomicNumber) },
                onCompareClick = { viewModel.addToCompare(it) }
            )
        }
    }
}
