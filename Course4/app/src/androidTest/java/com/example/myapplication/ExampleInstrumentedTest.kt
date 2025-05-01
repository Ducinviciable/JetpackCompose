package com.example.myapplication

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)
    }
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addition_displaysCorrectResult() {
        composeTestRule.setContent {
            CalculatorScreen()
        }

        // Nhập số a và b
        composeTestRule.onNodeWithText("Enter A").performTextInput("2")
        composeTestRule.onNodeWithText("Enter B").performTextInput("3")

        // Nhấn nút "+"
        composeTestRule.onNodeWithText("+").performClick()

        // Kiểm tra kết quả
        composeTestRule.onNodeWithText("Result").assertExists()
        composeTestRule.onNode(hasText("5")).assertExists()
    }
}