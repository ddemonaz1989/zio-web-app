package services.KTailConsumer.Impl

import model.Message
import services.KTailConsumer.KTailConsumer
import zio.kafka.consumer.Consumer
import zio.kafka.consumer.Subscription.Topics
import zio.kafka.serde.Serde
import zio.stream._

final case class KTailConsumerImpl(topics: Set[String], consumer: Consumer) extends KTailConsumer {
  override def consume: Stream[Throwable, Message] =
    consumer
      .plainStream[Any, Array[Byte], Array[Byte]](
        Topics(topics),
        Serde.byteArray,
        Serde.byteArray,
      )
      .map(record =>
        Message(
          record.offset.topicPartition.topic(),
          record.partition,
          record.offset.offset,
          record.key,
          record.value,
        ),
      )
}
