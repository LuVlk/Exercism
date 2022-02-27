import scala.collection.mutable

object PascalsTriangle {

  private val rowCache = mutable.Map[Int, List[Int]]()

  def row(number: Int): List[Int] = {
    if (number <= 0) {
      List()
    }
    else number match {
      case n if rowCache.contains(n) => rowCache(n)
      case 1 => List(1)
      case _ => {
        val currentRow = (row(number - 1) :+ 0, 0 +: row(number - 1)).zipped.map(_ + _)
        rowCache.update(number, currentRow)
        currentRow
      }
    }
  }

  def rows(number: Int): List[List[Int]] = {
    if (number <= 0) {
      List()
    }
    else (1 to number).map(row).toList
  }

}