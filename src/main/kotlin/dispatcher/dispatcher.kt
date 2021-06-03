package dispatcher

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN_V2
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.github.kotlintelegrambot.entities.TelegramFile.ByUrl
import com.github.kotlintelegrambot.entities.dice.DiceEmoji
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.github.kotlintelegrambot.network.fold


fun main() {
    /**
     * author: Marcos López Santorum
     * David Mariño Alonso
     *
     */
    val bot = bot {

        token = Apikey.miToken //el token indica cual es nuentro bot y a que le estamos mandando nuetros comandos

        dispatch {

            //El mensaje aparece cuando le mandas al bot un sticker
            message(Filter.Sticker) {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "You have received an awesome sticker \\o/")
            }
            //El mensaje salta cuando reenvías el mensaje del bot o por que lo respondes en el chat
            message(Filter.Reply or Filter.Forward){

                bot.sendMessage(ChatId.fromId(message.chat.id), text = "someone is replying or forwarding messages ...")
            }
            /**
             * @param start Nombre del comando de nuestro bot
             * Este comando te devuelve un texto en el que nos indica que el bor a sido iniciado
             */

            command("empezar") {

                val result = bot.sendMessage(chatId = ChatId.fromId(update.message!!.chat.id), text = "Bot started")

                result.fold(
                    {
                        // do something here with the response
                    },
                    {
                        // do something with the error
                    }
                )
            }
            /**
             * @param hola Nombre del comando de nuestro bot
             * Este comando te devuelve un texto
             */

            command("hola") {

                val result = bot.sendMessage(chatId = ChatId.fromId(update.message!!.chat.id), text = "Ey, mu buenas!")

                result.fold(
                    {
                        // do something here with the response
                    },
                    {
                        // do something with the error
                    }
                )
            }
            /**
             * @param adios Nombre del comando de nuestro bot
             * Este comando te devuelve un texto
             */
            command("adios") {

                val result = bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Adiós, hasta la próxima")

                result.fold({

                }, {
                    // do something with the error
                })
            }
            /**
             * @param comandoconargs Nombre del comando de nuestro bot
             * Este comando te devuelve un texto
             */
            command("commandwithargs") {
                val joinedArgs = args.joinToString()/*Crea una cadena de todos los elementos separados usando un
                separador y usando el prefijo y sufijo dados si se suministran. Si la colección puede ser enorme, puede
                especificar un valor de límite no negativo, en cuyo caso solo se agregarán los primeros elementos de
                límite, seguidos de la cadena truncada (que por defecto es "...").*/

                val response = if (joinedArgs.isNotBlank()) joinedArgs else "No hay texto! :("
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = response)
            }
            /**
             * @param markdown Nombre del comando de nuestro bot
             * Este comando te devuelve un texto
             */
            //Altera el texto sin necesidad de un editor de texto. Solo sirve para texto incluido en el comando

            command("markdown") {
                val markdownText = "Mensaje guay: *Markdown* es maravilloso :P"
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = markdownText,
                    parseMode = MARKDOWN
                )
            }
            /**
             * @param markdown2 Nombre del comando de nuestro bot
             * Este comando te devuelve un texto
             */
            //Version mejorada del marckdown. Lo de la mencion del usuario aun no funciona ni en la libreria de kotlinTelegram


            command("markdownv2") {
                val markdownV2Text = """
                    *bold \*text*
                    _italic \*text_
                    __underline__
                    ~strikethrough~
                    *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
                    [inline URL](http://www.example.com/)
                    [inline mention of a user](tg://user?id=123456789)
                    `inline fixed-width code`
                    ```kotlin
                    fun main() {
                        println("Hello Kotlin!")
                    }
                    ```
                """.trimIndent()
                /*trimIndent() ->
                Detecta una sangría mínima común de todas las líneas de entrada, la elimina de cada línea y también
                elimina la primera y la última línea si están en blanco (observe la diferencia en blanco vs vacío).
                Tenga en cuenta que las líneas en blanco no afectan el nivel de sangría detectado. En caso de que haya
                líneas que no estén en blanco sin espacios en blanco iniciales (sin sangría), la sangría común es 0 y,
                por lo tanto, esta función no cambia la sangría. No conserva los finales de línea originales.
                 */

                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = markdownV2Text,
                    parseMode = MARKDOWN_V2
                )
            }
            /**
             * @param inlinebuttons Nombre del comando de nuestro bot
             * Este comando retorna botones que se le puede asociar texto o un aventana emerjente con texto
             */
            //sirve para crear dos botones

            command("inlinebuttons") {
                val inlineKeyboardMarkup = InlineKeyboardMarkup.create(
                //text es el texto que aparece en el botón
                // callbackData Retoena el callbackQuery asociado (linea de código)
                    listOf(InlineKeyboardButton.CallbackData(text = "Test Inline Button", callbackData = "testButton")),
                //text es el texto que aparece en el boton
                //callbackData Devuelve el callbackQuery asociado (linea de codigo )
                    listOf(InlineKeyboardButton.CallbackData(text = "Show alert", callbackData = "showAlert"))
                )
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, inline buttons!",
                    replyMarkup = inlineKeyboardMarkup
                )
            }
            /**
             * @param f Nombre del comando de nuestro bot
             * Este comando retorna un  texto y dos botones situados en donde el usuario puede escribir
             */
            /*
            Con esto le envias tu direccion al bot y tu numero de telefono. Solo funciona por privado con el bot, en
            un grupo no funciona este comando
            */

            command("userbuttons") {
                val keyboardMarkup = KeyboardReplyMarkup(keyboard = generateUsersButton(), resizeKeyboard = true)
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, users buttons!",
                    replyMarkup = keyboardMarkup
                )
            }
            /**
             * @param mediagroup Nombre del comando de nuestro bot
             * Este comando retorna un texto y fotos
             */
            //Este comando sirve para poder enviar fotos con el bot

            command("mediagroup") {
                bot.sendMediaGroup(
                    chatId = ChatId.fromId(message.chat.id),
                    mediaGroup = MediaGroup.from(
                        InputMediaPhoto(
                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
                            caption = "I come from an url :P"
                        ),
                        InputMediaPhoto(
                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
                            caption = "Me too!"
                        )
                    ),
                    replyToMessageId = message.messageId
                )
            }
            /**
             * @param Quéesestoo Es el texto que aparece en el collbackData del boton asociado
             * Retorna el callbackQuery
             */
            //este texto de entrada tiene que coincidir con el texto que se le ponga al boton EN callbackData

            callbackQuery("queesestoo") {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(ChatId.fromId(chatId), callbackQuery.data)
            }
            /**
             * @param muestraAlerta Es el texto que aparece en el collbackData del boton asociado
             * Retorna el callbackQuery
             */
            //este texto de entrada tiene que coincidir con el texto que se le ponga al boton en callbackData

            callbackQuery(
                callbackData = "muestraAlerta",
                callbackAnswerText = "Esto es una alerta!! Corran mientras puedan!",
                callbackAnswerShowAlert = true
            ) {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(ChatId.fromId(chatId), callbackQuery.data)
            }

            // si pones /Marco te devuelve Polo
            text("Marco") {
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Polo")
            }

            //te devuelve la latituid y longitud de te localizacion
            location {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Your location is (${location.latitude}, ${location.longitude})",
                    replyMarkup = ReplyKeyboardRemove()
                )
            }

            //devuelve un mensaje de texto con tu nombre
            contact {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hello, ${contact.firstName} ${contact.lastName}",
                    replyMarkup = ReplyKeyboardRemove()
                )
            }

            channel {
                // Handle channel update
            }

            inlineQuery {
                val queryText = inlineQuery.query

                if (queryText.isBlank() or queryText.isEmpty()) return@inlineQuery

                val inlineResults = (0 until 5).map {
                    InlineQueryResult.Article(
                        id = it.toString(),
                        title = "$it. $queryText",
                        inputMessageContent = InputMessageContent.Text("$it. $queryText"),
                        description = "Add $it. before you word"
                    )
                }
                bot.answerInlineQuery(inlineQuery.id, inlineResults)
            }

            //Envías una foto y te devuelve "Wow qué fotaza!". Si lo haces en un grupo tendrías que responderle a un comentario del bot
            photos {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Wow qué fotaza!"
                )
            }

            /**
             * @param dado nombre del comando
             * Retorna un dado/diana en formato emoji que saca una puntuacion
             */
            command("diceasdartboard") {
                bot.sendDice(ChatId.fromId(message.chat.id), DiceEmoji.Dartboard)
            }
            //cuando reenvias la diana a el bot por privado te devuelve la puntiacion

            dice {
                bot.sendMessage(ChatId.fromId(message.chat.id), "A dice ${dice.emoji.emojiValue} with value ${dice.value} has been received!")
            }

            //Cuando salga un error saltará un mensaje
            telegramError {
                println(error.getErrorMessage())
            }
        }
    }

    bot.startPolling()
}

fun generateUsersButton(): List<List<KeyboardButton>> {
    return listOf(
        listOf(KeyboardButton("Request location (not supported on desktop)", requestLocation = true)),
        listOf(KeyboardButton("Request contact", requestContact = true))
    )
}
