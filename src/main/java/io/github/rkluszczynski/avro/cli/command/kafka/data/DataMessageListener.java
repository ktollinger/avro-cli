package io.github.rkluszczynski.avro.cli.command.kafka.data;

import io.github.rkluszczynski.avro.cli.command.kafka.ExtendedMessageListener;
import io.github.rkluszczynski.avro.cli.command.kafka.text.TextMessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static java.time.Instant.ofEpochMilli;

public class DataMessageListener extends ExtendedMessageListener<String, byte[]> {

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> record) {
        long number = incrementAndGet();
        log.info(String.format("{%d} offset == %d, partition == %d, timestamp == %s",
                number, record.offset(), record.partition(), ofEpochMilli(record.timestamp())));

        System.out.printf("%s%n%n", Objects.toString(record.value()));
        final String filename = "message-" + number + ".dat";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(record.value());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Log log = LogFactory.getLog(TextMessageListener.class);
}
