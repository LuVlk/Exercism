class DNA(sequence: String) {

  def nucleotideCounts: Either[IllegalArgumentException, Map[Char, Int]] = {

    val zeroValue = Right(Map[Char, Int]('A' -> 0, 'C' -> 0, 'G' -> 0, 'T' -> 0))

    sequence.foldLeft[Either[IllegalArgumentException, Map[Char, Int]]](zeroValue)(
      (errorOrMap, char) => { errorOrMap.fold(
        Left(_),
        map => map.get(char) match {
          case None => Left(new IllegalArgumentException)
          case Some(count) => Right(map.updated(char, count + 1))
        })
      })
  }
}
