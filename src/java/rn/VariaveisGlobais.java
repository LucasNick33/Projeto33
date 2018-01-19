package rn;

import beans.Usuario;
import dao.ClienteDao;
import dao.EstoqueDao;
import dao.ItemVendaDao;
import dao.PagamentoDao;
import dao.ProdutoDao;
import dao.UsuarioDao;
import dao.VendaDao;

public class VariaveisGlobais {
    
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private ProdutoDao produtoDao;
    private VendaDao vendaDao;
    private ItemVendaDao itemDao;
    private PagamentoDao pagamentoDao;
    private EstoqueDao estoqueDao;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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
    
}
