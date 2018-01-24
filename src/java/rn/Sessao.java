package rn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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
    private static final Integer TEMPO_SESSAO;

    static {
        SESSOES = new ArrayList<>();
        TEMPO_SESSAO = 300;
    }

    public Sessao() {
        limparSessoes();
        getSessao();
        checarLogin();
    }

    private RNVenda rnVenda;
    private String id;
    private VariaveisGlobais variaveisGlobais;
    private Long tempoPersistencia;

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

    private void limparSessoes(){
        long time = Calendar.getInstance().getTimeInMillis();
        for(Sessao s : SESSOES){
            if(s.tempoPersistencia + TEMPO_SESSAO * 1000 < time){
                SESSOES.remove(s);
            }
        }
    }
    
    private void checarLogin() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (!request.getRequestURI().endsWith("Login.xhtml") && !variaveisGlobais.getUsuario().getLogado()) {
            try {
                ((HttpServletResponse) facesContext.getExternalContext().getResponse()).sendRedirect("/Projeto33/Login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Sessao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void redirectToIndex() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            ((HttpServletResponse) facesContext.getExternalContext().getResponse()).sendRedirect("/Projeto33/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Sessao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setIdSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        Cookie cookie = new Cookie("idSessao", id);
        cookie.setMaxAge(TEMPO_SESSAO);
        tempoPersistencia = Calendar.getInstance().getTimeInMillis();
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
        variaveisGlobais = new VariaveisGlobais(this);
        rnVenda = new RNVenda(variaveisGlobais);
        SESSOES.add(this);
        setIdSessao();
    }

    private void copiar(Sessao s) {
        this.id = s.id;
        this.variaveisGlobais = s.variaveisGlobais;
        this.rnVenda = s.rnVenda;
        s.tempoPersistencia = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sessao other = (Sessao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
