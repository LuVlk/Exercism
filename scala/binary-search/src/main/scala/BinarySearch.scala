object BinarySearch {
  def find[A](list: List[A], element: A)(implicit ordering: Ordering[A]): Option[Int] = {
    var (low, high) = (0, list.length - 1)

    while (low <= high) {
      val mid = (low + high) / 2
      if (list(mid) == element) return Some(mid)
      if (ordering.lt(element, list(mid))) high = mid - 1
      else low = mid + 1
    }
    None
  }
}