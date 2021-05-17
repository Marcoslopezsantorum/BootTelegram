package polls

import Apikey.miToken
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.polls.PollType.QUIZ
import com.github.kotlintelegrambot.network.fold

fun main() {
    bot {
        token = miToken
        dispatch {

//Devuelve un mensaje con la opcion que ha escogido

            pollAnswer {
                println("${pollAnswer.user.username} has selected the option ${pollAnswer.optionIds.lastOrNull()} in the poll ${pollAnswer.pollId}")
            }

            command("start") {

                val result = bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "El bot se ha iniciado")

                result.fold({

                }, {
                    // do something with the error
                })
            }

//Comando que mostrara una encuesta con los equipos de futbol
            command("poll") {
                bot.sendPoll(
//Recoge la id del usuario que ha votado
                    chatId = ChatId.fromId(message.chat.id),
//Tipo de encuesta
                    type = QUIZ,
//Pregunta
                    question = "Cual es el mejor equipo de futbol",
//Todas las opciones
                    options = listOf("Barsa", "Madrid", "Celta","Atletico de Madrid"),
//Cual es la opcion correcta
                    correctOptionId = 1,
//Si la votacion es anónima o no
                    isAnonymous = false,
//El tiempo que dura la votacion
                    openPeriod = 120,
                )
            }

//Comando que mostrara una encuesta con los mejores defensas
            command("defensa") {
                bot.sendPoll(
//Recoge la id del usuario que ha votado
                    chatId = ChatId.fromId(message.chat.id),
//Tipo de encuesta
                    type = QUIZ,
//Pregunta
                    question = "Mejor defensa del mundo?",
//Todas las opciones
                    options = listOf("Piqué", "Ramos", "Puyol"),
//Cual es la opcion correcta
                    correctOptionId = 2,
                    isAnonymous = false
                )
            }

//Comando que mostrara una encuesta con los mejores jugadores
            command("closedpoll") {
                bot.sendPoll(
//Recoge la id del usuario que ha votado
                    chatId = ChatId.fromId(message.chat.id),
//Tipo de encuesta
                    type = QUIZ,
//Pregunta
                    question = "Cual es el mejor jugador del mundo",
//Todas las opciones
                    options = listOf("Neymar", "Messi", "Ronaldo"),
//Todas las opciones
                    correctOptionId = 1,
//Cual es la opcion correcta
                    explanation = "Porque tiene mas balones de oro"
                )
            }
        }
    }.startPolling()
}
