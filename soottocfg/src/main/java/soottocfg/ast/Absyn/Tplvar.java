package soottocfg.ast.Absyn; // Java Package generated by the BNF Converter.

public class Tplvar extends TupleAcc {
  public final SpecExp specexp_;
  public final String ident_;
  public Tplvar(SpecExp p1, String p2) { specexp_ = p1; ident_ = p2; }

  public <R,A> R accept(soottocfg.ast.Absyn.TupleAcc.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof soottocfg.ast.Absyn.Tplvar) {
      soottocfg.ast.Absyn.Tplvar x = (soottocfg.ast.Absyn.Tplvar)o;
      return this.specexp_.equals(x.specexp_) && this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.specexp_.hashCode())+this.ident_.hashCode();
  }


}
