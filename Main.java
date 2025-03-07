import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Questão 1
        calcularSoma();
        
        // Questão 2
        int numero = 21; // Defina o número desejado
        verificarFibonacci(numero);
        
        // Questão 3
        analisarFaturamento("dados.json", "dados (2).xml");
        
        // Questão 4
        calcularPercentualFaturamento();
        
        // Questão 5
        String texto = "hello world";
        System.out.println("Invertido: " + inverterString(texto));
    }

    public static void calcularSoma() {
        int INDICE = 13, SOMA = 0, K = 0;
        while (K < INDICE) {
            K = K + 1;
            SOMA = SOMA + K;
        }
        System.out.println("Questão 1 - Soma: " + SOMA);
    }

    public static void verificarFibonacci(int num) {
        int a = 0, b = 1, temp;
        while (a < num) {
            temp = a + b;
            a = b;
            b = temp;
        }
        if (a == num) {
            System.out.println("Questão 2 - " + num + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println("Questão 2 - " + num + " não pertence à sequência de Fibonacci.");
        }
    }

    public static void analisarFaturamento(String jsonFile, String xmlFile) {
        List<Double> valores = new ArrayList<>();
        double soma = 0;
        int diasComFaturamento = 0;
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(jsonFile));
            for (JsonNode node : rootNode) {
                double valor = node.get("valor").asDouble();
                if (valor > 0) {
                    valores.add(valor);
                    soma += valor;
                    diasComFaturamento++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
        
        try {
            File xml = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            
            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                Node node = rows.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double valor = Double.parseDouble(element.getElementsByTagName("valor").item(0).getTextContent());
                    if (valor > 0) {
                        valores.add(valor);
                        soma += valor;
                        diasComFaturamento++;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo XML: " + e.getMessage());
        }
        
        double media = soma / diasComFaturamento;
        double min = valores.stream().min(Double::compare).orElse(0.0);
        double max = valores.stream().max(Double::compare).orElse(0.0);
        long diasAcimaMedia = valores.stream().filter(v -> v > media).count();
        
        System.out.println("Questão 3 - Faturamento diário:");
        System.out.println("Menor valor: " + min);
        System.out.println("Maior valor: " + max);
        System.out.println("Dias acima da média: " + diasAcimaMedia);
    }

    public static void calcularPercentualFaturamento() {
        double sp = 67836.43, rj = 36678.66, mg = 29229.88, es = 27165.48, outros = 19849.53;
        double total = sp + rj + mg + es + outros;
        System.out.println("Questão 4 - Percentuais de faturamento:");
        System.out.printf("SP: %.2f%%\n", (sp / total) * 100);
        System.out.printf("RJ: %.2f%%\n", (rj / total) * 100);
        System.out.printf("MG: %.2f%%\n", (mg / total) * 100);
        System.out.printf("ES: %.2f%%\n", (es / total) * 100);
        System.out.printf("Outros: %.2f%%\n", (outros / total) * 100);
    }

    public static String inverterString(String str) {
        String invertida = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            invertida += str.charAt(i);
        }
        return invertida;
    }
}
