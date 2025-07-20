package com.abnamro.apps.referenceandroid.e2e

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.abnamro.apps.referenceandroid.MainActivity
import com.abnamro.apps.referenceandroid.R
import com.abnamro.apps.referenceandroid.e2e.screen.CommentScreen
import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen
import org.junit.Rule
import org.junit.Test

class KaspressoTest :
    TestCase(kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport().addComposeSupport()) {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validateCommentLoaded() = run {
        ComposeScreen.Companion.onComposeScreen<CommentScreen>(composeTestRule) {
            step("Press load comment button") {
                reloadButton {
                    assertIsDisplayed()
                    performClick()
                }
            }
            flakySafely(10000) {
                step("Validate snackbar displayed") {
                    snackBar {
                        hasText(composeTestRule.activity.getString(R.string.snackbar_text))
                        assertIsDisplayed()
                    }
                }
                step("Validate comments loaded into comment list") {
                    commentItem {
                        assertIsDisplayed()
                    }
                }
            }
        }
    }
}