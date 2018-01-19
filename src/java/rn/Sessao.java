package rn;

import beans.Cliente;
import beans.Estoque;
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
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
public class Sessao {

    private static final List<Sessao> SESSOES;

    static {
        SESSOES = new ArrayList<>();
    }

    private RNVenda rnVenda;
    private String id;
    private VariaveisGlobais variaveisGlobais;

    public RNVenda getRnVenda() {
        return rnVenda;
    }

    public void setRnVenda(RNVenda rnVenda) {
        this.rnVenda = rnVenda;
    }

    public VariaveisGlobais getVariaveisGlobais() {
        return variaveisGlobais;
    }

    public void setVariaveisGlobais(VariaveisGlobais variaveisGlobais) {
        this.variaveisGlobais = variaveisGlobais;
    }

    private void setIdSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        Cookie cookie = new Cookie("idSessao", id);
        cookie.setMaxAge(3600);
        ((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(cookie);
    }

    private String getIdSessao() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("idSessao")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public void getSessao() {
        String idSessao = getIdSessao();
        
        if (idSessao != null && !idSessao.isEmpty()) {
            for (Sessao s : SESSOES) {
                if (s.id.equals(idSessao)) {
                    copiar(s);
                    return;
                }
            }
        }
        
        novaSessao();
    }

    private void novaSessao() {
        this.id = UUID.randomUUID().toString();

        variaveisGlobais = new VariaveisGlobais();
        variaveisGlobais.setUsuario(new Usuario());

        UsuarioDao usuarioDao = new UsuarioDao(variaveisGlobais);
        usuarioDao.setUsuario(variaveisGlobais.getUsuario());
        variaveisGlobais.setUsuarioDao(usuarioDao);

        ClienteDao clienteDao = new ClienteDao(variaveisGlobais);
        clienteDao.setCliente(new Cliente());
        variaveisGlobais.setClienteDao(clienteDao);

        ProdutoDao produtoDao = new ProdutoDao(variaveisGlobais);
        produtoDao.setProduto(new Produto());
        variaveisGlobais.setProdutoDao(produtoDao);

        VendaDao vendaDao = new VendaDao(variaveisGlobais);
        vendaDao.setVenda(new Venda());
        variaveisGlobais.setVendaDao(vendaDao);

        ItemVendaDao itemDao = new ItemVendaDao();
        itemDao.setItem(new ItemVenda());
        variaveisGlobais.setItemDao(itemDao);

        PagamentoDao pagamentoDao = new PagamentoDao();
        pagamentoDao.setPagamento(new Pagamento());
        variaveisGlobais.setPagamentoDao(pagamentoDao);

        EstoqueDao estoqueDao = new EstoqueDao(variaveisGlobais);
        estoqueDao.setEstoque(new Estoque());
        estoqueDao.getEstoque().setNome(variaveisGlobais.getUsuario().getEstoque());
        variaveisGlobais.setEstoqueDao(estoqueDao);

        rnVenda = new RNVenda(variaveisGlobais);

        SESSOES.add(this);
        
        setIdSessao();
    }

    private void copiar(Sessao s) {
        this.id = s.id;
        this.variaveisGlobais = s.variaveisGlobais;
        this.rnVenda = s.rnVenda;
    }

}
