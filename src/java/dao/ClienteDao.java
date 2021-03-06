
package dao;

import beans.Cliente;
import beans.Usuario;
import java.math.BigDecimal;
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

public class ClienteDao {
    
    private final VariaveisGlobais variaveisGlobais;
    private Cliente cliente;

    public ClienteDao(VariaveisGlobais variaveisGlobais){
        this.variaveisGlobais = variaveisGlobais;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Boolean inserir() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_CLIENTE)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de cliente!");
            return false;
        }
        cliente.setId(Calendar.getInstance().getTimeInMillis());
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.save(cliente);
            t.commit();
            variaveisGlobais.setMensagem("Cliente cadastrado com sucesso!");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                variaveisGlobais.setMensagem("Há outro cliente cadastrado com esse nome!");
            } else {
                variaveisGlobais.setMensagem("Erro ao cadastrar cliente!");
            }
        }
        return false;
    }

    public Boolean atualizar() {
        if(Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.CADASTRO_CLIENTE)){
            variaveisGlobais.setMensagem("Usuário não tem permissão para cadastro de cliente!");
            return false;
        }
        Session s = variaveisGlobais.getBd().getConexao();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(cliente);
            t.commit();
            variaveisGlobais.setMensagem("Cliente atualizado com sucesso!");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if (t != null) {
                t.rollback();
            }
            if(ex.toString().contains("ConstraintViolationException")){
                variaveisGlobais.setMensagem("Há outro cliente cadastrado com esse nome!");
            } else {
                variaveisGlobais.setMensagem("Erro ao atualizar cliente!");
            }
        }
        return false;
    }
    
    public List<Usuario> listar() {
        cliente.setNome(cliente.getNome() == null ? "" : cliente.getNome());
        String nome = "%" + cliente.getNome().trim() + "%";
        cliente.setAtivo(cliente.getAtivo() == null ? true : cliente.getAtivo());
        Session s = variaveisGlobais.getBd().getConexao();
        List<Usuario> clientes = new ArrayList<>();
        try {
            Query query = s.createQuery("FROM Cliente c WHERE c.nome like ? AND c.ativo = ? ORDER BY c.nome");
            query.setParameter(0, nome);
            query.setParameter(1, cliente.getAtivo());
            clientes = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }
    
    public BigDecimal debto(){
        BigDecimal debto = BigDecimal.ZERO;
        Session s = variaveisGlobais.getBd().getConexao();
        try {
            Query query = s.createQuery("SELECT SUM(p.valor) FROM Pagamento p INNER JOIN Venda v INNER JOIN Cliente c WHERE v.ativo = true AND c.id = ?");
            query.setParameter(0, cliente.getId());
            List<BigDecimal> lista = query.list();
            BigDecimal valorPago = null;
            if(!lista.isEmpty()){
                valorPago = lista.get(0);
            }
            query = s.createQuery("SELECT SUM(v.valor - (v.valor * v.porcentagemDesconto / 100)) FROM Venda v INNER JOIN Cliente c WHERE v.ativo = true AND c.id = ?");
            query.setParameter(0, cliente.getId());
            lista = query.list();
            BigDecimal valorComprado = null;
            if(!lista.isEmpty()){
                valorComprado = lista.get(0);
            }
            if(valorPago != null && valorComprado != null){
                debto = valorComprado.subtract(valorPago);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return debto;
    }
    
}
