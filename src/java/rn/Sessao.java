
package rn;

import beans.Cliente;
import beans.ItemVenda;
import beans.Pagamento;
import beans.Produto;
import beans.Usuario;
import beans.Venda;
import dao.ClienteDao;
import dao.EstoqueDao;
import dao.ItemVendaDao;
import dao.PagamentoDao;
import dao.ProdutoDao;
import dao.UsuarioDao;
import dao.VendaDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class Sessao {
    
    private static final List<Sessao> SESSOES;
    
    static{
        SESSOES = new ArrayList<>();
    }
    
    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private ProdutoDao produtoDao;
    private VendaDao vendaDao;
    private ItemVendaDao itemDao;
    private PagamentoDao pagamentoDao;
    private EstoqueDao estoqueDao;
    private RNVenda rnVenda;
    private String id;

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public ProdutoDao getProdutoDao() {
        return produtoDao;
    }

    public void setProdutoDao(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public VendaDao getVendaDao() {
        return vendaDao;
    }

    public void setVendaDao(VendaDao vendaDao) {
        this.vendaDao = vendaDao;
    }

    public ItemVendaDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemVendaDao itemDao) {
        this.itemDao = itemDao;
    }

    public PagamentoDao getPagamentoDao() {
        return pagamentoDao;
    }

    public void setPagamentoDao(PagamentoDao pagamentoDao) {
        this.pagamentoDao = pagamentoDao;
    }

    public EstoqueDao getEstoqueDao() {
        return estoqueDao;
    }

    public void setEstoqueDao(EstoqueDao estoqueDao) {
        this.estoqueDao = estoqueDao;
    }

    public RNVenda getRnVenda() {
        return rnVenda;
    }

    public void setRnVenda(RNVenda rnVenda) {
        this.rnVenda = rnVenda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getSessao(String id){
        if(id != null && !id.isEmpty()){
            for(Sessao s : SESSOES){
                if(s.id.equals(id)){
                    copiar(s);
                    return;
                }
            }
        }
        novaSessao();
    }
    
    private void novaSessao(){
        this.id = String.valueOf(Calendar.getInstance().getTimeInMillis());
        
        usuarioDao = new UsuarioDao();
        usuarioDao.setUsuario(new Usuario());
        
        clienteDao = new ClienteDao();
        clienteDao.setCliente(new Cliente());
        
        produtoDao = new ProdutoDao();
        produtoDao.setProduto(new Produto());
        
        vendaDao = new VendaDao();
        vendaDao.setVenda(new Venda());
        
        itemDao = new ItemVendaDao();
        itemDao.setItem(new ItemVenda());
        
        pagamentoDao = new PagamentoDao();
        pagamentoDao.setPagamento(new Pagamento());
        
        estoqueDao = new EstoqueDao();
        rnVenda = new RNVenda(usuarioDao, clienteDao, vendaDao, itemDao, estoqueDao, pagamentoDao);
        
        SESSOES.add(this);
    }
    
    private void copiar(Sessao s){
        this.clienteDao = s.clienteDao;
        this.estoqueDao = s.estoqueDao;
        this.id = s.id;
        this.itemDao = s.itemDao;
        this.pagamentoDao = s.pagamentoDao;
        this.produtoDao = s.produtoDao;
        this.rnVenda = s.rnVenda;
        this.usuarioDao = s.usuarioDao;
        this.vendaDao = s.vendaDao;
    }
    
}
