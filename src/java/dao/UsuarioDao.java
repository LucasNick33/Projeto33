package dao;

import beans.Usuario;
import java.util.List;
import org.hibernate.Query;

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
    
    public Boolean inserir(){
        try{
            BaseDao.getConexao().save(usuario);
            mensagem = "Usuário cadastrado com sucesso!";
            return true;
        } catch(Exception ex){
            mensagem = "Erro ao cadastrar usuário!";
            return false;
        }
    }
    
    public Boolean atualizar(){
        try{
            BaseDao.getConexao().update(usuario);
            mensagem = "Usuário atualizado com sucesso!";
            return true;
        } catch(Exception ex){
            mensagem = "Erro ao atualizar usuário!";
            return false;
        }
    }
    
    public List<Usuario> listar(){
        try{
            return (List<Usuario>) BaseDao.getConexao().createQuery("SELECT * FROM Usuario").list();
        } catch(Exception ex){
            return null;
        }
    }
    
    public List<Usuario> listarPorNome (String nome) {
        try{
            nome = "%" + nome.trim() + "%";
            Query query = BaseDao.getConexao().createQuery("SELECT * FROM Usuario u WHERE u.nome like ?");
            query.setParameter(0, nome);
            return query.list();
        } catch(Exception ex){
            return null;
        }
    }
    
}
