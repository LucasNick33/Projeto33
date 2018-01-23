package dao;

import beans.Pagamento;
import beans.Venda;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rn.VariaveisGlobais;

public class PagamentoDao {
    
    private final VariaveisGlobais variaveisGlobais;
    private Pagamento pagamento;

    public PagamentoDao(VariaveisGlobais variaveisGlobais) {
        this.variaveisGlobais = variaveisGlobais;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public Boolean inserir(){
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.save(pagamento);
            t.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if(t != null){
                t.rollback();
            }
        }
        return false;
    }
    
    public Boolean atualizar(){
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.update(pagamento);
            t.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if(t != null){
                t.rollback();
            }
        }
        return false;
    }
    
    public List<Pagamento> listar(Venda venda){
        Session s = variaveisGlobais.getBd().getConexao();
        List<Pagamento> pagamentos = new ArrayList<>();
        try{
            Query query = s.createQuery("SELECT Pagamento FROM Pagamento INNER JOIN Venda v WHERE v.id = ?");
            query.setParameter(0, venda.getId());
            pagamentos = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    return pagamentos;
    }
    
    public List<String> listarTipos(){
        Session s = variaveisGlobais.getBd().getConexao();
        List<String> pagamentos = new ArrayList<>();
        try{
            Query query = s.createQuery("SELECT p.tipo FROM Pagamento p GROUP BY p.tipo ORDER BY p.tipo");
            pagamentos = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    return pagamentos;
    }
    
}
