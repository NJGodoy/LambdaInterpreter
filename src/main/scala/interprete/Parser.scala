package interprete

import scala.collection.mutable.Stack
import modelo.*

import scala.annotation.tailrec
def interpretarExpresion(ecuacion: List[Token | Char]): Expresion = {
  val (res,_) = _interpretarExpresionRec(ecuacion)
  res
}
def _interpretarExpresionRec(tokens: List[Token | Char]): (Expresion, List[Token | Char]) = {
  tokens match {
    case Nil => throw new IllegalArgumentException("Token Invalido")
    case Token.LPAR :: xs =>
      val (e1, siguiente) = _interpretarExpresionRec(xs)
      val (e2, resto) = _interpretarExpresionRec(siguiente)
      (Aplicacion(e1, e2), resto.tail)
    case Token.LAMBDA :: (param : Char) :: Token.DOT :: xs =>
      val (cuerpo, resto) = _interpretarExpresionRec(xs)
      (Abstraccion(param, cuerpo), resto)
    case Token.SPACE :: xs =>
      _interpretarExpresionRec(xs)
    case (variable : Char) :: resto =>
      (Variable(variable), resto)
    case _ => throw new IllegalArgumentException("Argumento Invalido.")
  }
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