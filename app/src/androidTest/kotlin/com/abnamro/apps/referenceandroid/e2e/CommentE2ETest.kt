package com.abnamro.apps.referenceandroid.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import com.abnamro.apps.referenceandroid.MainActivity
import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class CommentE2ETest :
    TestCase(kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport().addComposeSupport()) {

    val composeTestRule = createAndroidComposeRule<MainActivity>()
    val screenOrientationRule: ScreenOrientationRule =
        ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @get:Rule
    val ruleChain: TestRule = RuleChain
        .outerRule(screenOrientationRule)
        .around(composeTestRule)

    @Test
    fun validateCommentAreLoadedAndSnackBarDisplayed() {
        composeTestRule.onNodeWithTag("reload_button").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Comments loaded!").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithText("Comments loaded!").assertIsDisplayed()
    }

    @Test
    fun validateCommentAreLoadedAndSnackBarDisplayedRotatedDevice() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.onNodeWithTag("reload_button").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Comments loaded!").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithText("Comments loaded!").assertIsDisplayed()
    }
}