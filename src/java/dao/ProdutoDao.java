package dao;

import beans.Produto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rn.Permissao;
import rn.VariaveisGlobais;
import util.StringUtils;

public class ProdutoDao {

    private final VariaveisGlobais variaveisGlobais;
    private Produto produto;

    public ProdutoDao(VariaveisGlobais variaveisGlobais){
        this.variaveisGlobais = variaveisGlobais;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Boolean inserir() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_PRODUTO)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de produto!");
            return false;
        }
        produto.setId(Calendar.getInstance().getTimeInMillis());
        produto.setNome(StringUtils.padronizar(produto.getNome()));
        produto.setCategoria(StringUtils.padronizar(produto.getCategoria()));
        produto.setMarca(StringUtils.padronizar(produto.getMarca()));
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(produto);
            t.commit();
            variaveisGlobais.setMensagem("Produto cadastrado com sucesso!");
            return true;
        } catch (Exception ex) {
            if (t != null) {
                t.rollback();
            }
            if (ex.toString().contains("ConstraintViolationException")) {
                variaveisGlobais.setMensagem("Há outro produto cadastrado com esse nome e marca ou código!");
            } else {
                variaveisGlobais.setMensagem("Erro ao cadastrar produto!");
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean atualizar() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_PRODUTO)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de produto!");
            return false;
        }
        produto.setNome(StringUtils.padronizar(produto.getNome()));
        produto.setCategoria(StringUtils.padronizar(produto.getCategoria()));
        produto.setMarca(StringUtils.padronizar(produto.getMarca()));
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(produto);
            t.commit();
            variaveisGlobais.setMensagem("Produto atualizado com sucesso!");
            return true;
        } catch (Exception ex) {
            if (t != null) {
                t.rollback();
            }
            if (ex.toString().contains("ConstraintViolationException")) {
                variaveisGlobais.setMensagem("Há outro produto cadastrado com esse nome e marca ou código!");
            } else {
                variaveisGlobais.setMensagem("Erro ao atualizado produto!");
            }
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Produto> listar() {
        Session s = variaveisGlobais.getBd().getConexao();
        List<Produto> produtos = new ArrayList<>();
        produto.setAtivo(produto.getAtivo() == null ? true : produto.getAtivo());
        try {
            if (produto.getId() != null) {
                Query query = s.createQuery("FROM Produto p WHERE p.id = ? AND p.ativo = ? ORDER BY p.categoria, p.marca, p.nome");
                query.setParameter(0, produto.getId());
                query.setParameter(1, produto.getAtivo());
                produtos = query.list();
            } else {
                String nome = "%" + (produto.getNome() == null ? "" : StringUtils.padronizar(produto.getNome())) + "%";
                String marca = "%" + (produto.getMarca() == null ? "" : StringUtils.padronizar(produto.getMarca())) + "%";
                String categoria = "%" + (produto.getCategoria() == null ? "" : StringUtils.padronizar(produto.getCategoria())) + "%";
                Query query = s.createQuery("FROM Produto p WHERE p.nome like ? AND p.marca like ? AND p.categoria like ? AND p.ativo = ? ORDER BY p.categoria, p.marca, p.nome");
                query.setParameter(0, nome);
                query.setParameter(1, marca);
                query.setParameter(2, categoria);
                query.setParameter(3, produto.getAtivo());
                produtos = query.list();
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtos;
    }

    public List<String> listarCategorias() {
        Session s = variaveisGlobais.getBd().getConexao();
        List<String> categorias = new ArrayList<>();
        try {
            Query query = s.createQuery("SELECT p.categoria FROM Produto p GROUP BY p.categoria ORDER BY p.categoria DESC");
            categorias = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorias;
    }

    public List<String> listarMarcas() {
        Session s = variaveisGlobais.getBd().getConexao();
        List<String> marcas = new ArrayList<>();
        try {
            Query query = s.createQuery("SELECT p.marca FROM Produto p GROUP BY p.marca ORDER BY p.marca DESC");
            marcas = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marcas;
    }

}
