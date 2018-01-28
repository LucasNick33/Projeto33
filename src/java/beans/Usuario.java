package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.swing.JOptionPane;
import rn.Permissao;
import util.DataUtils;

@Entity
public class Usuario implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(unique = true, nullable = false, name = "nome")
    private String nome;
    @Column(name = "CPF")
    private String CPF;
    @Column(nullable = false, name = "senha")
    private String senha;
    @Column(name = "email")
    private String email;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "data_nascimento")
    private Timestamp dataNascimento;
    @Transient
    private String nascimento;
    @Column(name = "permissoes")
    private String permissoes;
    @Transient
    private Boolean cadastroCliente;
    @Transient
    private Boolean cadastroEstoque;
    @Transient
    private Boolean cadastroProduto;
    @Transient
    private Boolean cadastroUsuario;
    @Transient
    private Boolean configuracoes;
    @Transient
    private Boolean descontoItem;
    @Transient
    private Boolean editarVenda;
    @Column(name = "ativo")
    private Boolean ativo;
    @Column(name = "porcentagem_desconto")
    private BigDecimal porcentagemDesconto;
    @Transient
    private String estoque;
    @Transient
    private Boolean logado;

    public Usuario() {
        CPF = "";
        email = "";
        telefone = "";
        endereco = "";
        permissoes = "";
        ativo = true;
        porcentagemDesconto = BigDecimal.ZERO;
        logado = false;
        cadastroCliente = true;
        cadastroEstoque = true;
        cadastroProduto = true;
        cadastroUsuario = true;
        configuracoes = true;
        descontoItem = true;
        editarVenda = true;
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
        permissoesToString();
        return permissoes;
    }

    public void permissoesToString(){
        permissoes = "";
        if(cadastroCliente){
            permissoes += "," + Permissao.CADASTRO_CLIENTE.getId();
        }
        if(cadastroEstoque){
            permissoes += "," + Permissao.CADASTRO_ESTOQUE.getId();
        }
        if(cadastroProduto){
            permissoes += "," + Permissao.CADASTRO_PRODUTO.getId();
        }
        if(cadastroUsuario){
            permissoes += "," + Permissao.CADASTRO_USUARIO.getId();
        }
        if(configuracoes){
            permissoes += "," + Permissao.CONFIGURACOES.getId();
        }
        if(descontoItem){
            permissoes += "," + Permissao.DESCONTO_ITEM_VENDA.getId();
        }
        if(editarVenda){
            permissoes += "," + Permissao.EDITAR_VENDA.getId();
        }
        if(permissoes.isEmpty()){
            permissoes = "";
            return;
        }
        permissoes = permissoes.substring(1);
    }
    
    public void setPermissoes(String permissoes) {
        this.permissoes = permissoes;
    }

    public void permissoesFromString(){
        if(Permissao.temPermissao(permissoes, Permissao.CADASTRO_CLIENTE)){
            cadastroCliente = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.CADASTRO_ESTOQUE)){
            cadastroEstoque = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.CADASTRO_PRODUTO)){
            cadastroProduto = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.CADASTRO_USUARIO)){
            cadastroUsuario = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.CONFIGURACOES)){
            configuracoes = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.DESCONTO_ITEM_VENDA)){
            descontoItem = true;
        }
        if(Permissao.temPermissao(permissoes, Permissao.EDITAR_VENDA)){
            editarVenda = true;
        }
    }
    
    public Boolean getCadastroCliente() {
        permissoesFromString();
        return cadastroCliente;
    }

    public void setCadastroCliente(Boolean cadastroCliente) {
        this.cadastroCliente = cadastroCliente;
        permissoesToString();
    }

    public Boolean getCadastroEstoque() {
        permissoesFromString();
        return cadastroEstoque;
    }

    public void setCadastroEstoque(Boolean cadastroEstoque) {
        this.cadastroEstoque = cadastroEstoque;
        permissoesToString();
    }

    public Boolean getCadastroProduto() {
        permissoesFromString();
        return cadastroProduto;
    }

    public void setCadastroProduto(Boolean cadastroProduto) {
        this.cadastroProduto = cadastroProduto;
        permissoesToString();
    }

    public Boolean getCadastroUsuario() {
        permissoesFromString();
        return cadastroUsuario;
    }

    public void setCadastroUsuario(Boolean cadastroUsuario) {
        this.cadastroUsuario = cadastroUsuario;
        permissoesToString();
    }

    public Boolean getConfiguracoes() {
        permissoesFromString();
        return configuracoes;
    }

    public void setConfiguracoes(Boolean configuracoes) {
        this.configuracoes = configuracoes;
        permissoesToString();
    }

    public Boolean getDescontoItem() {
        permissoesFromString();
        return descontoItem;
    }

    public void setDescontoItem(Boolean descontoItem) {
        this.descontoItem = descontoItem;
        permissoesToString();
    }

    public Boolean getEditarVenda() {
        return editarVenda;
    }

    public void setEditarVenda(Boolean editarVenda) {
        this.editarVenda = editarVenda;
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
    public String toString() {
        return getNome();
    }

}
