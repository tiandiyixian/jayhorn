package soottocfg.ast.Absyn; // Java Package generated by the BNF Converter.

public abstract class JumpStm implements java.io.Serializable {
  public abstract <R,A> R accept(JumpStm.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(soottocfg.ast.Absyn.Glabel p, A arg);
    public R visit(soottocfg.ast.Absyn.Return p, A arg);
    public R visit(soottocfg.ast.Absyn.ReturnExp p, A arg);

  }

}