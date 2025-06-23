package com.aksrua.common.outboxmessagerelay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
// autoconfigure에 등록되고, 다른 모듈에서 DI할때 componentscan 한다.
@ComponentScan("com.aksrua.common.outboxmessagerelay")
// 전송되지 않은 이벤트들을 주기적으로 가져와서 카프카로 보낸다.
@EnableScheduling
public class MessageRelayConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public KafkaTemplate<String, String> messageRelayKafkaTemplate() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.ACKS_CONFIG, "all");
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
	}

	@Bean
	public Executor messageRelayPublishEventExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("mr-pub-event-");
		return executor;
	}

	@Bean
	public Executor messageRelayPublishPendingEventExecutor() {
		return Executors.newSingleThreadScheduledExecutor();
	}
}
