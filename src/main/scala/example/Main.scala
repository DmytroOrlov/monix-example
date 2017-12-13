package example

import monix.execution.Scheduler.Implicits.global
import monix.execution.atomic.Atomic
import monix.execution.{Cancelable, FutureUtils}
import monix.reactive.Observable
import monix.reactive.observers.Subscriber

import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {
  private val device = new Device

  val f = device.foreach(println)

  FutureUtils.delayedResult(3.seconds)(()).foreach(_ => device.motorDown())

  Await.ready(f, Duration.Inf)
}

class Device extends Observable[Long] {

  def motorUp(): Unit =
    transformer.set(inc)

  def motorDown(): Unit =
    transformer.set(dec)


  private val inc: Long => Long = _ + 1
  private val dec: Long => Long = _ - 1
  val transformer = Atomic(inc)

  override def unsafeSubscribeFn(subscriber: Subscriber[Long]): Cancelable = {
    var state = 0L
    Observable
      .interval(1.second)
      .map { _ =>
        state = transformer.get(state)
        state
      }
      .unsafeSubscribeFn(subscriber)
  }
}
