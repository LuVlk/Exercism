import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


/** @version 1.2.0 */
class RnaTranscriptionTest extends AnyFunSuite with Matchers {

  test("RNA complement of cytosine is guanine") {
    RnaTranscription.toRna("C") should be(Some("G"))
  }

  test("RNA complement of guanine is cytosine") {
    RnaTranscription.toRna("G") should be(Some("C"))
  }

  test("RNA complement of thymine is adenine") {
    RnaTranscription.toRna("T") should be(Some("A"))
  }

  test("RNA complement of adenine is uracil") {
    RnaTranscription.toRna("A") should be(Some("U"))
  }

  test("RNA complement") {
    RnaTranscription.toRna("ACGTGGTCTTAA") should be(Some("UGCACCAGAAUU"))
  }

  test("invalid RNA") {
    RnaTranscription.toRna("ABCD") should be(None)
  }
}
