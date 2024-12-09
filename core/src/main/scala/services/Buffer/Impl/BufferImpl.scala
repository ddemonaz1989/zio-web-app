package services.Buffer.Impl

import model.Message
import services.Buffer.Buffer
import zio._

final case class BufferImpl(queue: Queue[Message]) extends Buffer {
  override def offer(message: Message): UIO[Unit] =
    queue.offer(message).unit *>
      ZIO.logInfo(s"buffer message ${message.id}")

  override def poll(): UIO[Option[Message]] = queue.poll
}
