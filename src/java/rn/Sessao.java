package rn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Sessao(){
        getSessao();
        checarLogin();
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

    private void checarLogin(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if(!request.getContextPath().endsWith("Login.xhtml") && !variaveisGlobais.getUsuario().getLogado()){
            try {
                ((HttpServletResponse) facesContext.getExternalContext().getResponse()).sendRedirect("Login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Sessao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    private void getSessao() {
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
