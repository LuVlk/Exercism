import chessUtils.isOnChessBoard

import scala.annotation.tailrec

case class Queen(x: Int, y: Int) {
  def isHorizontalTo(other: Queen): Boolean = y == other.y

  def isVerticalTo(other: Queen): Boolean = x == other.x

  @tailrec
  private def isOnTopOfOrAdjacentTo(next: Queen => Queen)(other: Queen): Boolean = {
    if (!isOnChessBoard(other.x, other.y)) false
    else if (x == other.x && y == other.y) true
    else isOnTopOfOrAdjacentTo(next)(next(other))
  }

  private def isLowerRightDiagonalTo(other: Queen): Boolean = isOnTopOfOrAdjacentTo(q => Queen(q.x + 1, q.y - 1))(other)

  private def isLowerLeftDiagonalTo(other: Queen): Boolean = isOnTopOfOrAdjacentTo(q => Queen(q.x - 1, q.y - 1))(other)

  private def isUpperRightDiagonalTo(other: Queen): Boolean = isOnTopOfOrAdjacentTo(q => Queen(q.x + 1, q.y + 1))(other)

  private def isUpperLeftDiagonalTo(other: Queen): Boolean = isOnTopOfOrAdjacentTo(q => Queen(q.x - 1, q.y + 1))(other)

  def isDiagonalTo(other: Queen): Boolean = {
    isUpperLeftDiagonalTo(other) ||
      isUpperRightDiagonalTo(other) ||
      isLowerRightDiagonalTo(other) ||
      isLowerLeftDiagonalTo(other)
  }
}

object Queen {
  def create(x: Int, y: Int): Option[Queen] = {
    if (!isOnChessBoard(x, y)) None
    else Some(Queen(x, y))
  }
}

object QueenAttack {
  def canAttack(q1: Queen, q2: Queen): Boolean = q1.isVerticalTo(q2) || q1.isHorizontalTo(q2) || q1.isDiagonalTo(q2)
}
