
package Venda_Ingresso.services;



import Venda_Ingresso.entities.Ingresso;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivo {

    
    public static void serializar(List<Ingresso> ingressos, String path) {
        
        boolean sucesso = false;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {

            oos.writeObject(ingressos);

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }finally {
        if (sucesso) {
            System.out.println("Serialização concluída com sucesso.");
        } else {
            System.out.println("Falha na serialização.");
        }
      }
    }

   
    @SuppressWarnings("unchecked")
    public static List<Ingresso> desserializar(String path) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {

            return (List<Ingresso>) ois.readObject();

        } catch (IOException e) {
            System.out.println("Erro de leitura (arquivo pode não existir): " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Erro de classe não encontrada: " + e.getMessage());
        }
        
        return new ArrayList<>(); // fallback seguro
    }

    // EXPORTAR TXT
    public static void exportarTxt(List<Ingresso> ingressos, String path) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            writer.write("===== RELATÓRIO DE INGRESSOS =====");
            writer.newLine();
            writer.newLine();

            for (Ingresso ingresso : ingressos) {

                writer.write("Nome: " + ingresso.getNome());
                writer.newLine();

                writer.write("Setor: " + ingresso.getSetor());
                writer.newLine();

                writer.write("Quantidade: " + ingresso.getQuantidade());
                writer.newLine();

                writer.write("Valor Unitário: R$ " + String.format("%.2f", ingresso.getValor()));
                writer.newLine();

                writer.write("Valor Total: R$ " + String.format("%.2f", ingresso.getValorTotal()));
                writer.newLine();

                writer.write("Data/Hora: " + ingresso.getDataHora());
                writer.newLine();

                writer.write("-----------------------------------");
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao exportar TXT: " + e.getMessage());
        }
    }
}


