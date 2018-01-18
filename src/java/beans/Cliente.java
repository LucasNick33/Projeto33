package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente implements Serializable {
    
    @Id
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    private String CPF;
    private String email;
    private String telefone;
    private String endereco;
    private Timestamp dataNascimento;
    private Boolean ativo;
    private BigDecimal limiteDebto;
    
    public Cliente(){
        CPF = "";
        email = "";
        telefone = "";
        endereco = "";
        ativo = true;
        limiteDebto = BigDecimal.ZERO;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getLimiteDebto() {
        return limiteDebto;
    }

    public void setLimiteDebto(BigDecimal limiteDebto) {
        this.limiteDebto = limiteDebto;
    }
    
}
