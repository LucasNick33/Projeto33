package dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class BaseDao {
    
    private static Session conexao;

    static{
        conexao = new Configuration().configure().buildSessionFactory().openSession();
    }
    
    public static Session getConexao() {
        return conexao;
    }

    public static void setConexao(Session conexao) {
        BaseDao.conexao = conexao;
    }
    
}
