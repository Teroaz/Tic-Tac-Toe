package tictactoe

import kotlin.math.abs

class TicTacToe {

    private var field: Array<Array<Char>> = Array(3) { Array(3) { ' ' } }
    var players: Array<Char> = arrayOf('X', 'O')
    var indexPlayer = 0

    fun showField() {
        println("---------")
        for (i in 0 until 3) {
            print("| ")
            for (j in 0 until 3) {
                print("${field[i][j]} ")
            }
            print("|")
            println()
        }
        println("---------")
    }

    fun processCoordinates() {

        println("Enter the coordinates :")
        val input = readLine()!!

        val coords: List<Int>
        try {
            coords = input.split(" ").map { it.toInt() }
        } catch (e: Exception) {
            println("You should enter numbers!")
            return processCoordinates()
        }

        if (coords.any { it !in 1..3 }) {
            println("Coordinates should be from 1 to 3!")
            return processCoordinates()
        }

        val (x, y) = coords.map { it - 1 }

        if (arrayOf('X', 'O').contains(field[x][y])) {
            println("This cell is occupied! Choose another one!")
            return processCoordinates()
        }

        field[x][y] = players[indexPlayer]
        indexPlayer = (indexPlayer + 1) % 2
        showField()
        analyze()
    }

    fun analyze(): Any {
        val charsWinning: MutableList<Char> = mutableListOf()
        val symbolsOnTheField = field.flatten()
        val remainingPlaces = symbolsOnTheField.count { it == ' ' }
        val xCount = symbolsOnTheField.count { it == 'X' }
        val oCount = symbolsOnTheField.count { it == 'O' }

        if (abs(xCount - oCount) > 1) return "Impossible"

        for (index in 0..2) {
            if (arrayOf('O', 'X').contains(field[index][0])) {
                if (field[index][0] == field[index][1] && field[index][0] == field[index][2]) {
                    charsWinning.add(field[index][0])
                }
            }
            if (arrayOf('O', 'X').contains(field[0][index])) {
                if (field[0][index] == field[1][index] && field[0][index] == field[2][index]) {
                    charsWinning.add(field[0][index])
                }
            }
        }

        if ((field[1][1] == field[0][0] && field[1][1] == field[2][2]) || (field[1][1] == field[0][2] && field[1][1] == field[2][0])) {
            if (arrayOf('O', 'X').contains(field[1][1])) charsWinning.add(field[1][1])
        }

        return if (charsWinning.size == 1) {
            println("${charsWinning[0]} wins")
        } else if (charsWinning.size > 1) {
            println("Impossible")
        } else {
            if (remainingPlaces != 0) {
                processCoordinates()
            } else {
                println("Draw")
            }
        }
    }
}

fun main() {
    val game = TicTacToe()

    with(game) {
        showField()
        processCoordinates()
    }
}
