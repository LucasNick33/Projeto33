package dao;

import beans.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.StringUtils;

public class ProdutoDao {
    
    private Produto produto;
    private String mensagem;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public Boolean inserir(){
        produto.setCategoria(StringUtils.padronizar(produto.getCategoria()));
        produto.setMarca(StringUtils.padronizar(produto.getMarca()));
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.save(produto);
            t.commit();
            mensagem = "Produto cadastrado com sucesso!";
            return true;
        } catch (Exception ex) {
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro produto cadastrado com esse nome e marca!";
            } else {
                mensagem = "Erro ao cadastrar produto!";
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Boolean atualizar(){
        produto.setCategoria(StringUtils.padronizar(produto.getCategoria()));
        produto.setMarca(StringUtils.padronizar(produto.getMarca()));
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.update(produto);
            t.commit();
            mensagem = "Produto atualizado com sucesso!";
            return true;
        } catch (Exception ex) {
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro produto cadastrado com esse nome e marca!";
            } else {
                mensagem = "Erro ao atualizado produto!";
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<Produto> listar(){
        Session s = BaseDao.getConexao();
        String nome = "%" + (produto.getNome() == null ? "" : produto.getNome().trim()) + "%";
        String marca = "%" + (produto.getMarca() == null ? "" : produto.getMarca().trim()) + "%";
        String categoria = "%" + (produto.getCategoria() == null ? "" : produto.getCategoria().trim()) + "%";
        List<Produto> produtos = new ArrayList<>();
        try{
            Query query = s.createQuery("FROM Produto p WHERE p.nome like ? AND p.marca like ? AND p.categoria like ?");
            query.setParameter(0, nome);
            query.setParameter(1, marca);
            query.setParameter(2, categoria);
            produtos = query.list();
        } catch (Exception ex){
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtos;
    }
    
}
