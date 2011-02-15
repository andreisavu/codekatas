
class Board(val rows: Int, val cols: Int) {
    require(rows > 0 && cols > 0)

    private var board: Array[Array[Boolean]] = new Array[Array[Boolean]](rows)
    for(row <- 0 to (rows - 1)) {
        board(row) = new Array[Boolean](cols)
        /* by default scala sets each cell to false */
    }

    def set(row: Int, col: Int, status: Boolean): Unit = {
        require(row > 0 && row <= rows)
        require(col > 0 && col <= cols)

        board(row - 1)(col - 1) = status
    }

    def get(row: Int, col: Int): Boolean = 
        if (row > 0 && row <= rows && col > 0 && col <= cols) 
            board(row - 1)(col - 1)
        else
            false

    def neighbours(row: Int, col: Int): Int = {
        require(row > 0 && row <= rows)
        require(col > 0 && col <= cols)

        val x_deltas = List(-1, -1, 0, 1, 1, 1, 0, -1)
        val y_deltas = List(0, -1, -1, -1, 0, 1, 1, 1)

        (for {
            (dx, dy) <- x_deltas zip y_deltas 
            if get(row + dy, col + dx)
        } yield true).length     
    }

    private def rules(alive: Boolean, neighbours: Int): Boolean = 
        if (alive) {
            if (neighbours == 2 || neighbours == 3) true else false
        } else if (neighbours == 3) true else false

    def next(): Board = {
        val board = new Board(rows, cols)
        for {
            row <- 1 to rows
            col <- 1 to cols
        } board.set(row, col, rules(get(row, col), neighbours(row, col)))
        board
    }

    def mkString = {
        val rows: Array[String] = for(row <- board) 
            yield row.map(if (_) "*" else "-").mkString
        rows.mkString("\n")
    } 

    override def toString = mkString 
}

object Board {

    def fromString(content: String): Board = {
        val rows: Int = content.split("\n").length
        val cols: Int = content.split("\n")(0).length

        val board = new Board(rows, cols)
        var current_row = 1

        for(line <- content.split("\n")) {
            var current_col = 1
            for(ch <- line) {
                if (ch == '*') board.set(current_row, current_col, true)
                current_col += 1
            }
            current_row += 1
        }

        board
    }
}


