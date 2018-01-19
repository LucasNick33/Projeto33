package dao;

import beans.Usuario;
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

public class UsuarioDao {

    private final VariaveisGlobais variaveisGlobais;
    private Usuario usuario;
    private String mensagem;

    public UsuarioDao(VariaveisGlobais variaveisGlobais){
        this.variaveisGlobais = variaveisGlobais;
    }
    
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
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_USUARIO)){
            mensagem = "Usuário não tem permissão para cadastro de usuário!";
            return false;
        }
        usuario.setId(Calendar.getInstance().getTimeInMillis());
        Session s = BaseDao.getConexao();
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
        }
        return false;
    }

    public Boolean atualizar() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_USUARIO)){
            mensagem = "Usuário não tem permissão para cadastro de usuário!";
            return false;
        }
        Session s = BaseDao.getConexao();
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
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro usuário cadastrado com esse nome!";
            } else {
                mensagem = "Erro ao atualizar usuário!";
            }
        }
        return false;
    }

    public List<Usuario> listar() {
        usuario.setNome(usuario.getNome() == null ? "" : usuario.getNome());
        String nome = "%" + usuario.getNome().trim() + "%";
        usuario.setAtivo(usuario.getAtivo() == null ? true : usuario.getAtivo());
        Session s = BaseDao.getConexao();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Query query = s.createQuery("FROM Usuario u WHERE u.nome like ? AND u.ativo = ? ORDER BY u.nome");
            query.setParameter(0, nome);
            query.setParameter(1, usuario.getAtivo());
            usuarios = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    public Boolean login(){
        Session s = BaseDao.getConexao();
        boolean login = false;
        List<Usuario> usuarios = null;
        try {
            Query query = s.createQuery("FROM Usuario u WHERE u.nome = ? and u.senha = ?");
            query.setParameter(0, usuario.getNome());
            query.setParameter(1, usuario.getSenha());
            usuarios = query.list();
            if(!usuarios.isEmpty()){
                login = true;
                usuario = usuarios.get(0);
                usuario.setLogado(true);
            } else {
                mensagem = "Nome ou senha incorreto(s)!";
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }
    
}
