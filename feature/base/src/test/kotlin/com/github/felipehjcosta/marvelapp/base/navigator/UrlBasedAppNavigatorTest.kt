package com.github.felipehjcosta.marvelapp.base.navigator

import android.app.Activity
import com.github.felipehjcosta.marvelapp.base.BuildConfig
import com.github.felipehjcosta.marvelapp.base.TestStubApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(
        application = TestStubApplication::class,
        manifest = Config.NONE,
        constants = BuildConfig::class,
        sdk = [21]
)
class UrlBasedAppNavigatorTest {

    private val activityController: ActivityController<Activity> = Robolectric.buildActivity(Activity::class.java)
    private val shadowActivity = Shadows.shadowOf(activityController.get())
    private val appNavigator = UrlBasedAppNavigator()

    @Test
    fun ensureShowWikiStartsActivity() {
        appNavigator.showWiki(activityController.get())

        assertNotNull(shadowActivity.peekNextStartedActivity())
    }

    @Test
    fun ensureShowListStartsActivity() {
        appNavigator.showList(activityController.get())

        assertNotNull(shadowActivity.peekNextStartedActivity())
    }

    @Test
    fun ensureShowDetailStartsActivity() {
        appNavigator.showDetail(activityController.get(), 1)

        assertNotNull(shadowActivity.peekNextStartedActivity())
    }
}