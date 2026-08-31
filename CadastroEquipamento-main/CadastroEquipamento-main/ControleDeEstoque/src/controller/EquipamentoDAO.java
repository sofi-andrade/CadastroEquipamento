/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


     
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Equipamento;
/**
 *
 * @author Admin
 */

public class EquipamentoDAO {
    
    Connection conexao = Conexao.getConexao();
    
     public void cadastrarEquipamento(Equipamento equipamento) throws SQLException {
       // Connection conexao = Conexao.getConexao();

        String sql = "INSERT INTO tbl_equipamentos(nome,descricao,quantidade,marca,sala,estadoConservacao,patrimonio)VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps = conexao.prepareStatement(sql);

        ps.setString(1, equipamento.getNome());
        ps.setString(2, equipamento.getDescricao());
        ps.setInt(3, equipamento.getQuantidade());
        ps.setString(4, equipamento.getMarca());
        ps.setInt(5, equipamento.getSala());
        ps.setString(6, equipamento.getEstadoConservacao());
        ps.setString(7,equipamento.getPatrimonio());
    
        ps.execute();
    }
     
 public void pesquisar(Equipamento equipamento) throws SQLException {

    String sql = "SELECT nome, descricao, quantidade, marca, sala, " +
                 "estadoConservacao, patrimonio " +
                 "FROM tbl_equipamentos WHERE patrimonio = ?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {

        ps.setString(1, equipamento.getPatrimonio().trim());

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                equipamento.setNome(rs.getString("nome"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setQuantidade(rs.getInt("quantidade"));
                equipamento.setMarca(rs.getString("marca"));
                equipamento.setSala(rs.getInt("sala"));
                equipamento.setEstadoConservacao(rs.getString("estadoConservacao")
                );
                equipamento.setPatrimonio(rs.getString("patrimonio"));
            }
        }
    }
}
     
  public void excluir(Equipamento equipamento) throws SQLException {
   
    String sql = "DELETE FROM tbl_equipamentos WHERE patrimonio = ?";
    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, equipamento.getPatrimonio().trim());
        int linhasAfetadas = ps.executeUpdate();
        System.out.println("Linhas removidas: " + linhasAfetadas);
    }

}

       
    public void alterar(Equipamento equipamento) throws SQLException {
    String sql = "UPDATE tbl_equipamentos SET nome=?, descricao=?, quantidade=?, marca=?, sala=?, estadoConservacao=? WHERE patrimonio=?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, equipamento.getNome());
        ps.setString(2, equipamento.getDescricao());
        ps.setInt(3, equipamento.getQuantidade());
        ps.setString(4, equipamento.getMarca());
        ps.setInt(5, equipamento.getSala());
        ps.setString(6, equipamento.getEstadoConservacao());
        ps.setString(7, equipamento.getPatrimonio());

        int linhasAfetadas = ps.executeUpdate();
        System.out.println("Linhas atualizadas: " + linhasAfetadas);
    }
}
    
    public List<Equipamento> pesquisarSala(int sala) throws SQLException {

    List<Equipamento> equipamentos = new ArrayList<>();

    String sql = "SELECT * FROM tbl_equipamentos WHERE sala=?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {

        ps.setInt(1, sala);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Equipamento equipamento = new Equipamento();

                equipamento.setNome(rs.getString("nome"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setQuantidade(rs.getInt("quantidade"));
                equipamento.setMarca(rs.getString("marca"));
                equipamento.setSala(rs.getInt("sala"));
                equipamento.setEstadoConservacao(rs.getString("estadoConservacao"));
                equipamento.setPatrimonio(rs.getString("patrimonio"));

                equipamentos.add(equipamento);
            }
        }
    }

    return equipamentos;
}
}
