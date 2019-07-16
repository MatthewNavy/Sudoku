package sudoku

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.MouseEvent
import scalafx.scene.text.Font

object Main extends JFXApp {
  val w = 800
  val h = 800
  val wSpacing = w / 9
  val hSpacing = h / 9
  val wOffset = w / 24
  val hOffset = h / 15

  // DEBUG
  //val grid = Array.tabulate(9)(i => Array.tabulate(9)(j => new Cell(i, j, i + j + "", false)))
  //grid.foreach(a => a.foreach(c => println(c.value)))

  stage = new JFXApp.PrimaryStage
  {
    title = "Sudoku"
    scene = new Scene(w, h) {
      val canvas = new Canvas(w, h)
      val gc = canvas.getGraphicsContext2D
      content = canvas

      gc.lineWidth_=(3)

      // Outside boundaries
      gc.strokeLine(0,0,w,0)
      gc.strokeLine(0,0,0,h)
      gc.strokeLine(w,0,w,h)
      gc.strokeLine(0,h,w,h)

      // Box lines
      gc.strokeLine(w / 3,0,w / 3,h)
      gc.strokeLine(w / 3 * 2, 0, w / 3 * 2,h)
      gc.strokeLine(0, h / 3, w, h / 3)
      gc.strokeLine(0, h / 3 * 2, w, h / 3 * 2)

      // Row and column lines
      gc.lineWidth_=(1)
      gc.strokeLine(w / 9, 0, w / 9, h)
      gc.strokeLine(w / 9 * 2, 0, w / 9 * 2, h)
      gc.strokeLine(w / 9 * 4, 0, w / 9 * 4, h)
      gc.strokeLine(w / 9 * 5, 0, w / 9 * 5, h)
      gc.strokeLine(w / 9 * 7, 0, w / 9 * 7, h)
      gc.strokeLine(w / 9 * 8, 0, w / 9 * 8, h)
      gc.strokeLine(0, h / 9, w, h / 9)
      gc.strokeLine(0, h / 9 * 2, w, h / 9 * 2)
      gc.strokeLine(0, h / 9 * 4, w, h / 9 * 4)
      gc.strokeLine(0, h / 9 * 5, w, h / 9 * 5)
      gc.strokeLine(0, h / 9 * 7, w, h / 9 * 7)
      gc.strokeLine(0, h / 9 * 8, w, h / 9 * 8)

      val grid = new Grid(1, stage, wSpacing, hSpacing, wOffset, hOffset)

      gc.font_=(new Font("Times Roman", 32))
      grid.drawBoard(gc)

      onMousePressed = (me: MouseEvent) =>
      {
        grid.cellClicked(me.x, me.y, gc)
      }

    }
  }
}


