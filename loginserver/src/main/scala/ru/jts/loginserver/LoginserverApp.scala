package ru.jts.loginserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{ApplicationArguments, ApplicationRunner, SpringApplication}

/**
  * @author Camelion
  * @since 06.11.15
  */
class AppConfig

@SpringBootApplication
object LoginserverApp extends App with ApplicationRunner {

  // context will be initialized after run method
  var context = SpringApplication.run(classOf[AppConfig])

  override def run(args: ApplicationArguments): Unit = {

  }
}
