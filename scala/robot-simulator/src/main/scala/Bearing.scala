object Bearing extends PrevNextEnum {
  type Bearing = Value
  val North, East, South, West = Value
}

trait PrevNextEnum extends Enumeration {

  lazy val prevOf: Value => Value = {
    val list = values.toList
    val map = (list.tail :+ list.head).zip(list).toMap
    v: Value => map(v)
  }
  lazy val nextOf: Value => Value = {
    val list = values.toList
    val map = list.zip(list.tail :+ list.head).toMap
    v: Value => map(v)
  }
}