import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


/** @version created manually **/
class BstTest extends AnyFlatSpec with Matchers {

  it should "retain data" in {
    Bst(4).value should equal(4)
  }

  it should "retain data - char" in {
    Bst('d').value should equal('d')
  }

  it should "insert less" in {
    Bst(4).insert(2).left.get.value should equal(2)
  }

  it should "insert less - char" in {
    Bst('d').insert('a').left.get.value should equal('a')
  }

  it should "insert same" in {
    Bst(4).insert(4).left.get.value should equal(4)
  }

  it should "insert greater than" in {
    Bst(4).insert(5).right.get.value should equal(5)
  }

  it should "handle complex tree - sort out of order list" in {
    val bst = Bst.fromList(List(4, 2, 6, 1, 3, 7, 5))
    Bst.toList(bst) should equal((1 to 7).toList)

    bst.value should equal(4)
    bst.left.get.value should equal(2)
    bst.left.get.left.get.value should equal(1)
    bst.left.get.right.get.value should equal(3)
    bst.right.get.value should equal(6)
    bst.right.get.left.get.value should equal(5)
    bst.right.get.right.get.value should equal(7)
  }

  it should "iterating one element" in {
    Bst.toList(Bst(4)) should equal(List(4))
  }

  it should "iterating over smaller element" in {
    Bst.toList(Bst.fromList(List(4, 2))) should equal(List(2, 4))
  }

  it should "iterating over larger element" in {
    Bst.toList(Bst.fromList(List(4, 5))) should equal(List(4, 5))
  }

  it should "iterating over complex tree" in {
    Bst.toList(Bst.fromList(List(4, 2, 1, 3, 6, 7, 5))) should equal((1 to 7).toList)
  }

  it should "iterating over complex tree - chars" in {
    Bst.toList(Bst.fromList(List('d', 'b', 'a', 'c', 'f', 'g', 'e'))) should
      equal(('a' to 'g').toList)
  }
}
