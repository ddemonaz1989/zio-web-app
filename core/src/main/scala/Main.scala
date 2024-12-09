import config.KTailConfig
import services.Buffer.Buffer
import services.KTailConsumer.KTailConsumer
import zio._
import zio.config._
import zio.config.typesafe._
import zio.stream.ZSink

object Main extends ZIOAppDefault {

  override val bootstrap: ZLayer[Any, Throwable, KTailConfig & Buffer & KTailConsumer] =
    ZLayer.make[KTailConfig & Buffer & KTailConsumer](
      Runtime.setConfigProvider(ConfigProvider.fromResourcePath()),
      KTailConfig.live,
      Buffer.live,
      KTailConsumer.live,
    )

  private val program: RIO[KTailConfig & Buffer & KTailConsumer, Unit] =
    for {
      consume <- KTailConsumer.consume
      _       <- consume.mapZIO(message => ZIO.serviceWithZIO[Buffer](_.offer(message))).run(ZSink.drain)
    } yield ()

  override def run: ZIO[Any & ZIOAppArgs & Scope, Throwable, Unit] = program

}
