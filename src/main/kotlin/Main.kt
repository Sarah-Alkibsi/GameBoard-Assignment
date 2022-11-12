import board.GameBoard
import board.SquareBoard

fun main(args: Array<String>) {
val board = createSquareBoard(3)

    println("width: ${board.width}")
    println("all-cells: ${board.getAllCells()}")
    println("getCellOrNull(1,3): ${board.getCellOrNull(1, 3)}")
    println("get(1, 2): ${board.getCell(1, 2)}")
    println("row 1-1..3: ${board.getRow(1, 1..3)}")
    println("col 1..3 - 2: ${board.getColumn(1..3, 2)}")


//    operator fun <T> GameBoard<T>.get(i: Int, j: Int) = get(getCell(i, j))
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImp(width)
//fun <T> createGameBoard(width: Int): GameBoard<T> = TODO()
