package ru.alaev.fellowgigachat.config

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.StatusPrinter
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.encoder.LogstashEncoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LogbackConfiguration {

    @Bean
    @Throws(JoranException::class)
    fun configureLogback(): Logger {
        val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val loggerContext = rootLogger.loggerContext

        val logstashAppender = LogstashTcpSocketAppender().apply {
            name = "LOGSTASH"
            context = loggerContext
            addDestination("logstash:5044")
            encoder = LogstashEncoder().apply {
                context = loggerContext
                start()
            }
            start()
        }

        val consoleAppender = ConsoleAppender<ILoggingEvent>().apply {
            name = "STDOUT"
            context = loggerContext
            encoder = PatternLayoutEncoder().apply {
                context = loggerContext
                pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
                start()
            }
            start()
        }

        rootLogger.addAppender(logstashAppender)
        rootLogger.addAppender(consoleAppender)
        StatusPrinter.print(loggerContext)
        return rootLogger
    }
}
