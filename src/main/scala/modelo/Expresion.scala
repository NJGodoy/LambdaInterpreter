package modelo
sealed trait Expresion
case class Variable(valor: Char) extends Expresion
case class Abstraccion(param: Char, cuerpo: Expresion) extends Expresion
case class Aplicacion(e1: Expresion, e2: Expresion) extends Expresion
