package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG: String = "GameViewModel"

class GameViewModel : ViewModel() {

    init {
        Log.i(TAG, "GameViewModel created!")
        getNextWord()
    }

    private var _score = 0
    val score: Int
        get() = _score
    private var _currentWordCount: Int = 0
    val currentWordCount: Int
        get() = _currentWordCount
    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "GameViewModel destroyed!")
    }

    /**
     * Gets a random word for the list of words and shuffles the letters in it.
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        while (wordsList.contains(currentWord)) currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        while (String(tempWord).equals(currentWord, false)) tempWord.shuffle()

        _currentScrambledWord = String(tempWord)
        ++_currentWordCount
        wordsList.add(currentWord)
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        return if (playerWord.equals(currentWord, false)) {
            nextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score = 0
        wordsList.clear()
        _currentWordCount = 0
        getNextWord()
    }
}