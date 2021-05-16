package utils

import edit.ScenarioController
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

fun BehaviorSubject<Unit>.next() = onNext(Unit)

fun <T> Observable<T>.toSubject(): BehaviorSubject<T> {
  val subject = BehaviorSubject.create<T>()
  this.subscribe(subject)
  return subject
}

val <T> BehaviorSubject<ScenarioController.RefreshFromFilesystem<T>>.data get() =this.value?.data?.value
