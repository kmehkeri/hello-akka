import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

object HelloAkka {
  def main(args: Array[String]) {

    implicit val actorSystem = ActorSystem("hello-system")
    implicit val actorMaterializer = ActorMaterializer()

    val route = pathSingleSlash {
      get {
        complete {
          "Hello Akka!"
        }
      }
    }

    Http().bindAndHandle(route, "localhost", 8080)
    println("Server started at localhost:8080")
  }
}
