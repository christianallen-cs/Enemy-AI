package game.lo4_data_structures.linkedlist

class QueueClass[A]() {

  var front: LinkedListNode[A] = null
  var back: LinkedListNode[A] = null

  def inQueue(a: A): Unit = {
    if (back == null) {
      this.back = new LinkedListNode[A](a, null)
      this.front = this.back
    } else {
      this.back.next = new LinkedListNode[A](a, null)
      this.back = this.back.next
    }
  }

  def unQueue(): A = {
    val finish = this.front.value
    this.front = this.front.next
    if (this.front == null) {
      this.back = null
    }
    finish
  }
}

