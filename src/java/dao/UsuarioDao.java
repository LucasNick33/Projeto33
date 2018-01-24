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

    public UsuarioDao(VariaveisGlobais variaveisGlobais){
        this.variaveisGlobais = variaveisGlobais;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean inserir() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_USUARIO)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de usuário!");
            return false;
        }
        usuario.setId(Calendar.getInstance().getTimeInMillis());
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(usuario);
            t.commit();
            variaveisGlobais.setMensagem("Usuário cadastrado com sucesso!");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                variaveisGlobais.setMensagem("Há outro usuário cadastrado com esse nome!");
            } else {
                variaveisGlobais.setMensagem("Erro ao cadastrar usuário!");
            }
        }
        return false;
    }

    public Boolean atualizar() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_USUARIO)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de usuário!");
            return false;
        }
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(usuario);
            t.commit();
            variaveisGlobais.setMensagem("Usuário atualizado com sucesso!");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                variaveisGlobais.setMensagem("Há outro usuário cadastrado com esse nome!");
            } else {
                variaveisGlobais.setMensagem("Erro ao atualizar usuário!");
            }
        }
        return false;
    }

    public List<Usuario> listar() {
        usuario.setNome(usuario.getNome() == null ? "" : usuario.getNome());
        String nome = "%" + usuario.getNome().trim() + "%";
        usuario.setAtivo(usuario.getAtivo() == null ? true : usuario.getAtivo());
        Session s = variaveisGlobais.getBd().getConexao();
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
        Session s = variaveisGlobais.getBd().getConexao();
        List<Usuario> usuarios;
        try {
            Query query = s.createQuery("FROM Usuario u WHERE u.nome = ? and u.senha = ?");
            query.setParameter(0, usuario.getNome());
            query.setParameter(1, usuario.getSenha());
            usuarios = query.list();
            if(!usuarios.isEmpty()){
                variaveisGlobais.setUsuario(usuarios.get(0));
                variaveisGlobais.getUsuario().setLogado(true);
                //variaveisGlobais.getSessao().redirectToIndex();
                return true;
            } else {
                variaveisGlobais.setMensagem("Nome ou senha incorreto(s)!");
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
