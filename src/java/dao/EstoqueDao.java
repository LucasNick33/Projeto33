package dao;

import util.VariaveisGlobais;
import beans.Estoque;
import beans.ItemVenda;
import beans.Produto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.StringUtils;

public class EstoqueDao {

    private Estoque estoque;
    private String mensagem;

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean inserir(Produto produto) {
        estoque.setId(Calendar.getInstance().getTimeInMillis());
        estoque.setIdProduto(produto.getId());
        estoque.setNome(StringUtils.padronizar(estoque.getNome()));
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(estoque);
            t.commit();
            mensagem = "Estoque registrado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if (ex.toString().contains("ConstraintViolationException")) {
                mensagem = "Esse produto já possui registro pra esse estoque!";
            } else {
                mensagem = "Erro ao registrar estoque!";
            }
        }
        return false;
    }

    public Boolean atualizar() {
        estoque.setNome(StringUtils.padronizar(estoque.getNome()));
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(estoque);
            t.commit();
            mensagem = "Estoque atualizado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if (ex.toString().contains("ConstraintViolationException")) {
                mensagem = "Esse produto já possui registro pra esse estoque!";
            } else {
                mensagem = "Erro ao atualizar estoque!";
            }
        }
        return false;
    }

    public Boolean excluir() {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.delete(estoque);
            t.commit();
            mensagem = "Estoque excluído com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            mensagem = "Erro ao excluir estoque!";
        }
        return false;
    }

    public List<Estoque> listar(Produto produto) {
        estoque.setNome(StringUtils.padronizar(estoque.getNome()));
        Session s = BaseDao.getConexao();
        List<Estoque> estoques = new ArrayList<>();
        try {
            Query query = s.createQuery("SELECT Estoque FROM Estoque e INNER JOIN Produto p WHERE e.idProduto = ? AND e.nome like ?");
            query.setParameter(0, produto.getId());
            query.setParameter(1, estoque.getNome());
            estoques = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estoques;
    }

    public List<String> listarNomes() {
        Session s = BaseDao.getConexao();
        List<String> estoques = new ArrayList<>();
        try {
            estoques = s.createQuery("SELECT nome FROM Estoque GROUP BY nome").list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estoques;
    }

    public Boolean retirarDoEstoque(ItemVenda item) {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            Query query = s.createQuery("UPDATE Estoque SET quantidade = (quantidade - ?) WHERE idProduto = ? AND nome = ?");
            query.setParameter(0, item.getQuantidade());
            query.setParameter(1, item.getIdProduto());
            query.setParameter(2, VariaveisGlobais.usuario.getEstoque());
            query.executeUpdate();
            t.commit();
            return true;
        } catch (Exception ex) {
            if(t != null){
                t.rollback();
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean adicionarAoEstoque(ItemVenda item) {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            Query query = s.createQuery("UPDATE Estoque SET quantidade = (quantidade + ?) WHERE idProduto = ? AND nome = ?");
            query.setParameter(0, item.getQuantidade());
            query.setParameter(1, item.getIdProduto());
            query.setParameter(2, VariaveisGlobais.usuario.getEstoque());
            query.executeUpdate();
            t.commit();
            return true;
        } catch (Exception ex) {
            if(t != null){
                t.rollback();
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
