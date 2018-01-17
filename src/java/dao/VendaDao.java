package dao;

import beans.Cliente;
import beans.Usuario;
import beans.Venda;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VendaDao {

    private Venda venda;
    private String mensagem;
    private Timestamp dataInicial;
    private Timestamp dataFinal;
    private Boolean ativo;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean inserir() {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(venda);
            t.commit();
            mensagem = "Venda inserida com sucesso!";
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            mensagem = "Erro ao inserir venda!";
        }
        return true;
    }

    public Boolean atualizar() {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(venda);
            t.commit();
            mensagem = "Venda atualizada com sucesso!";
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            mensagem = "Erro ao atualizar venda!";
        }
        return true;
    }

    public List<Venda> listar() {
        dataInicial = dataInicial == null ? new Timestamp(0l) : dataInicial;
        dataFinal = dataFinal == null ? new Timestamp(Long.MAX_VALUE) : dataFinal;
        ativo = ativo == null ? false : ativo;
        Session s = BaseDao.getConexao();
        List<Venda> vendas = new ArrayList<>();
        try {
            Query query;
            if (venda.getIdCliente() != null && venda.getIdUsuario() != null && dataInicial != null && dataFinal != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idCliente = ? AND v.idUsuario = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdCliente());
                query.setParameter(1, venda.getIdUsuario());
                query.setParameter(2, dataInicial);
                query.setParameter(3, dataFinal);
                query.setParameter(4, ativo);
            } else if (venda.getIdCliente() != null && dataInicial != null && dataFinal != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idCliente = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdCliente());
                query.setParameter(1, dataInicial);
                query.setParameter(2, dataFinal);
                query.setParameter(3, ativo);
            } else if (venda.getIdUsuario() != null && dataInicial != null && dataFinal != null) {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.idUsuario = ? AND v.dataVenda BETWEEN ? AND ? AND v.ativo = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, venda.getIdUsuario());
                query.setParameter(1, dataInicial);
                query.setParameter(2, dataFinal);
                query.setParameter(3, ativo);
            } else {
                query = s.createQuery("SELECT Venda FROM Venda v WHERE v.dataVenda BETWEEN ? AND ? AND v.ativo = ? ORDER BY v.dataVenda, v.valor");
                query.setParameter(0, dataInicial);
                query.setParameter(1, dataFinal);
                query.setParameter(2, ativo);
            }
            vendas = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendas;
    }

}
