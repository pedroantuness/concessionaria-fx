package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;
import com.example.model.Veiculo;

public class VeiculoDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "";
    private final String PASS = "";
    
    public void inserir(Veiculo veiculo) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO veiculos (marca, modelo, ano, preco, cliente_id) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, veiculo.getMarca());
        ps.setString(2, veiculo.getModelo());
        ps.setInt(3, veiculo.getAno());
        ps.setBigDecimal(4, veiculo.getValor());
        ps.setInt(5, veiculo.getCliente().getId());

        ps.executeUpdate();
        con.close();
    }

    public List<Veiculo> buscarTodos() throws SQLException{
        var veiculos = new ArrayList<Veiculo>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT veiculos.*, clientes.nome FROM veiculos INNER JOIN clientes ON veiculos.cliente_id=clientes.id");

        while(rs.next()){
            veiculos.add(new Veiculo(
                rs.getInt("id"),
                rs.getString("marca"), 
                rs.getString("modelo"), 
                rs.getInt("ano"), 
                rs.getBigDecimal("preco"),
                new Cliente(
                    rs.getInt("cliente_id"),
                    rs.getString("nome"),
                    null,
                    null
                )
            ));
        }

        con.close();
        return veiculos;
    }

    public void apagar(Veiculo veiculo) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM veiculos WHERE id=?"); 
        ps.setInt(1, veiculo.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE veiculos SET marca=?, modelo=?, ano=?, preco=? WHERE id=?");
        ps.setString(1, veiculo.getMarca());
        ps.setString(2, veiculo.getModelo());
        ps.setInt(3, veiculo.getAno());
        ps.setBigDecimal(4, veiculo.getValor());
        ps.setInt(5, veiculo.getId());
        ps.executeUpdate();
        con.close();

    }

}
