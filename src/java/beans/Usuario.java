package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import util.DataUtils;

@Entity
public class Usuario implements Serializable {
    
    @Id
    @Column(name="id")
    private Long id;
    @Column(unique = true, nullable = false, name="nome")
    private String nome;
    @Column(name="CPF")
    private String CPF;
    @Column(nullable = false, name="senha")
    private String senha;
    @Column(name="email")
    private String email;
    @Column(name="telefone")
    private String telefone;
    @Column(name="endereco")
    private String endereco;
    @Column(name="data_nascimento")
    private Timestamp dataNascimento;
    @Transient
    private String nascimento;
    @Column(name="permissoes")
    private String permissoes;
    @Column(name="ativo")
    private Boolean ativo;
    @Column(name="porcentagem_desconto")
    private BigDecimal porcentagemDesconto;
    @Transient
    private String estoque;
    @Transient
    private Boolean logado;
    
    public Usuario(){
        CPF = "";
        email = "";
        telefone = "";
        endereco = "";
        permissoes = "";
        ativo = true;
        porcentagemDesconto = BigDecimal.ZERO;
        logado = false;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Timestamp getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Timestamp dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNascimento() {
        return DataUtils.deTimestampSemHorario(dataNascimento);
    }

    public void setNascimento(String nascimento) {
        this.dataNascimento = DataUtils.paraTimestampSemHorario(nascimento);
    }

    public String getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(String permissoes) {
        this.permissoes = permissoes;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public Boolean getLogado() {
        return logado;
    }

    public void setLogado(Boolean logado) {
        this.logado = logado;
    }

    @Override
    public String toString(){
        return getNome();
    }
    
}
