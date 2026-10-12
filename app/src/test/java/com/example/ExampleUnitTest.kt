package com.example

import com.example.data.AlphabetRepository
import com.example.data.RewardRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun alphabetRepository_containsAll26Letters() {
        val items = AlphabetRepository.items
        assertEquals(26, items.size)
        val letters = items.map { it.letter }.toSet()
        assertEquals(26, letters.size)

        for (ch in 'A'..'Z') {
            assertTrue("Alphabet must contain $ch", letters.contains(ch))
            val item = AlphabetRepository.getItem(ch)
            assertNotNull(item)
            assertTrue(item.word.isNotEmpty())
            assertTrue(item.emoji.isNotEmpty())
            assertTrue(item.sentence.isNotEmpty())
        }
    }

    @Test
    fun rewardRepository_levelsCalculateCorrectly() {
        val lvl1 = RewardRepository.getLevel(0)
        assertEquals(1, lvl1.levelNumber)

        val lvl2 = RewardRepository.getLevel(50)
        assertEquals(2, lvl2.levelNumber)

        val lvl3 = RewardRepository.getLevel(100)
        assertEquals(3, lvl3.levelNumber)

        val lvl5 = RewardRepository.getLevel(350)
        assertEquals(5, lvl5.levelNumber)
    }
}
