package com.felipecosta.kotlinrxjavasample.rx

import com.felipecosta.kotlinrxjavasample.utils.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class AsyncCommandTest {

    @Test
    fun whenSubscribeToExecutionThenAssertValue() {

        val mockResult = mock<Any>()

        val observable = Observable.just(mockResult)

        val executionObserver: TestObserver<Any> = TestObserver.create()
        val asyncCommand = AsyncCommand<Any>({ observable })

        asyncCommand.execution.subscribe(executionObserver)

        val disposable = asyncCommand.execute().subscribe()

        executionObserver.assertValue(mockResult)

        disposable.dispose()
    }

    @Test
    fun whenSubscribeToExecutingThenAssertStartAndFinishExecutingValues() {

        val mockResult = mock<Any>()

        val observable = Observable.just(mockResult)

        val executingObserver: TestObserver<Boolean> = TestObserver.create()
        val asyncCommand = AsyncCommand<Any>({ observable })

        asyncCommand.executing.subscribe(executingObserver)

        val disposable = asyncCommand.execute().subscribe()

        executingObserver.assertValues(true, false)

        disposable.dispose()
    }

    @Test
    fun whenSubscribeToErrorsThenAssertException() {

        val exception = Exception()

        val observable = Observable.error<Any>(exception)

        val errorObserver: TestObserver<Throwable> = TestObserver.create()
        val asyncCommand = AsyncCommand<Any>({ observable })

        asyncCommand.errors.subscribe(errorObserver)

        val disposable = asyncCommand.execute().subscribe()

        errorObserver.assertValue(exception)

        disposable.dispose()
    }
}