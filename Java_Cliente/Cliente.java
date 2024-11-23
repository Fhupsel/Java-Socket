import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conectado ao servidor!");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Digite um ID (ou 'sair' para encerrar): ");
                String input = scanner.nextLine();

                // Permitir encerrar o cliente
                if (input.equalsIgnoreCase("sair")) {
                    break;
                }

                // Enviar ID para o servidor
                out.println(input);

                // Receber e exibir a resposta do servidor
                String resposta = in.readLine();
                System.out.println("Resposta do servidor: " + resposta);
            }

            // Fechar recursos
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
