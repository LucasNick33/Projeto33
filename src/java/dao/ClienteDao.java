
package dao;

import beans.Cliente;
import beans.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDao {
    
    private Cliente cliente;
    private String mensagem;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
            s.save(cliente);
            t.commit();
            mensagem = "Cliente cadastrado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro cliente cadastrado com esse nome!";
            } else {
                mensagem = "Erro ao cadastrar cliente!";
            }
        }
        return false;
    }

    public Boolean atualizar() {
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(cliente);
            t.commit();
            mensagem = "Cliente atualizado com sucesso!";
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                mensagem = "Há outro cliente cadastrado com esse nome!";
            } else {
                mensagem = "Erro ao atualizar cliente!";
            }
        }
        return false;
    }

    public List<Cliente> listar() {
        Session s = BaseDao.getConexao();
        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = s.createQuery("FROM Cliente").list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    public List<Usuario> listarPorNome() {
        cliente.setNome(cliente.getNome() == null ? "" : cliente.getNome());
        String nome = "%" + cliente.getNome().trim() + "%";
        Session s = BaseDao.getConexao();
        List<Usuario> clientes = new ArrayList<>();
        try {
            Query query = s.createQuery("FROM Cliente c WHERE c.nome like ?");
            query.setParameter(0, nome);
            clientes = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }
    
}
