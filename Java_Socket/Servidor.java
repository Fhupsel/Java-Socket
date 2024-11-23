import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Servidor {
    public static void main(String[] args) {
        // Criar um HashMap para armazenar dados de exemplo
        HashMap<Integer, String> dataMap = new HashMap<>();
        dataMap.put(1, "Informação do ID 1");
        dataMap.put(2, "Informação do ID 2");
        dataMap.put(3, "Informação do ID 3");

        try {
            // Inicializar o ServerSocket na porta 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado na porta 12345");

            while (true) {
                // Aguardar conexão de cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado!");

                // Criar uma thread para tratar o cliente (permitindo múltiplos clientes no futuro)
                new Thread(() -> handleClient(clientSocket, dataMap)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, HashMap<Integer, String> dataMap) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                // Verificar se o cliente deseja sair -> sair
                if (input.equalsIgnoreCase("sair")) {
                    out.println("Conexão encerrada. Até mais!");
                    break;
                }

                try {
                    // Converter o input para um número inteiro (ID)
                    int id = Integer.parseInt(input);

                    // Buscar a informação correspondente no HashMap
                    String resposta = dataMap.getOrDefault(id, "ID não encontrado. Por favor, insira algum desses números: " + dataMap.keySet());
                    out.println(resposta);
                } catch (NumberFormatException e) {
                    out.println("ID inválido. Por favor, insira algum desses números: " + dataMap.keySet());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Conexão com o cliente encerrada.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
