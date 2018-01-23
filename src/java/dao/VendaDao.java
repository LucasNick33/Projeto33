package dao;

import beans.Venda;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rn.VariaveisGlobais;

public class VendaDao {

    private final VariaveisGlobais variaveisGlobais;
    private Venda venda;
    private Timestamp dataInicial;
    private Timestamp dataFinal;

    public VendaDao(VariaveisGlobais variaveisGlobais) {
        this.variaveisGlobais = variaveisGlobais;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Timestamp getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Timestamp dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Timestamp getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Timestamp dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Boolean inserir() {
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(venda);
            t.commit();
            variaveisGlobais.setMensagem("Venda inserida com sucesso!");
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            variaveisGlobais.setMensagem("Erro ao inserir venda!");
        }
        return true;
    }

    public Boolean atualizar() {
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(venda);
            t.commit();
            variaveisGlobais.setMensagem("Venda atualizada com sucesso!");
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            variaveisGlobais.setMensagem("Erro ao atualizar venda!");
        }
        return true;
    }

    public List<Venda> listar() {
        dataInicial = dataInicial == null ? new Timestamp(0l) : dataInicial;
        dataFinal = dataFinal == null ? new Timestamp(Long.MAX_VALUE) : dataFinal;
        venda.setAtivo(venda.getAtivo() == null ? true : venda.getAtivo());
        venda.setPago(venda.getPago() == null ? false : venda.getPago());
        Session s = variaveisGlobais.getBd().getConexao();
        List<Venda> vendas = new ArrayList<>();
        try {
            Query query;
            if (venda.getIdCliente() != null && venda.getIdUsuario() != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idCliente = ? AND v.idUsuario = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? AND v.pago = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdCliente());
                query.setParameter(1, venda.getIdUsuario());
                query.setParameter(2, dataInicial);
                query.setParameter(3, dataFinal);
                query.setParameter(4, venda.getAtivo());
                query.setParameter(5, venda.getPago());
            } else if (venda.getIdCliente() != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idCliente = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? AND v.pago = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdCliente());
                query.setParameter(1, dataInicial);
                query.setParameter(2, dataFinal);
                query.setParameter(3, venda.getAtivo());
                query.setParameter(4, venda.getPago());
            } else if (venda.getIdUsuario() != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idUsuario = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? AND v.pago = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdUsuario());
                query.setParameter(1, dataInicial);
                query.setParameter(2, dataFinal);
                query.setParameter(3, venda.getAtivo());
                query.setParameter(4, venda.getPago());
            } else {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.dataVenda BETWEEN ? AND ? AND v.ativo = ? AND v.pago = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, dataInicial);
                query.setParameter(1, dataFinal);
                query.setParameter(2, venda.getAtivo());
                query.setParameter(3, venda.getPago());
            }
            vendas = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendas;
    }

}
