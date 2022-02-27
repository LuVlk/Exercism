import scala.collection.mutable
import scala.collection.immutable.SortedMap

class School {
  type DB = mutable.Map[Int, Seq[String]]

  private val database: DB = mutable.Map()

  def add(name: String, g: Int): Unit = database update (g, {database.get(g) match {
    case Some(names) => names :+ name
    case None => Seq(name)
  }})

  def db: DB = database.clone()

  def grade(g: Int): Seq[String] = database.getOrElse(g, Seq())

  def sorted: SortedMap[Int, Seq[String]] = SortedMap(database.toSeq.map(kv => (kv._1, kv._2.sorted)):_*)
}
