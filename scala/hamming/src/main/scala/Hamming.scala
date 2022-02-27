object Hamming {

  def distance(A: String, B: String): Option[Int] =
    if (A.length != B.length) None
    else Some((A zip B).map(pair => if (pair._1 != pair._2) 1 else 0).sum)
}