package fr.acinq.eklair.utils

import kotlin.test.Test
import kotlin.test.assertTrue

class ByteArraysCommon {

    @Test
    fun `Left pad`() {
        assertTrue(byteArrayOf(0, 0, 0, 1, 2).contentEquals(byteArrayOf(1, 2).leftPaddedCopyOf(5)))
        assertTrue(byteArrayOf(1, 2, 3, 4, 5).contentEquals(byteArrayOf(1, 2, 3, 4, 5).leftPaddedCopyOf(3)))
    }
}