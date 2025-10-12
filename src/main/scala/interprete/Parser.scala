package interprete

import scala.collection.mutable.Stack
import modelo.*

import scala.annotation.tailrec
def interpretarExpresion(ecuacion: List[Token | Char]): Expresion = {
  val (res,_) = _interpretarExpresionRec(ecuacion)
  res
}
def _interpretarExpresionRec(tokens: List[Token | Char]): (Expresion, List[Token | Char]) = {
  def leerTermino(tokens: List[Token | Char]): (Expresion, List[Token | Char]) =
    val sinEspacios = tokens.dropWhile(_ == Token.SPACE)
    sinEspacios match {
    case Token.LPAR :: xs =>
      val (expr, resto) = _interpretarExpresionRec(xs)
      resto match {
        case Token.RPAR :: tail => (expr, tail)
        case _ => throw new IllegalArgumentException("Falta paréntesis de cierre")
      }
    case Token.LAMBDA :: (param: Char) :: Token.DOT :: xs =>
      val (cuerpo, resto) = _interpretarExpresionRec(xs)
      (Abstraccion(param, cuerpo), resto)
    case (v: Char) :: resto =>
      (Variable(v), resto)
    case _ =>
      throw new IllegalArgumentException("Token inesperado")
  }

  var (expr, resto) = leerTermino(tokens)

  while (resto.nonEmpty && resto.head != Token.RPAR) {
    val (nextExpr, tail) = leerTermino(resto)
    expr = Aplicacion(expr, nextExpr)
    resto = tail
  }

  (expr, resto)
}

def graficarArbol(expresion: Expresion, espacios: String = "", esIzquierdo: Boolean = true): Unit = {
  expresion match {
    case Variable(nombre) =>
      println(espacios + (if (esIzquierdo) "├──" else "└──") + s"$nombre")
    case Abstraccion(param, cuerpo) =>
      println(espacios + (if (esIzquierdo) "├──" else "└──") + s"Abstraccion")
      graficarArbol(Variable(param), espacios + (if (esIzquierdo) "│  " else "   "))
      graficarArbol(cuerpo, espacios + (if (esIzquierdo) "│  " else "   "), false)
    case Aplicacion(e1, e2) =>
      println(espacios + (if (esIzquierdo) "├──" else "└──") + "Aplicacion")
      graficarArbol(e1, espacios + (if (esIzquierdo) "│  " else "   "))
      graficarArbol(e2, espacios + (if (esIzquierdo) "│  " else "   "), false)
  }
}

def imprimirExpresion(expresion: Expresion): Unit = {
  def obtenerString(expresion: Expresion): String = {
    expresion match {
      case Variable(nombre) => nombre.toString
      case Abstraccion(param, cuerpo) =>
        "λ" + param + "." + obtenerString(cuerpo)
      case Aplicacion(e1, e2) =>
        obtenerString(e1) + " " + obtenerString(e2)
    }
  }
  println(obtenerString(expresion))
}