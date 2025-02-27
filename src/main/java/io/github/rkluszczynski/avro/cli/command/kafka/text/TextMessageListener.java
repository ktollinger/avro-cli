package io.github.rkluszczynski.avro.cli.command.kafka.text;

import io.github.rkluszczynski.avro.cli.command.kafka.ExtendedMessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.time.Instant.ofEpochMilli;

public class TextMessageListener extends ExtendedMessageListener<String, String> {

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        long number = incrementAndGet();
        log.info(String.format("{%d} offset == %d, partition == %d, timestamp == %s",
                number, record.offset(), record.partition(), ofEpochMilli(record.timestamp())));

        System.out.printf("%s%n%n", record.value());
        final String filename = "message-" + number + ".txt";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(record.value().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Log log = LogFactory.getLog(TextMessageListener.class);
}