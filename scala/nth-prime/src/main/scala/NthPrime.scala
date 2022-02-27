object NthPrime {

  def prime(nth: Int): Option[Int] = {
    if (nth <= 0) None
    else Some(primes().take(nth).last)
  }

  private def primes(from: Int = 2): Stream[Int] = {
    if (isPrime(from)) from #:: primes(from + 1)
    else primes(from + 1)
  }

  private def isPrime(num: Int): Boolean = {
    if (num <= 3) num > 1
    else if (num % 2 == 0) false
    else if (num % 3 == 0) false
    else {
      var i = 5
      while (i * i <= num) {
        if (num % i == 0) return false
        if (num % (i + 2) == 0) return false
        i = i + 6
      }
      true
    }
  }
}