package com.android.cars

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
@ExperimentalCoroutinesApi
class MainDispatcherRule(paused: Boolean) : TestWatcher() {
  val dispatcher = TestCoroutineDispatcher().apply { if (paused) pauseDispatcher() }

  override fun starting(description: Description?) {
    super.starting(description)

    Dispatchers.setMain(dispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)

    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }
}