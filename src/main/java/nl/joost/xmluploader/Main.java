package nl.joost.xmluploader;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Slf4j
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "nl.joost.xmluploader.repo")
@ComponentScan(basePackages = "nl.joost.xmluploader")
@Configuration
public class Main {

  public static void main(String[] args) {
    final var springApplication = new SpringApplicationBuilder(Main.class).bannerMode(
        Banner.Mode.CONSOLE).logStartupInfo(true).build();

    springApplication.run(args);
  }

  @EventListener
  void onStartup(ApplicationReadyEvent event) {
    log.info(
        "################################# APPLICATION STARTED #################################");
  }

  @PreDestroy
  void onShutdown() {
    log.info(
        "################################# APPLICATION STOPPED #################################");
  }

}