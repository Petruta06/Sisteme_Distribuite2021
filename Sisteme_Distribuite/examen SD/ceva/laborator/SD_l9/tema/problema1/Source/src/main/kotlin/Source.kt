package  com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder

import java.io.File

//citesc din fisier lista de comenzi din pipe si le trimit catre procesare
// cu un delay de 5 s
@EnableBinding(Source::class)
@SpringBootApplication
class PipelineSource
{
        companion object
        {
            var i:Int = -1
        }
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = [Poller(fixedDelay = "5000", maxMessagesPerPoll = "1")])
    fun operation():() -> Message<String> {
        var listt = read()
        if (i >= listt.size) {
            return {
                MessageBuilder.withPayload("gata!").build()
            }
        } else {
            if (!listt.isEmpty()) {
                i = i + 1
                return {
                    MessageBuilder.withPayload(listt[i]).build()
                }
            } else {
                var error = "Eroare! Fisierul de la aceea cale nu exista!"
                return {
                    MessageBuilder.withPayload(error).build()
                }
            }
        }

    }


    fun read():List<String>
    {
        var sir:List<String> = listOf<String>()
        val path ="file.txt"
        val file =File(path)
        if(!file.exists())
        {
            return sir
        }
        sir = file.bufferedReader().readLines()
        return sir
    }
}


fun main(args: Array<String>) {
    runApplication<PipelineSource>(*args)
}