package soottocfg.ast.Absyn; // Java Package generated by the BNF Converter.

public abstract class FieldDeclaration implements java.io.Serializable {
  public abstract <R,A> R accept(FieldDeclaration.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(soottocfg.ast.Absyn.Dvar p, A arg);
    public R visit(soottocfg.ast.Absyn.UDvar p, A arg);

  }

}