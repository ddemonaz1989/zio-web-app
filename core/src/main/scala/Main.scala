import config.KTailConfig
import services.Buffer.Buffer
import services.KTailConsumer.KTailConsumer
import zio._
import zio.config._
import zio.config.typesafe._
import zio.stream.ZSink
import zio.logging.backend.SLF4J

object Main extends ZIOApp {

  override type Environment = KTailConfig & Buffer & KTailConsumer

  override implicit def environmentTag: EnvironmentTag[Environment] = Tag[Environment]

  override val bootstrap: ZLayer[Any, Throwable, Environment] =
    ZLayer.make[KTailConfig & Buffer & KTailConsumer](
      Runtime.removeDefaultLoggers >>> SLF4J.slf4j,
      Runtime.setConfigProvider(ConfigProvider.fromResourcePath()),
      KTailConfig.live,
      Buffer.live,
      KTailConsumer.live,
    )

  private val program: RIO[Environment, Unit] =
    for {
      consume <- ZIO.serviceWith[KTailConsumer](_.consume)
      _       <- consume.mapZIO(message => ZIO.serviceWithZIO[Buffer](_.offer(message))).run(ZSink.drain)
    } yield ()

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    program
}
