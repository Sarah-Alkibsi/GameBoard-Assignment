import board.Cell
import board.Direction
import board.GameBoard
import board.SquareBoard
import java.lang.IllegalArgumentException

open class SquareBoardImpl(size: Int) : SquareBoard {

    private val board: Array<Array<Cell>>

    init {
        board = Array(size) { i ->
            Array(size) { j -> Cell(i + 1, j + 1) }
        }
    }

    override val width: Int
        get() = board.size

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if(i in 1..width && j in 1..width){
            return board[i-1][j-1]
        }
        return null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return board.flatten()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = ArrayList<Cell>()
        val row = board[i - 1]
        jRange.forEach { j ->
            if (j > width || j <= 0) {
                return@forEach
            }
            list.add(row[j - 1])
        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = ArrayList<Cell>()
        val columnIndex = j - 1
        iRange.forEach { i ->
            if (i > width || i <= 0) {
                return@forEach
            }
            list.add(board[i - 1][columnIndex])
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val (neighbourI, neighbourJ) = when (direction) {
            Direction.UP -> Pair(i - 1, j)
            Direction.DOWN -> Pair(i + 1, j)
            Direction.LEFT -> Pair(i, j - 1)
            Direction.RIGHT -> Pair(i, j + 1)
        }

        return getCellOrNull(neighbourI, neighbourJ)
    }

}

class GameBoardImpl<T>(size: Int) : SquareBoardImpl(size), GameBoard<T> {

    private val values = mutableMapOf<Cell, T?>()

    init {
        getAllCells().forEach {
            values[it] = null
        }
    }

    override fun get(cell: Cell): T? {
        return values[cell]
    }

    override fun set(cell: Cell, value: T?) {
        values[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return values.filter { (_, value) -> predicate(value) }.map { (cell, _) -> cell }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return find(predicate) != null
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return filter(predicate).size == values.size
    }

}