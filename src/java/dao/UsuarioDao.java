package dao;

import beans.Usuario;
import java.util.ArrayList;
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
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro usuário cadastrado com esse nome!";
            } else {
                mensagem = "Erro ao cadastrar usuário!";
            }
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
        List<Usuario> usuarios = new ArrayList<>();
        try {
            usuarios = (List<Usuario>) s.createQuery("FROM Usuario").list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.close();
        }
        return usuarios;
    }

    public List<Usuario> listarPorNome() {
        usuario.setNome(usuario.getNome() == null ? "" : usuario.getNome());
        String nome = "%" + usuario.getNome().trim() + "%";
        Session s = BaseDao.getConexao().openSession();
        List<Usuario> usuarios = new ArrayList<>();
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

    public Boolean login(){
        Session s = BaseDao.getConexao().openSession();
        boolean login = false;
        List<Usuario> usuarios = null;
        try {
            Query query = s.createQuery("FROM Usuario u WHERE u.nome = ? and u.senha = ?");
            query.setParameter(0, usuario.getNome());
            query.setParameter(1, usuario.getSenha());
            usuarios = query.list();
            if(!usuarios.isEmpty()){
                login = true;
            } else {
                mensagem = "Nome ou senha incorreto(s)!";
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.close();
        }
        return login;
    }
    
}
