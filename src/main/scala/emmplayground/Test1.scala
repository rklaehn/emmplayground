package emmplayground

import emm._
import emm.compat.cats._
import cats.implicits._
import emm.effects.Lifter

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
//
//object Foo {
//
//  import Test1.E
//
//  val ll = implicitly[Lifter[List[_], E]]
//  val ol = implicitly[Lifter[Option[_], E]]
//  val fl = implicitly[Lifter[Future[_], E]]
//}

object Test1 extends App {
//
//  implicit val i1 = Foo.ll
//  implicit val i2 = Foo.ol
//  implicit val i3 = Foo.fl

  type E = Future |: Option |: List |: Base

  readName.liftM[E]

  def readItems: List[String] = List("a", "b", "c")
  def readConfig: Option[String] = Some("on")
  def readName: Future[String] = Future("foo")
  def readFoo: Option[List[String]] = Some(Nil)
  def readFoo2: Future[List[String]] = Future(Nil)
  def log(msg: String): Future[Unit] = Future { println(msg) }

  def effect: Emm[E, String] = for {
    items <- readItems.liftM[E]
    config <- readConfig.liftM[E]
    first <- readName.liftM[E]
    last <- readName.liftM[E]
    x <- readFoo.liftM[E]
    x <- readFoo2.liftM[E]
    name <- (if ((first.length * last.length) < 20) Some(s"$first $last") else None).liftM[E]
    _ <- log(s"successfully read in $name $config $items").liftM[E]
  } yield name

  val effect2: Emm[E, String] = for {
    items <- readItems.liftM[E]
    config <- readConfig.liftM[E]
    first <- readName.liftM[E]
    _ <- log(s"$items").liftM[E]
  } yield items

  val effect3 = effect2.expand.expand.map { x => println(x); x }.collapse.collapse

  val result: Option[List[String]] = Await.result(effect.run, 1.second)
  println(result)
}
