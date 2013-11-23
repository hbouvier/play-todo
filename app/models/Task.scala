package models

/**
 * Created with IntelliJ IDEA.
 * User: henri
 * Date: 2013-11-23
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String)
object Task {

  val task = {
    get[Long]("id") ~
      get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

  def all() : List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}
