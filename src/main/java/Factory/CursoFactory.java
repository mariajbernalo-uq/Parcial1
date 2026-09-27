package Factory;

import Model.Curso;
import Model.CursoPersonalizado;
import Model.Estado;

public abstract class CursoFactory {

    public abstract Curso crearCurso();
}