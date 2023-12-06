package org.example;
import java.util.Scanner;
import java.sql.*;


public class Main {
    public static void afisare_tabela_persoane(Statement statement, String mesaj) {
        String sql ="select * from persoane";
        System.out.println("\n---" +mesaj +"---");
        try(ResultSet rs=statement.executeQuery(sql)) {
            while (rs.next())
                System.out.println("id=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta=" + rs.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void afisare_persoane_si_excursii(Connection connection,Statement statement, String mesaj) {
        String sql_persoane ="select * from persoane";
        String sql_excursii="select * from excursii where id_persoana=?";
        System.out.println("\n---" +mesaj +"---");
        try(ResultSet rs=statement.executeQuery(sql_persoane)) {
            while (rs.next()){
                System.out.println("id=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta=" + rs.getInt(3));
                PreparedStatement ps=connection.prepareStatement(sql_excursii);
                ps.setInt(1, rs.getInt(1) );
                ResultSet rs2= ps.executeQuery();
                while (rs2.next())
                    System.out.println("id_persoana=" + rs2.getInt(1) + ", id_excursie=" + rs2.getInt(2) + ",locatie=" + rs2.getString(3)+", anul="+rs2.getInt(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public static void afisare_excursii_dupa_persoana(Connection connection,Statement statement, String persoana_c, String mesaj){
        String sql1="select id from persoane where nume=?";
        String sql="select * from excursii where id_persoana=?";
        System.out.println("\n---" +mesaj +"---");
        try {
            PreparedStatement ps1=connection.prepareStatement(sql1);
            ps1.setString(1, persoana_c);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, rs1.getInt(1));
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                System.out.println("id_persoana=" + rs.getInt(1) + ", id_excursie=" + rs.getInt(2) + ",locatie=" + rs.getString(3)+", anul="+rs.getInt(4));



        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static void afisare_persoane_dupa_excursii(Connection connection,Statement statement, String excursia_c, String mesaj){
        String sql1="select id_persoana from excursii where destinatia=?";
        String sql2="select * from persoane where id=?";
        System.out.println("\n---" +mesaj +"---");
        try {
            PreparedStatement ps1=connection.prepareStatement(sql1);
            ps1.setString(1, excursia_c);
            ResultSet rs1 = ps1.executeQuery();
           while(rs1.next()){
            PreparedStatement ps=connection.prepareStatement(sql2);
            ps.setInt(1, rs1.getInt(1));
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                System.out.println("id=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta=" + rs.getInt(3));}



        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static void  afisare_persoane_dupa_an_excursie(Connection connection,Statement statement, int an_c, String mesaj){
        String sql1="select id_persoana from excursii where anul=?";
        String sql2="select * from persoane where id=?";
        System.out.println("\n---" +mesaj +"---");
        try {
            PreparedStatement ps1=connection.prepareStatement(sql1);
            ps1.setInt(1, an_c);
            ResultSet rs1 = ps1.executeQuery();

           while( rs1.next()){
            PreparedStatement ps=connection.prepareStatement(sql2);
            ps.setInt(1, rs1.getInt(1));
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                System.out.println("id=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta=" + rs.getInt(3));}



        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public static void afisare_tabela_excursii(Statement statement, String mesaj) {
        String sql ="select * from excursii";
        System.out.println("\n---" +mesaj +"---");
        try(ResultSet rs =statement.executeQuery(sql)) {
            while (rs.next())
                System.out.println("id_persoana=" + rs.getInt(1) +", id_excursie="+rs.getInt(2) +", destinatie=" + rs.getString(3) + ",anul=" + rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void adaugare_persoana(Connection connection, int id, String nume, int varsta) {
        String sql="insert into persoane values (?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, nume);
            ps.setInt(3, varsta);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }

    public static void adaugare_excursie(Connection connection,  int id_persoana,int id_excursie, String destinatia, int anul) {
        String sql="select * from persoane where id_persoana=?";
if(sql.length()==0)
        System.out.println("Persoana nu se afla in baza de date");

if(sql.length()>0){
            String sql2="insert into excursii values (?,?,?,?)";
            try(PreparedStatement ps=connection.prepareStatement(sql2)) {
                ps.setInt(1, id_persoana);
                ps.setInt(2, id_excursie);
                ps.setString(3, destinatia);
                ps.setInt(4, anul);
                int nr_randuri=ps.executeUpdate();
                System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
            } catch (SQLException e) {
                System.out.println(sql2);
                e.printStackTrace();
            }

        }



    }


    public static void stergere_persoane(Connection connection,int id){
        String sql="delete from persoane where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        }
        catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }

    public static void stergere_excursii(Connection connection,int id){
        String sql="delete from excursii where id_excursie=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        }
        catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lab8";
        try {
            Connection connection = DriverManager.getConnection(url, "root", "root");
            Statement statement = connection.createStatement();
            //afisare_tabela(statement,"Continut initial");
           // adaugare_persoana(connection,5,"Catalina",21);
            // adaugare_excursie(connection,4,10,"Salonta",2003);
            afisare_tabela_persoane(statement,"Dupa adaugare");
          //  actualizare(connection,4,24);
           // afisare_tabela(statement,"Dupa modificare");
           // adaugare_excursie(connection,5,6,"paltinis",2003);
            afisare_tabela_excursii(statement,"Dupa adaugare excursie");
            afisare_persoane_si_excursii(connection,statement, "Afisarea tuturor persoanelor si pentru fiecare persoana a excursiilor in care a fost");
            afisare_persoane_si_excursii(connection,statement, "Afisarea tuturor persoanelor si pentru fiecare persoana a excursiilor in care a fost");
            Scanner scanner=new Scanner(System.in);
          //  System.out.println("LDati numele unei persoane pentru a afisa excursiile sale");
            //String persoana_cautata = scanner.next();
            //afisare_excursii_dupa_persoana(connection,statement, persoana_cautata," Afișarea excursiilor în care a fost o persoană al cărei nume se citește de la tastatură");
            //String excursie_cautata = scanner.next();
           // afisare_persoane_dupa_excursii(connection,statement, excursie_cautata," Afisarea tuturor persoanelor care au vizitat o anumita destinatie.");
            int an_c=scanner.nextInt();
            afisare_persoane_dupa_an_excursie(connection,statement, an_c," Afisarea persoanelor care au facut excursii intr-un an introdus");


            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
