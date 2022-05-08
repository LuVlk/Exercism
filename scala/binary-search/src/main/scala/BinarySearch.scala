import scala.annotation.tailrec

object BinarySearch {
  def find[A](list: List[A], element: A)(implicit ordering: Ordering[A]): Option[Int] = {
    @tailrec
    def go(low: Int, high: Int): Option[Int] = {
      if (low > high) return None
      val mid = (low + high) / 2
      if (list(mid) == element) return Some(mid)
      if (ordering.lt(element, list(mid))) go(low, mid - 1)
      else go(mid + 1, high)
    }
    go(0, list.length - 1)
  }
}