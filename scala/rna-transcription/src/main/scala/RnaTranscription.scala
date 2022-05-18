object RnaTranscription {
  private val nucleotideComplements = Map(
    'G' -> 'C',
    'C' -> 'G',
    'T' -> 'A',
    'A' -> 'U'
  )

  def toRna(sequence: String): Option[String] = sequence
      .map(nucleotideComplements.get)
      .foldLeft[Option[String]](Some(""))((accO, sO) => for {
          acc <- accO
          s   <- sO
        } yield acc + s)
}