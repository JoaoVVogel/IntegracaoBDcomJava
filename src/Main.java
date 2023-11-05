import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //COLOCA AS INFORMAÇÕES DE CADASTRO
        String dbURL = "jdbc:mysql://localhost:3306/projetojava";
        String username = "root";
        String password = "MYindustri@l123QL";
        Connection conn = null;
        try {//Tenta abrir o banco
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("BANCO DE DADOS CONECTADO COM SUCESSO!");
            }
            Scanner pega = new Scanner(System.in);
        Produtor produtor;
        produtor = new Produtor();
        Tecnologia tecnologia;
        tecnologia = new Tecnologia();
        Localidade localidade;
        localidade = new Localidade();
        int escolhamain;
        do {
            Statement statement = conn.createStatement();
            String txtmain = """
                    ---CRUD---
                    O que voce deseja fazer?
                    1 - Visualizar
                    2 - Cadastrar
                    3 - Atualizar
                    4 - Excluir
                    0 - Sair""";
            System.out.println(txtmain);
            escolhamain = pega.nextInt();
            switch (escolhamain) {
                case 1://Imprime na tela
                    String txtvisualizar = """
                            Qual voce deseja visualizar?
                            1-Produtor
                            2-Tecnologia
                            3-Localidade""";
                    System.out.println(txtvisualizar);
                    int escolhaexibir = pega.nextInt();
                    switch (escolhaexibir) {
                        case 1://Exibe produtores
                            System.out.println("--Lista de Produtores--");
                            ResultSet rs = statement.executeQuery("SELECT * FROM produtor");
                            while (rs.next()) {
                                String lastName = rs.getString("nome");
                                String id = rs.getString("id");
                                System.out.println(id + " - " + lastName + "\n");
                            }
                            break;
                        case 2://Exibe tecnologia
                            System.out.println("--Lista de Tecnologias--");
                            ResultSet rst = statement.executeQuery("SELECT * FROM tecnologia");
                            while (rst.next()) {
                                String lastName = rst.getString("tipoQueijo");
                                String id = rst.getString("id");
                                System.out.println(id + " - " + lastName + "\n");
                            }
                            break;
                        case 3://Exibe localidades
                            System.out.println("--Lista de Localidades--");
                            ResultSet rsl = statement.executeQuery("SELECT * FROM localidade");
                            while (rsl.next()) {
                                String lastName = rsl.getString("endereco");
                                String id = rsl.getString("id");
                                System.out.println(id + " - " + lastName + "\n");
                            }
                            break;
                    }
                    break;
                case 2://Adiciona
                    String txtcadastrar = """
                            O que voce deseja cadastrar?
                            1-Produtor
                            2-Tecnologia
                            3-Localidade""";
                    System.out.println(txtcadastrar);
                    int ecolhacadastrar = pega.nextInt();
                    switch (ecolhacadastrar) {
                        case 1://Caso produtor
                            System.out.println("Digite o nome do produtor:");
                            pega.nextLine();//limpa o buffer do teclado
                            produtor.setNome(pega.nextLine());
                            System.out.println("Digite o CNPJ do produtor:");
                            produtor.setCNPJ(pega.nextInt());
                            System.out.println("Digite a situacao do produtor:(1-Ativo/2-Em implementacao/3-Desistente");
                            produtor.setSituacao(pega.nextInt());
                            String query = " insert into produtor (cnpj, nome, situacao)"
                                         + " values (?, ?, ?)";
                            PreparedStatement preparedStmt = conn.prepareStatement(query);
                            preparedStmt.setInt(1, produtor.getCNPJ());
                            preparedStmt.setString(2, produtor.getNome());
                            preparedStmt.setInt(3, produtor.getSituacao());
                            preparedStmt.execute();
                            System.out.println("Adicionado com sucesso!");
                            break;

                        case 2://Caso tecnologia
                            System.out.println("Digite o queijo que a tecnologia produzirá:");
                            pega.nextLine();//limpa o buffer do teclado
                            tecnologia.setNomeQueijo(pega.nextLine());
                            System.out.println("Digite o processo utilizado:");
                            tecnologia.setProcesso(pega.nextLine());
                            System.out.println("Digite o tipo de preparo do queijo");
                            tecnologia.setPreparo(pega.nextLine());
                            String queryTecnologia = " insert into tecnologia (tipoQueijo, processoUtilizado, modoPreparo)"
                                                   + " values (?, ?, ?)";
                            PreparedStatement preparedStmtTecnologia = conn.prepareStatement(queryTecnologia);
                            preparedStmtTecnologia.setString(1, tecnologia.getNomeQueijo());
                            preparedStmtTecnologia.setString(2, tecnologia.getProcesso());
                            preparedStmtTecnologia.setString(3, tecnologia.getPreparo());
                            preparedStmtTecnologia.execute();
                            System.out.println("Adicionado com sucesso!");
                            break;

                        case 3://Caso localidade
                            System.out.println("Digite o estado que deseja cadastrar:");
                            pega.nextLine();
                            localidade.setEstado(pega.nextLine());
                            System.out.println("Digite a cidade:");
                            localidade.setCidade(pega.nextLine());
                            System.out.println("Digite o nome da estrada:");
                            localidade.setNomeEstrada(pega.nextLine());
                            System.out.println("Digite o numero da propriedade:");
                            localidade.setNumero(pega.nextInt());
                            System.out.println("Digite a longitude:");
                            localidade.setLongitude(pega.nextInt());
                            System.out.println("Digite a latitude:");
                            localidade.setLatitude(pega.nextInt());
                            String queryLocalidade = " insert into localidade (estado, cidade, nomeEstrada, numero, longitude, latitude)"
                                    + " values (?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStmtLocalidade = conn.prepareStatement(queryLocalidade);
                            preparedStmtLocalidade.setString(1, localidade.getEstado());
                            preparedStmtLocalidade.setString(2, localidade.getCidade());
                            preparedStmtLocalidade.setString(3, localidade.getNomeEstrada());
                            preparedStmtLocalidade.setInt(4, localidade.getNumero());
                            preparedStmtLocalidade.setInt(5, localidade.getLongitude());
                            preparedStmtLocalidade.setInt(6, localidade.getLatitude());
                            preparedStmtLocalidade.execute();
                            System.out.println("Adicionado com sucesso!");
                            break;

                        default:
                            System.out.println("Erro!");
                            break;
                    }
                    break;
                case 3://Atualizar
                    String txtatualizar = """
                            O que deseja atualizar?
                            
                            """;

                    break;
                case 4://Excluir
                    String txtexcluir = """
                            De onde voce deseja excluir?
                            1-Produtor""";
                    System.out.println(txtexcluir);
                    int escolhaexcluir = pega.nextInt();
                    switch (escolhaexcluir) {
                        case 1://Escolha produtor
                            System.out.println("Insira o ID do produtor que deseja excluir:");
                            int idexcluir = pega.nextInt();
                            String query = " DELETE FROM produtor"
                                         + " WHERE id = (?);";
                            PreparedStatement preparedStmt = conn.prepareStatement(query);
                            preparedStmt.setInt(1,idexcluir);
                            preparedStmt.execute();
                            System.out.println("Excluido com sucesso!");
                            break;
                    }
                    break;
                default:
                    System.out.println("Erro!");
                    break;
            }
        }while (escolhamain!=0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}