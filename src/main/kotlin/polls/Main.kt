package polls


import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.polls.PollType.QUIZ

        dispatch {

//Devuelve un mensaje con la opcion que ha escogido

            pollAnswer {
                println("${pollAnswer.user.username} has selected the option ${pollAnswer.optionIds.lastOrNull()} in the poll ${pollAnswer.pollId}")
            }


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


                    isAnonymous = false
                )
            }


                    explanation = "Porque tiene mas balones de oro"
                )
            }
        }
    }.startPolling()
}
