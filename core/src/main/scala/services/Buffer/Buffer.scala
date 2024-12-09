package services.Buffer

import model.Message
import services.Buffer.Impl.BufferImpl
import zio._

trait Buffer {
  def offer(message: Message): UIO[Unit]
  def poll(): UIO[Option[Message]]
}

object Buffer {
  val live: ULayer[Buffer] =
    ZLayer(Queue.unbounded[Message].map(BufferImpl))
}
