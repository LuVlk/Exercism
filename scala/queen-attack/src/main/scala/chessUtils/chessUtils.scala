package object chessUtils {
  def isOnChessBoard(x: Int, y: Int): Boolean = x >= 0 && x < 8 && y >= 0 && y < 8
}