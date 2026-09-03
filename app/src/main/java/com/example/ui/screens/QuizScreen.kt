package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavTab
import com.example.model.QuizQuestion
import com.example.ui.theme.BorderMedium
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Orange400
import com.example.ui.theme.Rose500
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Teal400
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun QuizScreen(
    questions: List<QuizQuestion>,
    currentIndex: Int,
    score: Int,
    selectedAnswerIndex: Int?,
    isAnswerSubmitted: Boolean,
    isQuizFinished: Boolean,
    bestScore: Int,
    onSelectAnswer: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onBackToExplorer: () -> Unit
) {
    if (isQuizFinished || questions.isEmpty()) {
        QuizResultsView(
            score = score,
            totalQuestions = questions.size.coerceAtLeast(10),
            bestScore = bestScore,
            onTryAgain = onRestartQuiz,
            onBackToExplorer = onBackToExplorer
        )
    } else {
        val currentQuestion = questions.getOrNull(currentIndex) ?: return

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Zinc950)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .testTag("quiz_screen")
        ) {
            // Header: Question X of 10 + Current Score & Best Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Question ${currentIndex + 1} of ${questions.size}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate100
                    )
                    Text(
                        text = currentQuestion.type.label,
                        fontSize = 12.sp,
                        color = Teal400
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Zinc900,
                        border = BorderStroke(1.dp, BorderSubtle)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = null,
                                tint = Orange400,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Score: $score",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate100
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar
            val progress = (currentIndex.toFloat()) / questions.size.toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Teal400,
                trackColor = Zinc800
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Question Prompt Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Teal400.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = Teal400,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate100,
                        textAlign = TextAlign.Center,
                        lineHeight = 23.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4 Answer Option Buttons
            currentQuestion.options.forEachIndexed { index, optionText ->
                val isSelected = selectedAnswerIndex == index
                val isCorrect = index == currentQuestion.correctIndex

                val optionColor = when {
                    !isAnswerSubmitted -> Zinc900
                    isCorrect -> Emerald500.copy(alpha = 0.2f)
                    isSelected && !isCorrect -> Rose500.copy(alpha = 0.2f)
                    else -> Zinc900.copy(alpha = 0.6f)
                }

                val borderColor = when {
                    !isAnswerSubmitted && isSelected -> Teal400
                    !isAnswerSubmitted -> BorderSubtle
                    isCorrect -> Emerald500
                    isSelected && !isCorrect -> Rose500
                    else -> BorderSubtle
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !isAnswerSubmitted) {
                            onSelectAnswer(index)
                        }
                        .testTag("quiz_option_$index"),
                    shape = RoundedCornerShape(12.dp),
                    color = optionColor,
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Letter pill (A, B, C, D)
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Zinc950),
                                contentAlignment = Alignment.Center
                            ) {
                                val letter = ('A'.code + index).toChar().toString()
                                Text(
                                    text = letter,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isAnswerSubmitted && isCorrect) Emerald500 else Slate400
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = optionText,
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate100
                            )
                        }

                        if (isAnswerSubmitted) {
                            if (isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Correct",
                                    tint = Emerald500,
                                    modifier = Modifier.size(22.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Incorrect",
                                    tint = Rose500,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Explanation & Next Button if answered
            if (isAnswerSubmitted) {
                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = Orange400,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Chemistry Explanation",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange400
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentQuestion.explanation,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = Slate100
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onNextQuestion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("quiz_next_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal400)
                ) {
                    Text(
                        text = if (currentIndex < questions.size - 1) "Next Question" else "View Final Results",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Zinc950
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun QuizResultsView(
    score: Int,
    totalQuestions: Int,
    bestScore: Int,
    onTryAgain: () -> Unit,
    onBackToExplorer: () -> Unit
) {
    val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

    val (title, feedbackMessage, trophyColor) = when {
        percentage >= 90 -> Triple("Outstanding Chemist!", "You have an exceptional grasp of the periodic table and chemical elements.", Orange400)
        percentage >= 70 -> Triple("Great Work!", "Solid chemistry knowledge! Review a few more element properties to reach master status.", Teal400)
        percentage >= 50 -> Triple("Good Effort!", "You know your basics well. Explore the element cards and learn mode to level up!", Teal400)
        else -> Triple("Keep Practicing!", "Chemistry takes time to master. Check out the 8 student topics in Learn mode and try again!", Slate400)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .testTag("quiz_results_view"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(trophyColor.copy(alpha = 0.15f))
                .border(2.dp, trophyColor.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = trophyColor,
                modifier = Modifier.size(46.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Slate100
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = feedbackMessage,
            fontSize = 13.5.sp,
            color = Slate400,
            textAlign = TextAlign.Center,
            lineHeight = 19.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$score / $totalQuestions",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Teal400
                    )
                    Text(
                        text = "Score ($percentage%)",
                        fontSize = 12.sp,
                        color = Slate400
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(BorderSubtle)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$bestScore / 10",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Orange400
                    )
                    Text(
                        text = "Personal Best",
                        fontSize = 12.sp,
                        color = Slate400
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons: Try Again & Back to Explorer
        Button(
            onClick = onTryAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("quiz_try_again_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Teal400)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Zinc950,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Try Another 10 Questions",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Zinc950
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBackToExplorer,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("quiz_back_to_explorer_button"),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            Text(
                text = "Back to Table Explorer",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Slate100
            )
        }
    }
}

