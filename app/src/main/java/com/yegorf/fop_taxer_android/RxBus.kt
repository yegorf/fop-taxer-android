package com.yegorf.fop_taxer_android

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun sendEvent(event: Event) {
        publisher.onNext(event)
    }

    fun sendData(event: Event, data: Any) {
        publisher.onNext(DataEvent(event, data))
    }

    fun listenEvent(event: Event): Observable<Event> {
        return publisher.ofType(Event::class.java)
            .filter { it == event }
    }

    fun <T> listenData(event: Event, type: Class<T>): Observable<T> {
        return publisher.ofType(DataEvent::class.java)
            .filter { it.event == event }
            .map { it.data }
            .cast(type)
    }

    private data class DataEvent(
        var event: Event,
        var data: Any
    )

    enum class Event {
        BACK_PRESSED
    }
}