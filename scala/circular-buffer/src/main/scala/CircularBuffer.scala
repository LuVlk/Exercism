class EmptyBufferException() extends Exception {}

class FullBufferException() extends Exception {}

class CircularBuffer(private val capacity: Int) {

  private var data = Seq[Int]()

  def write(value: Int) = data match
    case seq if seq.size == capacity => throw new FullBufferException
    case _                           => data = data :+ value

  def read(): Int = data match
    case Seq() => throw new EmptyBufferException
    case value :: rest => {
      data = rest
      value
    }

  def overwrite(value: Int) = data match
    case seq if seq.size < capacity => write(value)
    case _ => {
      read()
      write(value)
    }

  def clear() = data = Seq()
}
