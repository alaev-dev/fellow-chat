package ru.alaev.fellowgigachat.chat.message

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.message.handler.ReadMessageCommand
import ru.alaev.fellowgigachat.chat.message.handler.ReadMessageCommandHandler

@RestController
class MessageController (
    private val readMessageCommandHandler: ReadMessageCommandHandler,
){

    @PostMapping("/massage/{messageID}/read")
    fun updateMessageRead(@PathVariable messageID: String): UpdateMessageIsReadResponse {
        logger.info("Message is read :: $messageID")
        return UpdateMessageIsReadResponse(readMessageCommandHandler.handle(ReadMessageCommand(
            messageID = messageID.toLong(),
        )).id.toString())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

data class UpdateMessageIsReadResponse(
    val id: String,
)