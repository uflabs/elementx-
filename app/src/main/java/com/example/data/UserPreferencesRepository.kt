package com.example.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferencesRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("elementx_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FAVORITES = "key_favorite_elements"
        private const val KEY_BEST_QUIZ_SCORE = "key_best_quiz_score"
        private const val KEY_QUIZZES_COMPLETED = "key_quizzes_completed"
        private const val KEY_TOTAL_CORRECT_ANSWERS = "key_total_correct_answers"
    }

    fun getFavorites(): Set<Int> {
        val stringSet = prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return stringSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun toggleFavorite(atomicNumber: Int): Boolean {
        val current = getFavorites().toMutableSet()
        val isNowFavorite = if (current.contains(atomicNumber)) {
            current.remove(atomicNumber)
            false
        } else {
            current.add(atomicNumber)
            true
        }
        prefs.edit()
            .putStringSet(KEY_FAVORITES, current.map { it.toString() }.toSet())
            .apply()
        return isNowFavorite
    }

    fun isFavorite(atomicNumber: Int): Boolean {
        return getFavorites().contains(atomicNumber)
    }

    fun removeFavorite(atomicNumber: Int) {
        val current = getFavorites().toMutableSet()
        if (current.remove(atomicNumber)) {
            prefs.edit()
                .putStringSet(KEY_FAVORITES, current.map { it.toString() }.toSet())
                .apply()
        }
    }

    fun getBestQuizScore(): Int {
        return prefs.getInt(KEY_BEST_QUIZ_SCORE, 0)
    }

    fun recordQuizCompletion(score: Int) {
        val currentBest = getBestQuizScore()
        val totalQuizzes = prefs.getInt(KEY_QUIZZES_COMPLETED, 0) + 1
        val totalCorrect = prefs.getInt(KEY_TOTAL_CORRECT_ANSWERS, 0) + score

        val editor = prefs.edit()
            .putInt(KEY_QUIZZES_COMPLETED, totalQuizzes)
            .putInt(KEY_TOTAL_CORRECT_ANSWERS, totalCorrect)

        if (score > currentBest) {
            editor.putInt(KEY_BEST_QUIZ_SCORE, score)
        }
        editor.apply()
    }

    fun getQuizzesCompleted(): Int {
        return prefs.getInt(KEY_QUIZZES_COMPLETED, 0)
    }
}
