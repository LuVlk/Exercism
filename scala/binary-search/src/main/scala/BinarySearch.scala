import scala.annotation.tailrec

object BinarySearch {
  @tailrec
  def find[A](list: List[A], element: A, offset: Int = 0)(implicit ordering: Ordering[A]): Option[Int] = {
    if (list.isEmpty) return None
    val mid = list.length / 2
    if (list(mid) == element) return Some(offset + mid)
    if (ordering.lt(element, list(mid))) find(list.slice(0, mid), element, offset)
    else find(list.slice(mid + 1, list.length + 1), element, offset + mid + 1)
  }
}