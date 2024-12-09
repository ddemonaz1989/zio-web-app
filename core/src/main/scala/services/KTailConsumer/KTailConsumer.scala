package services.KTailConsumer

import config.KTailConfig
import model.Message
import services.KTailConsumer.Impl.KTailConsumerImpl
import zio._
import zio.kafka.consumer.ConsumerSettings
import zio.kafka.consumer.Consumer
import zio.stream._

trait KTailConsumer {
  def consume: Stream[Throwable, Message]
}

object KTailConsumer {
  def consume: URIO[KTailConsumer, Stream[Throwable, Message]] =
    ZIO.serviceWith[KTailConsumer](_.consume)

  val live: RLayer[KTailConfig, KTailConsumer] =
    ZLayer.scoped {
      for {
        config   <- ZIO.service[KTailConfig]
        topics    = config.topics.toSet
        consumer <- Consumer.make(
                      ConsumerSettings(config.bootstrapServers)
                        .withGroupId(config.groupId),
                    )
      } yield KTailConsumerImpl(topics, consumer)
    }
}
