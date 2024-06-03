package reductor
import modelo.*

def variablesLibres(expresion: Expresion): Set[Char] = {
  expresion match {
    case Variable(nombre) =>
      Set(nombre)
    case Abstraccion(param, cuerpo) =>
      variablesLibres(cuerpo) - param
    case Aplicacion(e1, e2) =>
      variablesLibres(e1) ++ variablesLibres(e2)
  }
}

def reducirCallByName(expresion: Expresion): Expresion = {
  expresion match {
    case Variable(nombre) => Variable(nombre)
    case Abstraccion(param, cuerpo) => Abstraccion(param, reducirCallByName(cuerpo))
    case Aplicacion(Abstraccion(param, cuerpo), e2) =>
      reducirCallByName(reemplazar(cuerpo, param, e2))
    case Aplicacion(e1, e2) =>
      val e1Reducido = reducirCallByName(e1)
      e1Reducido match {
        case Abstraccion(param, cuerpo) => reducirCallByName(Aplicacion(e1Reducido, e2))
        case _ => Aplicacion(e1Reducido, e2)
      }
  }
}
def reducirCallByValue(expresion: Expresion, steps: Int = 500): Expresion = {
  if (steps <= 0) {
    throw new RuntimeException("Reducción infinita detectada") //Tengo que tirar un error o devolver una expresion que indique que es resultado de red infinita
  } else {
    expresion match {
      case Variable(name) => Variable(name)
      case Abstraccion(param, cuerpo) => Abstraccion(param, reducirCallByValue(cuerpo, steps - 1))
      case Aplicacion(e1, e2) =>
        val e2Reducido = reducirCallByValue(e2, steps - 1)
        val e1Reducido = reducirCallByValue(e1, steps - 1)
        e1Reducido match {
          case Abstraccion(param, cuerpo) => reducirCallByValue(reemplazar(cuerpo, param, e2Reducido), steps - 1)
          case _ => Aplicacion(e1Reducido, e2Reducido)
        }
    }
  }
}

def reemplazar(expresion: Expresion, param: Char, expresion2: Expresion): Expresion = {
  expresion match {
    case Variable(nombre) if nombre == param => expresion2
    case Variable(nombre) => Variable(nombre)
    case Abstraccion(p, cuerpo) if p == param => Abstraccion(p, cuerpo)
    case Abstraccion(p, cuerpo) if variablesLibres(expresion2).contains(p) =>
      val nuevoParam = ('a' to 'z').filterNot(variablesLibres(cuerpo) ++ variablesLibres(expresion2)).head//Devuelve primer elemento que no este en FV de e1 ni e2
      Abstraccion(nuevoParam, reemplazar(convertirAlfa(cuerpo, p, nuevoParam), param, expresion2))
    case Abstraccion(p, cuerpo) =>
      Abstraccion(p, reemplazar(cuerpo, param, expresion2))
    case Aplicacion(e1, e2) =>
      Aplicacion(reemplazar(e1, param, expresion2), reemplazar(e2, param, expresion2))
  }
}

def convertirAlfa(expresion: Expresion, viejoParam: Char, nuevoParam: Char): Expresion = {
  expresion match {
    case Variable(nombre) if nombre == viejoParam => Variable(nuevoParam)
    case Variable(nombre) => Variable(nombre)
    case Abstraccion(param, cuerpo) if param == viejoParam =>
      Abstraccion(nuevoParam, convertirAlfa(cuerpo, viejoParam, nuevoParam))
    case Abstraccion(param, cuerpo) =>
      Abstraccion(param, convertirAlfa(cuerpo, viejoParam, nuevoParam))
    case Aplicacion(e1, e2) =>
      Aplicacion(convertirAlfa(e1, viejoParam, nuevoParam), convertirAlfa(e2, viejoParam, nuevoParam))
  }
}