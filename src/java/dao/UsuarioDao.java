package dao;

import beans.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDao {

    private Usuario usuario;
    private String mensagem;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean inserir() {
        Session s = BaseDao.getConexao().openSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(usuario);
            t.commit();
            mensagem = "Usuário cadastrado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            mensagem = "Erro ao cadastrar usuário!";
            return false;
        } finally {
            s.close();
        }
    }

    public Boolean atualizar() {
        Session s = BaseDao.getConexao().openSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(usuario);
            t.commit();
            mensagem = "Usuário atualizado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            mensagem = "Erro ao atualizar usuário!";
            return false;
        } finally {
            s.close();
        }
    }

    public List<Usuario> listar() {
        Session s = BaseDao.getConexao().openSession();
        List<Usuario> usuarios = null;
        try {
            usuarios = (List<Usuario>) s.createQuery("FROM Usuario").list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.close();
        }
        return usuarios;
    }

    public List<Usuario> listar(String nome) {
        nome = "%" + nome.trim() + "%";
        Session s = BaseDao.getConexao().openSession();
        List<Usuario> usuarios = null;
        try {
            Query query = s.createQuery("FROM Usuario u WHERE u.nome like ?");
            query.setParameter(0, nome);
            usuarios = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.close();
        }
        return usuarios;
    }

}
