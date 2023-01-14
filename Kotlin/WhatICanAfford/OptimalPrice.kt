package game

import game.GameProvider.Companion.gameSteam
import java.io.File
import java.net.URI
import java.text.DecimalFormat
import java.util.*


val array: ArrayList<ArrayList<Game>> = arrayListOf()
var df = DecimalFormat("0.00")


fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}


fun main() {


    val listOfGames = gameSteam //concatenate(gamesEneba, gamesInstant,gameSteam)
    val target = 8.11
    val scanner = Scanner(System.`in`)
    println("Do you want to see only the $target€ bundles?(Yes/No)")
    val response = scanner.nextLine()
    sumUp(ArrayList(listOfGames), target, response)

    if (!response.equals("Yes", ignoreCase = true)) {
        val randomGames = generateSequence {
            array[array.indices.random()]
        }.distinct().take(10).toSet().toList()

        println(randomGames)

        randomGames.forEachIndexed { i, game ->
            println("Bundle -0${i + 1}- ")
            var sumPrice = 0.0
            randomGames[i].forEachIndexed { j, _ ->
                println("${game[j].name}: ${game[j].price}€")
                sumPrice += game[j].price
            }
            println("-")
            println("Total: ${df.format(sumPrice).replace(',', '.')}€")
            println("-")
        }
    } else {
        File("C:\\Users\\Adrian\\Desktop\\bundleGames.txt").printWriter().use { out ->
            array.forEachIndexed { i, game ->
                out.print("(${array[i].size} games)")
                array[i].forEachIndexed { j, _ ->
                    out.print(" ${game[j].name}: ${game[j].price}€ ");
                }
                out.println("")
            }
        }
        println("bundleGames.txt successfully created !!")
    }
}

fun sumUp(games: ArrayList<Game>, target: Double, response: String) {
    sumUpRecursive(games, target, ArrayList(), response)
}

fun sumUpRecursive(games: ArrayList<Game>, target: Double, partial: ArrayList<Game>, response: String) {
    var s = 0.0
    for (game in partial) s += game.price
    if (response.equals("Yes", ignoreCase = true)) {
        if (s == target) {
            //println("$partial=$target")
            array.add(partial)
        }
    } else {
        if (s <= target) {
            if (partial.size >= 2) {
                array.add(partial)
            }
        }
    }


    if (s >= target) return
    for (i in 0 until games.size) {
        val remaining = ArrayList<Game>()
        val n = games[i]
        for (j in i + 1 until games.size) remaining.add(games[j])
        val partialRec = ArrayList(partial)
        partialRec.add(n)
        sumUpRecursive(remaining, target, partialRec, response)
    }

}



